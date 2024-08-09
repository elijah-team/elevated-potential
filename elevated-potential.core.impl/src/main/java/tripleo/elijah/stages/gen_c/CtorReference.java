/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_c;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.stages.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_c.c_ast1.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.*;

import java.util.*;

import static tripleo.elijah.stages.deduce.DeduceTypes2.*;

/**
 * Created 3/7/21 1:22 AM
 */
public class CtorReference {

	@NotNull
	List<CReference.Reference> refs = new ArrayList<CReference.Reference>();
	private EvaNode _resolved;
	private List<String> args;
	private String  ctorName = "";

	/**
	 * Call before you call build
	 *
	 * @param sl3
	 */
	public void args(List<String> sl3) {
		args = sl3;
	}

	public String build(@NotNull ClassInvocation aClsinv) {
		StringBuilder   sb          = new StringBuilder();
		final boolean[] open        = {false};
		boolean         needs_comma = false;

		String text = "";
		for (CReference.Reference ref : refs) {
			switch (ref.type) {
			case LOCAL:
				text = "vv" + ref.text;
				sb.append(text);
				break;
			case MEMBER:
				text = "->vm" + ref.text;
				sb.append(text);
				break;
			case INLINE_MEMBER:
				text = Emit.emit("/*2190*/") + ".vm" + ref.text;
				sb.append(text);
				break;
			case DIRECT_MEMBER:
				text = Emit.emit("/*1240*/") + "vsc->vm" + ref.text;
				sb.append(text);
				break;
			case FUNCTION, CONSTRUCTOR, PROPERTY_GET: {
				final String s = sb.toString();
				text = String.format("%s(%s", ref.text, s);
				sb      = new StringBuilder();
				open[0] = true;
				if (!s.equals(""))
					needs_comma = true;
				sb.append(text);
				break;
			}
			default:
				throw new IllegalStateException("Unexpected value: " + ref.type);
			}
		}
		{
			// Assuming constructor call
			final Eventual<Integer> codeP = new Eventual();
			if (_resolved != null) {
				ESwitch.flep(((EvaConstructor) _resolved), new DoneCallback<DeducedEvaConstructor>() {
					@Override
					public void onDone(final DeducedEvaConstructor result) {
						codeP.resolve(result.getCode());
					}
				});
			} else {
				codeP.resolve(-3);
			}
			if (!(codeP.isResolved())) {
				SimplePrintLoggerToRemoveSoon.println_err_2("** 32135 ClassStatement with 0 code " + aClsinv.getKlass());
			}

			final C_Assignment[] cas     = new C_Assignment[1];
			final StringBuilder  finalSb = sb;
			codeP.then(new DoneCallback<Integer>() {
				@Override
				public void onDone(final Integer code) {
					final String n = finalSb.toString();

					// TODO Garish(?)Constructor.calculateCtorName(?)/Code
					String text2 = String.format("ZC%d%s", code, ctorName); // TODO what about named constructors
					finalSb.append(" = ");
					finalSb.append(text2);
					finalSb.append("(");
					assert !open[0];
					open[0] = true;

					final C_ProcedureCall pc = new C_ProcedureCall();
					pc.setTargetName(text2);
					pc.setArgs(args);

					cas[0] = new C_Assignment();
					cas[0].setLeft(n);
					cas[0].setRight(pc);
				}
			});

			assert !codeP.isPending();

			return cas[0].getString();
		}

		/*
		 * if (needs_comma && args != null && args.size() > 0) sb.append(", "); if
		 * (open) { if (args != null) { sb.append(Helpers.String_join(", ", args)); }
		 * sb.append(")"); } return sb.toString();
		 */
	}

	public void getConstructorPath(@NotNull InstructionArgument ia2, @NotNull BaseEvaFunction gf) {
		final List<InstructionArgument> s = CReference._getIdentIAPathList(ia2);

		for (int i = 0, sSize = s.size(); i < sSize; i++) {
			InstructionArgument ia = s.get(i);
			if (ia instanceof IntegerIA) {
				// should only be the first element if at all
				assert i == 0;
				final VariableTableEntry vte = gf.getVarTableEntry(to_int(ia));

				final ConstructorPathOp op = IntegerIA_Ops.get((IntegerIA) ia, sSize).getConstructorPath();
				_resolved = op.getResolved();
				ctorName = op.getCtorName();

				addRef(vte.getName(), CReference.Ref.LOCAL);
			} else if (ia instanceof IdentIA) {
				final ConstructorPathOp op = IdentIA_Ops.get((IdentIA) ia).getConstructorPath();
				_resolved = op.getResolved();
				ctorName = op.getCtorName();

				addRef(((IdentIA) ia).getEntry().getIdent().getText(), CReference.Ref.LOCAL); // TDOO check correctness
			} else if (ia instanceof ProcIA) {
//				final ProcTableEntry prte = generatedFunction.getProcTableEntry(to_int(ia));
//				text = (prte.expression.getLeft()).toString();
////				assert i == sSize-1;
//				addRef(text, Ref.FUNCTION); // TODO needs to use name of resolved function
				throw new NotImplementedException();
			} else {
				throw new NotImplementedException();
			}
//			sl.add(text);
		}
	}

	void addRef(String text, CReference.Ref type) {
		refs.add(new CReference.Reference(text, type));
	}
}

//
//
//
