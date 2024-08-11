package tripleo.elijah.stages.gen_c;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.ci.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.util.buffer.*;

public class GCFM implements Reactivable {

	private Buffer buf;
	private Buffer bufHdr;
	private final @NotNull DeducedBaseEvaFunction gf;
	private final GenerateResult gr;

	public GCFM(final @NotNull List<C2C_Result> aRs, final @NotNull DeducedBaseEvaFunction aGf,
			final GenerateResult aGr) {
		gf = aGf;
		gr = aGr;

		for (C2C_Result r : aRs) {
			// TODO store a Map<TY, Buffer/*GRI??*/> in rs
			switch (r.ty()) {
			case HEADER -> buf = r.getBuffer();
			case IMPL -> bufHdr = r.getBuffer();
			default -> throw new IllegalStateException();
			}
		}

	}

	@Override
	public void respondTo(final ReactiveDimension aDimension) {
		if (aDimension instanceof GenerateC generateC) {
			final LibraryStatementPart lsp = gf.__accessLangModule().getLsp();

			final BaseEvaFunction gf1 = (BaseEvaFunction) gf.getCarrier();

			gr.addFunction(gf1, buf, GenerateResult.TY.IMPL, lsp);
			gr.addFunction(gf1, bufHdr, GenerateResult.TY.HEADER, lsp);

			var gr2 = generateC.generateResultProgressive();
			gr2.addFunction((EvaFunction) gf1, buf, GenerateResult.TY.IMPL);
			gr2.addFunction((EvaFunction) gf1, bufHdr, GenerateResult.TY.HEADER);
		}
	}
}
