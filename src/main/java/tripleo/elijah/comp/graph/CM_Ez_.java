package tripleo.elijah.comp.graph;

//import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.wrap.File;

import java.io.InputStream;
import java.util.function.Supplier;

// question: why don't you change things?
public class CM_Ez_ implements CM_Ez {
	private EzSpec                           spec;
	private Operation2<CompilerInstructions> cio;

	@Override
	public void advise(final EzSpec aSpec) {
		this.spec = aSpec;
	}

	@Override
	public void advise(final Operation2<CompilerInstructions> aCio) {
		cio = aCio;
	}

	@Override
	public void advise(final CK_ObjectTree aObjectTree) {
//		final Triple<EzSpec, Operation2<CompilerInstructions>, Object> T = Triple.of(spec, cio, this);
//		aObjectTree.asseverate(T, Asseverate.CI_SPECCED); // FIXMe/NOTE use elsewhere
		aObjectTree.asseverate(this, Asseverate.CI_PARSED);
	}

	@Override
	public EzSpec getSpec() {
		return spec;
	}

	public Operation2<CompilerInstructions> getCio() {
		return cio;
	}

	public EzSpec provisionalSpec(final @NotNull Operation<String> absolute1,
	                              final String file_name,
	                              final File file,
	                              final @Nullable Supplier<InputStream> s) {
		if (spec == null) {
			spec = new EzSpec() {
				@Override
				public @NotNull Operation<String> absolute1() {
					return absolute1;
				}

				@Override
				public String file_name() {
					return file_name;
				}

				@Override
				public File file() {
					return file;
				}

				@Override
				public @Nullable Supplier<InputStream> sis() {
					return s;
				}
			};
		}
		return spec;
	}
}
