package tripleo.elijah_elevated_durable.world_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah_elevateder.stages.garish.GarishClass;
import tripleo.elijah_elevateder.stages.garish.GarishClass_Generator;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateFiles;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.world.i.LivingClass;

public class DefaultLivingClass implements LivingClass {
	private final          ClassStatement _element;
	private final @NotNull EvaClass    _gc;
	private @Nullable      GarishClass _garish;

	private int     _code;
	private boolean _generatedFlag;

	public DefaultLivingClass(final @NotNull EvaClass aClass) {
		_element = aClass.getKlass();
		_gc      = aClass;
		_garish  = null;
	}

	@Override
	public EvaClass evaNode() {
		return _gc;
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
	public ClassStatement getElement() {
		return _element;
	}

	@Override
	@Contract(mutates = "this")
	public GGarishClass getGarish() {
		if (_garish == null) {
			_garish = new GarishClass(this);
		}

		return _garish;
	}

	@Override
	@Contract(mutates = "this")
	public void generateWith(final GGenerateResultSink   aResultSink,
							 final @NotNull GGarishClass aGarishClass,
							 final GGenerateResult       aGenerateResult,
							 final GenerateFiles aGenerateFiles) {
		if (_generatedFlag) { return; }

		final GenerateC generateC = (GenerateC) aGenerateFiles;
		final EvaClass              evaClass             = evaNode();
		final GarishClass_Generator garishClassGenerator = evaClass.generator();//new GarishClass_Generator(evaClass);

		garishClassGenerator.provide((GenerateResultSink) aResultSink,
									 (GarishClass) aGarishClass,
									 (GenerateResult) aGenerateResult,
									 generateC);

		_generatedFlag = true;
	}
}
