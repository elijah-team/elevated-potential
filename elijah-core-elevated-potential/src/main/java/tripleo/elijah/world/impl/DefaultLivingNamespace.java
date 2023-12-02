package tripleo.elijah.world.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.world.i.*;

//import static com.ibm.j9ddr.StructureHeader.BlobID.node;

public class DefaultLivingNamespace implements LivingNamespace {
	private final EvaNamespace    node;
	private       GarishNamespace _garish;
	private       int             _code;
	private       boolean         _generatedFlag;

	@Contract(pure = true)
	public DefaultLivingNamespace(final EvaNamespace aNode) {
		node = aNode;
	}

	@Override
	public EvaNamespace evaNode() {
		return node;
	}

	@Override
	public int getCode() {
		return _code;
	}

	@Override
	public void setCode(final int aCode) {
		_code = aCode;
	}

	@Override
	public NamespaceStatement getElement() {
		return (NamespaceStatement) node.getElement();
	}

	@Override
	public @NotNull GarishNamespace getGarish() {
		if (_garish == null) {
			_garish = new GarishNamespace(this);
		}
		return _garish;
	}

	@Override
	public void generateWith(final GenerateResultSink aResultSink, final GarishNamespace aGarishNamespace, final GenerateResult aGr, final GenerateFiles aGenerateFiles) {
		if (!_generatedFlag) {
			var                             generateC = (GenerateC) aGenerateFiles;
			final GarishNamespace_Generator xg        = new GarishNamespace_Generator(evaNode());
			xg.provide(aResultSink, aGarishNamespace, aGr, generateC);

			_generatedFlag = true;
		}
	}
}
