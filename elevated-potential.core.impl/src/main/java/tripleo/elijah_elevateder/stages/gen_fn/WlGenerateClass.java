/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.gen_fn;

import lombok.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;
import tripleo.elijah.work.*;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_generic.ICodeRegistrar;

import java.util.function.*;

/**
 * Created 5/16/21 12:41 AM
 */
public class WlGenerateClass implements WorkJob {
	@Getter
	private final @NotNull ClassStatement               classStatement;
	@Getter
	private final @NotNull ClassInvocation              classInvocation;
	@Getter
	private final          GenerateFunctions            generateFunctions;
	private final          DeducePhase.GeneratedClasses coll;
	private final @NotNull RegisterClassInvocation_env  __passthru_env;
	private       boolean        _isDone = false;
	private final ICodeRegistrar cr;

	private EvaClass Result;

	private Consumer<EvaClass> consumer;

	public WlGenerateClass(GenerateFunctions aGenerateFunctions, @NotNull ClassInvocation aClassInvocation,
			DeducePhase.GeneratedClasses coll, final ICodeRegistrar aCodeRegistrar) {
		classStatement = aClassInvocation.getKlass();
		generateFunctions = aGenerateFunctions;
		classInvocation = aClassInvocation;
		this.coll = coll;

		cr = aCodeRegistrar;

		__passthru_env = null;
	}

	public WlGenerateClass(final GenerateFunctions aGenerateFunctions, final ClassInvocation aClassInvocation,
			final DeducePhase.GeneratedClasses coll, final ICodeRegistrar aCodeRegistrar,
			final RegisterClassInvocation_env aEnv) {
		classStatement = aClassInvocation.getKlass();
		generateFunctions = aGenerateFunctions;
		classInvocation = aClassInvocation;
		this.coll = coll;

		cr = aCodeRegistrar;

		__passthru_env = aEnv;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		final DeferredObject<EvaClass, Void, Void> resolvePromise = classInvocation.resolveDeferred();

		resolvePromise.then(x -> {
			if (consumer != null) {
				consumer.accept(x);
			}
		});

		switch (resolvePromise.state()) {
		case PENDING:
			@NotNull
			EvaClass kl = generateFunctions.generateClass(classStatement, classInvocation, __passthru_env);
			// kl.setCode(generateFunctions.module.getCompilation().nextClassCode());

			cr.registerClass1(kl);

			if (coll != null) {
				coll.add(kl);
			}

			resolvePromise.resolve(kl);
			Result = kl;
			break;
		case RESOLVED:
			Holder<EvaClass> hgc = new Holder<>();
			resolvePromise.then(hgc::set);
			Result = hgc.get();
			break;
		case REJECTED:
			throw new NotImplementedException();
		}
		_isDone = true;
	}

	public void setConsumer(Consumer<EvaClass> consumer) {
		this.consumer = consumer;
	}

	public ClassInvocation getClassInvocation() {
		// 24/01/04 back and forth
		return this.classInvocation;
	}

	public GenerateFunctions getGenerateFunctions() {
		// 24/01/04 back and forth
		return this.generateFunctions;
	}
}

//
//
//
