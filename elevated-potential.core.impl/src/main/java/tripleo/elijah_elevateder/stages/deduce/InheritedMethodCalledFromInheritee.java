package tripleo.elijah_elevateder.stages.deduce;

import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;

import java.util.Objects;

public final class InheritedMethodCalledFromInheritee implements CI_Hint {
	private final FunctionDef     activeFunction;
	private final ClassStatement  definedIn;
	private final BaseEvaFunction calledIn;

	public InheritedMethodCalledFromInheritee(FunctionDef activeFunction, ClassStatement definedIn,
											  BaseEvaFunction calledIn) {
		this.activeFunction = activeFunction;
		this.definedIn      = definedIn;
		this.calledIn       = calledIn;
	}

	public FunctionDef activeFunction() {
		return activeFunction;
	}

	public ClassStatement definedIn() {
		return definedIn;
	}

	public BaseEvaFunction calledIn() {
		return calledIn;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (InheritedMethodCalledFromInheritee) obj;
		return Objects.equals(this.activeFunction, that.activeFunction) &&
				Objects.equals(this.definedIn, that.definedIn) &&
				Objects.equals(this.calledIn, that.calledIn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(activeFunction, definedIn, calledIn);
	}

	@Override
	public String toString() {
		return "InheritedMethodCalledFromInheritee[" +
				"activeFunction=" + activeFunction + ", " +
				"definedIn=" + definedIn + ", " +
				"calledIn=" + calledIn + ']';
	}

	// the function referenced by pte.expression is an inherited method
	// defined in genType.resolved and called in generatedFunction
	// for whatever reason we don't have a CodePoint (gf, instruction...)
}
