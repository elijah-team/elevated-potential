package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.util.*;
import tripleo.util.buffer.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class C2C_CodeForMethod implements Generate_Code_For_Method.C2C_Results {
	private final GenerateResult           gr;
	private final Generate_Code_For_Method generateCodeForMethod;
//	private final GenerateResultEnv        fileGen;
	private final WhyNotGarish_Function    whyNotGarishFunction;
	private       boolean                  _calculated;
	private       C2C_Result               buf;
	private       C2C_Result               bufHdr;

	public C2C_CodeForMethod(final @NotNull Generate_Code_For_Method aGenerateCodeForMethod,
	                         final @NotNull DeducedBaseEvaFunction aDeducedBaseEvaFunction,
							 final GenerateResultEnv aFileGen) {
		generateCodeForMethod = aGenerateCodeForMethod;
//		fileGen               = aFileGen;
		gr                    = aFileGen.gr();

		final GenerateC gc = aGenerateCodeForMethod._gc();
		whyNotGarishFunction = aDeducedBaseEvaFunction.getWhyNotGarishFunction(gc);//gc.a_lookup(aGf);
	}

	private void calculate() {
		if (!_calculated) {
			final BufferTabbedOutputStream tos    = generateCodeForMethod.tos;
			final BufferTabbedOutputStream tosHdr = generateCodeForMethod.tosHdr;


			final Generate_Method_Header gmh = new Generate_Method_Header(whyNotGarishFunction.cheat(), generateCodeForMethod._gc(), generateCodeForMethod.LOG);

			tos.put_string_ln(String.format("%s {", gmh.header_string));
			tosHdr.put_string_ln(String.format("%s;", gmh.header_string));

			generateCodeForMethod.action_invariant(whyNotGarishFunction, gmh);

			tos.flush();
			tos.close();
			generateCodeForMethod.tosHdr.flush();
			generateCodeForMethod.tosHdr.close();
			final Buffer buf1    = tos.getBuffer();
			final Buffer bufHdr1 = generateCodeForMethod.tosHdr.getBuffer();

			buf    = new Default_C2C_Result(buf1, GenerateResult.TY.IMPL, "C2C_CodeForMethod IMPL", whyNotGarishFunction);
			bufHdr = new Default_C2C_Result(bufHdr1, GenerateResult.TY.HEADER, "C2C_CodeForMethod HEADER", whyNotGarishFunction);

			_calculated = true;
		}
	}

	public GenerateResult getGenerateResult() {
		return gr;
	}

	@Override
	public @NotNull List<C2C_Result> getResults() {
		calculate();
		return List_of(buf, bufHdr);
	}
}
