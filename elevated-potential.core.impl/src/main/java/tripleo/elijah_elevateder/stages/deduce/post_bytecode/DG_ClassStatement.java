package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah_elevateder.stages.deduce.ClassInvocation;
import tripleo.elijah_elevateder.stages.deduce.ConstructableElementHolder;
import tripleo.elijah_elevateder.stages.deduce.FunctionInvocation;
import tripleo.elijah_elevateder.stages.deduce.NULL_DeduceTypes2;
import tripleo.elijah_elevateder.stages.gen_fn.*;

// DeduceGrand
public class DG_ClassStatement implements DG_Item {
	private final ClassStatement classStatement;
	GenericElementHolder genericElementHolder;
	private EvaClass           _evaNode;
	private ClassInvocation    classInvocation;
	private FunctionInvocation fi;
	private ProcTableEntry     pte;

	public DG_ClassStatement(final ClassStatement aClassStatement) {
		classStatement = aClassStatement;
	}

	public void attach(final FunctionInvocation aFi, final ProcTableEntry aPte) {
		fi = aFi;
		pte = aPte;
	}

	public void attachClass(final EvaClass aResult) {
		_evaNode = aResult;
	}

	public @NotNull ClassInvocation classInvocation() {
		if (classInvocation == null) {
			classInvocation = new ClassInvocation((classStatement), null, new NULL_DeduceTypes2()); // 08/28 !!
			// classInvocation = _inj().new_ClassInvocation((classStatement), null);
		}
		return classInvocation;
	}

	public @NotNull IElementHolder ConstructableElementHolder(final OS_Element aE, final VariableTableEntry aVte) {
		// return _inj().new_ConstructableElementHolder(classStatement, aVte);
		return new ConstructableElementHolder(classStatement, aVte);
	}

	public EvaClass evaClass() {
		return _evaNode;
	}

	public FunctionInvocation functionInvocation() {
		return fi;
	}

	public @NotNull GenericElementHolder GenericElementHolder() {
		if (genericElementHolder == null) {
			// genericElementHolder = _inj().new_GenericElementHolder(classStatement);
			genericElementHolder = new GenericElementHolder(classStatement);
		}
		return genericElementHolder;
	}
}
