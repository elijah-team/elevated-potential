/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.gen_fn;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.diagnostic.Locatable;
import tripleo.elijah.lang.i.AccessNotation;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah_elevateder.stages.deduce.FunctionMapDeferred;
import tripleo.elijah_elevateder.stages.deduce.RegisterClassInvocation_env;
import tripleo.elijah_elevateder.stages.gen_generic.CodeGenerator;
import tripleo.elijah_elevateder.stages.gen_generic.Dependency;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_elevateder.stages.gen_generic.IDependencyReferent;
import tripleo.elijah_fluffy.util.Maybe;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created 3/16/21 10:45 AM
 */
public abstract class EvaContainerNC extends AbstractDependencyTracker implements EvaContainer, IDependencyReferent {
	static class VarNotFound implements Diagnostic {
		@Override
		public @Nullable String code() {
			return null;
		}

		@Override
		public @NotNull Locatable primary() {
			return null;
		}

		@Override
		public void report(final PrintStream stream) {

		}

		@Override
		public @NotNull List<Locatable> secondary() {
			return null;
		}

		@Override
		public @Nullable Severity severity() {
			return null;
		}
	}

	static @NotNull Diagnostic _def_VarNotFound = new VarNotFound();
	private final Dependency dependency = new Dependency(this);
	public @NotNull Map<ClassStatement, EvaClass> classMap = new HashMap<ClassStatement, EvaClass>();
	public @NotNull Map<FunctionDef, EvaFunction> functionMap = new HashMap<FunctionDef, EvaFunction>();
	public @NotNull List<VarTableEntry> varTable = new ArrayList<VarTableEntry>();
	@NotNull
	Multimap<FunctionDef, FunctionMapDeferred> functionMapDeferreds = ArrayListMultimap.create();

	private int code = 0;

	public void addClass(ClassStatement aClassStatement, EvaClass aEvaClass) {
		classMap.put(aClassStatement, aEvaClass);
	}

	public void addFunction(@NotNull EvaFunction generatedFunction) {
		var functionDef = generatedFunction.getFD();

		if (functionMap.containsKey(functionDef))
			throw new IllegalStateException("Function already generated"); // TODO there can be overloads, although we
																			// don't handle that yet

		functionMap.put(functionDef, generatedFunction);
		functionMapDeferreds.get(functionDef).stream().forEach(deferred -> deferred.onNotify(generatedFunction));
	}

	public void addVarTableEntry(@Nullable AccessNotation an, @NotNull VariableStatement vs,
			final RegisterClassInvocation_env aPassthruEnv) {
		// TODO dont ignore AccessNotationImpl
		varTable.add(new VarTableEntry(vs, vs.getNameToken(), vs.initialValue(), vs.typeName(),
				vs.getParent().getParent(), aPassthruEnv));
	}

	public void functionMapDeferred(final FunctionDef aFunctionDef, final FunctionMapDeferred aFunctionMapDeferred) {
		functionMapDeferreds.put(aFunctionDef, aFunctionMapDeferred);
	}

	public abstract void generateCode(GenerateResultEnv aFileGen, CodeGenerator aGgc);

	@Deprecated
	public abstract int getCode();

	public @NotNull Dependency getDependency() {
		return dependency;
	}

	/**
	 * Get a {@link EvaFunction}
	 *
	 * @param fd the function searching for
	 * @return null if no such key exists
	 */
	public EvaFunction getFunction(FunctionDef fd) {
		return functionMap.get(fd);
	}

	@Override
	public @NotNull Maybe<VarTableEntry> getVariable(String aVarName) {
		for (VarTableEntry varTableEntry : varTable) {
			if (varTableEntry.nameToken.getText().equals(aVarName))
				return Maybe.of(varTableEntry);
		}
		return Maybe.of_exc(_def_VarNotFound);
	}

	@Deprecated
	public abstract void setCode(int aCode);
}

//
//
//
