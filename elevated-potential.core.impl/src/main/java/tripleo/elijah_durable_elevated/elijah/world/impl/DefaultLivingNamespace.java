package tripleo.elijah_durable_elevated.elijah.world.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah._ElTaggableMixin;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.stages.d.Stages;
import tripleo.elijah_durable_elevated.elijah.stages.garish.GarishNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.garish.GarishNamespace_Generator;
import tripleo.elijah_durable_elevated.elijah.stages.gen_c.GenerateC;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_durable_elevated.elijah.world.i.LivingCreatorSpec;
import tripleo.elijah_durable_elevated.elijah.world.i.LivingNamespace;

import java.util.Optional;
import java.util.function.Function;

public class DefaultLivingNamespace extends _ElTaggableMixin implements LivingNamespace {
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

	@Override
	public <T> Optional<T> getForStage(Stages stg) {
		// TODO Auto-generated method stub
		var r = getExt(stg.getClass());
		if (r == null)
			return Optional.empty();
		else
			return Optional.of((T) r); // FIXME 12/09 this too // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
	}

	@Override
	public <T> T getForStage(Stages stg, Function<LivingCreatorSpec, T> factory) {
		switch (stg) {
		case GARISH -> {
			final GarishNamespace r = new GarishNamespace(this);
			putExt(stg.getClass(), r);
			return (T) r; // FIXME 12/09 ugh // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		}
		default ->
			throw new IllegalArgumentException("Unexpected value: " + stg);
		}
	}
}
