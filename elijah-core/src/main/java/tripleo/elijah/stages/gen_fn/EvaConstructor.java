/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.deduce.*;

import java.util.function.*;

/**
 * Created 6/27/21 9:45 AM
 */
public class EvaConstructor extends BaseEvaFunction implements IEvaConstructor {
	public final @Nullable ConstructorDef cd;
	private final Eventual<DeduceElement3_Constructor> _de3_Promise = new Eventual<>();

	public static class __Reactive extends DefaultReactive implements BaseEvaConstructor_Reactive {

		@Override
		public <T> void addListener(final Consumer<T> t) {
			throw new UnintendedUseException();
		}
	}


		public EvaConstructor(final @Nullable ConstructorDef aConstructorDef) {
		cd = aConstructorDef;
	}

	@Override
	public Eventual<DeduceElement3_Constructor> de3_Promise() {
		return _de3_Promise;
	}

	@Override
	public @NotNull FunctionDef getFD() {
		if (cd == null)
			throw new IllegalStateException("No function");
		return cd;
	}

	@Override
	public @Nullable VariableTableEntry getSelf() {
		if (getFD().getParent() instanceof ClassStatement)
			return getVarTableEntry(0);
		else
			return null;
	}

	@Override
	public String identityString() {
		return String.valueOf(cd);
	}

	@Override
	public @NotNull OS_Module module() {
		return cd.getContext().module();
	}

	@Override
	public String name() {
		if (cd == null) {
			throw new IllegalArgumentException("null cd");
		}
		return cd.name();
	}

	@Override
	public void setFunctionInvocation(@NotNull FunctionInvocation fi) {
		GenType genType = new GenTypeImpl();
		if (fi.getClassInvocation() != null) {
			final ClassInvocation classInvocation = fi.getClassInvocation();
			genType.setCi(classInvocation);

			final ClassStatement classStatement = classInvocation.getKlass();
			final OS_Type        osType         = classStatement.getOS_Type();
			genType.setResolved(osType);
		} else {
			final NamespaceInvocation namespaceInvocation = fi.getNamespaceInvocation();
			genType.setCi(namespaceInvocation);

			final NamespaceStatement namespaceStatement = namespaceInvocation.getNamespace();
			final OS_Type            osType             = namespaceStatement.getOS_Type();
			genType.setResolved(osType);
		}
		genType.setNode(this);
		typeDeferred().resolve(genType);
	}

	@Override
	public String toString() {
		return String.format("<GeneratedConstructor %s>", cd);
	}
}

//
//
//
