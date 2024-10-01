package tripleo.elijah_elevateder.stages.gen_c;

import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;
import tripleo.elijah_elevateder.stages.gen_fn.EvaContainerNC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNamespace;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNode;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_fluffy.util.BufferTabbedOutputStream;
import tripleo.util.buffer.Buffer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

class C2C_CodeForConstructor implements Generate_Code_For_Method.C2C_Results {
	final                  GenerateResult           gr;
	private final          Generate_Code_For_Method generateCodeForMethod;
	//	private final GenerateResultEnv             fileGen;
	//private final          EvaConstructor           gf;
	private final @NotNull WhyNotGarish_Constructor yf;
	EG_Statement st;
	private boolean    _calculated;
	private C2C_Result buf;
	private C2C_Result bufHdr;

	public C2C_CodeForConstructor(final Generate_Code_For_Method aGenerateCodeForMethod,
	                              final @NotNull GenerateResultEnv aFileGen,
	                              final @NotNull WhyNotGarish_Constructor aYf) {
		generateCodeForMethod = aGenerateCodeForMethod;

		this.yf = aYf;

		//gf = aYf.cheat();
//		fileGen = aFileGen;
		gr = aFileGen.gr();
	}

	@Override
	public @NotNull List<C2C_Result> getResults() {
		calculate();
		return List_of(buf, bufHdr);
	}

	private void calculate() {
		BufferTabbedOutputStream tos = generateCodeForMethod.tos;
		BufferTabbedOutputStream tosHdr = generateCodeForMethod.tosHdr;
		calculate(tos, tosHdr);
	}

	private void calculate(BufferTabbedOutputStream tos, BufferTabbedOutputStream tosHdr) {
		if (_calculated == false) {
			// TODO this code is only correct for classes and not meant for namespaces
			assert yf.declaringContext().isClassStatement();
			
			EvaNode genClass = yf.getGenClass();

			if (genClass instanceof EvaClass x) {
				switch (x.getKlass().getType()) {
				// Don't generate class definition for these three
				case INTERFACE:
				case SIGNATURE:
				case ABSTRACT:
					return;
				}
				final CClassDecl decl = new CClassDecl(x);
				decl.evaluatePrimitive();

				final String class_name = GenerateC.GetTypeName.forGenClass(x);
				final int    class_code = x.getCode();

				assert yf.cd() != null; // TODO 10/16 can we remove this?

				final String                           constructorName = yf.getConstructorNameText();
				final C2C_CodeForConstructor_Statement xx              = new C2C_CodeForConstructor_Statement(class_name, class_code, constructorName, decl, x);
				xx.getTextInto(tos); // README created because non-recursive interpreter
				this.st = xx;

				var gmh = new Generate_Method_Header(yf, generateCodeForMethod._gc(), generateCodeForMethod._gc().LOG);
				generateCodeForMethod.action_invariant(yf, gmh);

				tos.put_string_ln("return R;");
				tos.dec_tabs();

				assert !decl.prim;

				tos.put_string_ln(String.format("} // class %s%s", decl.prim ? "box " : "", x.getName()));
				tos.put_string_ln("");

				final String header_string = getHeaderString(x, class_name, class_code, constructorName);

				tosHdr.put_string_ln(String.format("%s;", header_string));

				tos.flush();
				tos.close();
				tosHdr.flush();
				tosHdr.close();

				final Buffer buf1    = tos.getBuffer();
				final Buffer bufHdr1 = tosHdr.getBuffer();

				buf    = new Default_C2C_Result(buf1, GenerateResult.TY.IMPL, "C2C_CodeForConstructor IMPL", yf);
				bufHdr = new Default_C2C_Result(bufHdr1, GenerateResult.TY.HEADER, "C2C_CodeForConstructor HEADER", yf);

				_calculated = true;
			}
		}
	}

	private String getHeaderString(final EvaClass x,
								   final String class_name,
								   final int class_code,
								   final String constructorName) {
		final String                 header_string;
		final Generate_Method_Header gmh         = new Generate_Method_Header(yf, generateCodeForMethod._gc(), generateCodeForMethod.LOG);
		final String                 args_string = gmh.args_string;

		// NOTE getGenClass is always a class or namespace, getParent can be a function
		final EvaContainerNC parent = (EvaContainerNC) yf.getGenClass();

		assert parent == x;

		if (parent instanceof EvaClass) {
			final String name = String.format("ZC%d%s", class_code, constructorName);
//				LOG.info("138 class_name >> " + class_name);
			header_string = String.format("%s* %s(%s)", class_name, name, args_string);
		} else if (parent instanceof EvaNamespace) {
			// TODO see note above
			final String name = String.format("ZNC%d", class_code);
//				EvaNamespace st = (EvaNamespace) parent;
//				LOG.info(String.format("143 (namespace) %s -> %s", st.getName(), class_name));
//				final String if_args = args_string.length() == 0 ? "" : ", ";
			// TODO vsi for namespace instance??
//				tos.put_string_ln(String.format("%s %s%s(%s* vsi%s%s) {", returnType, class_name, name, class_name, if_args, args));
			header_string = String.format("%s %s(%s)", class_name, name, args_string);
		} else {
			throw new IllegalStateException("generating a constructor for something not a class.");
//				final String name = String.format("ZC%d", class_code);
//				header_string = String.format("%s %s(%s)", class_name, name, args_string);
		}
		return header_string;
	}

}
