package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.elijah.lang.impl.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.util.*;
import tripleo.elijah_durable_elevated.elijah.lang.impl.VariableSequenceImpl;
import tripleo.elijah_durable_elevated.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;

public class GI_VariableStatement implements GenerateC_Item {
	private final GI_Repo               repo;
	private       EvaNode               _evaNode;
	private final VariableStatementImpl variableStatement;
	private       CR_ReferenceItem      item;

	public GI_VariableStatement(final VariableStatementImpl aVariableStatement, final GI_Repo aRepo) {
		variableStatement = aVariableStatement;
		repo = aRepo;
	}

	/**
	 * Create a {@link CReference.Reference} for
	 * {@link this.variableStatement}
	 *
	 * <p>
	 * If the parent of the variableStatemnt is the same as the generatedFunction's
	 * parent, create a DIRECT_MEMBER with the string {@code value}
	 * </p>
	 * <p>
	 * If the parent of the variableStatemnt is the same as the generatedFunction,
	 * create a LOCAL with no value
	 * </p>
	 * <p>
	 * Otherwise, create a MEMBER with the string {@code value} for value
	 * </p>
	 */
	@Contract("_, _ -> new")
	private CReference.@NotNull Reference __createReferenceForVariableStatement(
			final @NotNull IEvaFunctionBase generatedFunction, final @Nullable String value) {
		final String text2 = variableStatement.getName();

		// first getParent is VariableSequenceImpl
		final VariableSequenceImpl variableSequence = (VariableSequenceImpl) variableStatement.getParent();
		final OS_Element           parent           = variableSequence.getParent();

		final FunctionDef fd = generatedFunction.getFD();

		if (parent == fd.getParent()) {
			// A direct member value. Doesn't handle when indirect
//				text = Emit.emit("/*124*/")+"vsc->vm" + text2;
			return new CReference.Reference(text2, CReference.Ref.DIRECT_MEMBER, value);
		} else if (parent == fd) {
			return new CReference.Reference(text2, CReference.Ref.LOCAL);
		}

		// if (parent instanceof NamespaceStatement) {
		// int y=2;
		// }

		return new CReference.Reference(text2, CReference.Ref.MEMBER, value);
	}

	public void _createReferenceForVariableStatement(final @NotNull CReference aCReference,
			final @NotNull IEvaFunctionBase generatedFunction, final @Nullable String value) {
		final CReference.Reference r = __createReferenceForVariableStatement(generatedFunction, value);
		aCReference.addRef(r);
	}

	@Override
	public EvaNode getEvaNode() {
		return _evaNode;
	}

	public String getText() {
		String text2 = variableStatement.getName();

		if (text2.equals("argument_count_")) {
			var pp = variableStatement.getParent().getParent();

			if (pp instanceof ClassStatement cls) {
				assert cls.getName().equals("Arguments");

				var cp = cls.getParent();

				assert !(cp instanceof OS_Module mod) || mod.getFileName().equals("lib_elijjah/lib-c/Prelude.elijjah");

				var gf = item.getTableEntry().__gf;
				var dt2 = ((IdentTableEntry) item.getTableEntry())._deduceTypes2();

//				dt2._phase()._functionMap().asMap().entrySet().stream()
//					.filter(entry -> entry.)

				var cs = dt2._zero().findClassesFor(cls);

				assert cs.size() == 1;

				var ec = cs.get(0);

				var world = repo.generateC.ce.getCompilation().world();

//				var wcs = world.getClassesForClassStatement(cls);
//				
//				assert wcs.size() == 2;
//				
//				var lc = wcs.get(0);
//				
//				text2 = String.format("z%d%s", ec.getCode(), text2);

				var wcs2 = world.getClassesForClassNamed("Main");

				assert wcs2.size() == 2;

				var lc2 = wcs2.get(0);

				assert lc2.getElement().getPackageName().getName().equals("");

				text2 = String.format("z%d%s", lc2.getCode(), text2);

				NotImplementedException.raise();

//				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(gf);
//				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(dt2);
			}
		}

		// TODO ExitSuccess, ExitCode

		return text2;
	}

	@Override
	public void setEvaNode(final EvaNode a_evaNode) {
		_evaNode = a_evaNode;
	}

	public void setItem(final CR_ReferenceItem aItem) {

		item = aItem;
	}
}
