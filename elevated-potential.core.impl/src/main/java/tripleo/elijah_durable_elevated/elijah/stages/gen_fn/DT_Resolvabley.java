package tripleo.elijah_durable_elevated.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.IdentIA;

import java.util.LinkedList;
import java.util.List;

public class DT_Resolvabley {
	private final List<DT_Resolvable> x;

	public DT_Resolvabley(final List<DT_Resolvable> aX) {
		x = aX;
	}

	private static void logProgress(final int aI, final String z) {
		if (aI == 67) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("----- 67 Should be " + z);
		} else throw new Error();
	}

	public @NotNull String getNormalPath(final @NotNull BaseEvaFunction generatedFunction, final IdentIA identIA) {
		final List<String> rr = new LinkedList<>();

		for (DT_Resolvable resolvable : x) {
			final OS_Element element = resolvable.element();
			if (element == null && resolvable.deduceItem() instanceof FunctionInvocation fi) {
				final FunctionDef fd = fi.getFunction();
				rr.add("%s".formatted(fd.getNameNode().getText()));
				// rr.add("%s()".formatted(fd.getNameNode().getText()));
				continue;
			}

			if (element != null) {
				switch (DecideElObjectType.getElObjectType(element)) {
				case CLASS -> {
					var cs = (ClassStatement) element;
					if (resolvable.deduceItem() instanceof FunctionInvocation fi) {
						if (fi.getFunction() instanceof ConstructorDef cd) {
							rr.add("%s()".formatted(cs.getName()));
						}
					}
				}
				case FUNCTION -> {
					FunctionDef fd = (FunctionDef) element;
					if (resolvable.deduceItem() == null) {
						// when ~ is folders.forEach, this is null (fi not set yet)
						rr.add("%s".formatted(fd.getNameNode().getText()));
					} else if (resolvable.deduceItem() instanceof FunctionInvocation fi) {
						if (fi.getFunction() == fd) {
							rr.add("%s".formatted(fd.getNameNode().getText()));
//	    					rr.add("%s(...)".formatted(fd.getNameNode().getText()));
						}
					}
				}
				case VAR -> {
					VariableStatement vs = (VariableStatement) element;
					rr.add(vs.getName());
				}
				case FORMAL_ARG_LIST_ITEM -> {
					FormalArgListItem fali = (FormalArgListItem) element;
					rr.add(fali.name());
				}
				}
			} else if (resolvable.instructionArgument() instanceof IdentIA identIA2) {
				final IdentTableEntry ite = identIA2.getEntry();

				if (ite._callable_pte() != null) {
					final ProcTableEntry cpte = ite._callable_pte();

					assert cpte.status != BaseTableEntry.Status.KNOWN;

					rr.add("%s".formatted(ite.getIdent().getText()));
				}
			}
		}

		final String r = Helpers.String_join(".", rr);

		final String z = generatedFunction.getIdentIAPathNormal(identIA);

		// assert r.equals(z);
		if (!r.equals(z)) {
			// 08/13
			logProgress(67, z);
		}

		return r;
	}
}
