package tripleo.elijah.stages;

import org.jdeferred2.*;
import tripleo.elijah.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.function.*;

/**
 * prob clj here, no??
 */
public class ESwitch {
	/**
	 * "asserting" garish has eva
	 */
	public static void flap(final WhyNotGarish_Constructor aWhyNotGarishConstructor, final EvaConstructor aGf) {
		assert false;
	}

	/**
	 * "asserting" eva has living
	 */
	public static void flap(final EvaClass aEvaClass, final LivingClass aLivingClass) {
		assert false;
	}

	/**
	 * have (base)EF, want Deduced
	 * <br/>
	 * NOTE: Use for getCode, not setCode
	 */
	public static void flep(final BaseEvaFunction key, final DoneCallback<DeducedBaseEvaFunction> cb) {
		final Eventual<DeducedBaseEvaFunction> x = befMap.computeIfAbsent(key, (BaseEvaFunction __k) -> new Eventual<>());
		x.then(cb);
	}

	public static void flep(final EvaClass key, final DoneCallback<DeducedEvaClass> cb) {
		final var x = ecMap.computeIfAbsent(key, (__k) -> new Eventual<>());
		x.then(cb);
	}

	public static void flep(final EvaConstructor key, final DoneCallback<DeducedEvaConstructor> cb) {
		final var x = econMap.computeIfAbsent(key, (__k) -> new Eventual<>());
		x.then(cb);
	}

	public static void flep(final EvaNamespace key, final DoneCallback<DeducedEvaNamespace> cb) {
		final var x = enMap.computeIfAbsent(key, (__k) -> new Eventual<>());
		x.then(cb);
	}

	/*
	 * I am ready to resolve this function
	 */
	public static void flup(final BaseEvaFunction key, final Supplier<DeducedBaseEvaFunction> aSupplier) {
		final var x = befMap.computeIfAbsent(key, (__k) -> new Eventual<>());
		x.resolve(aSupplier.get());
	}

	private static Map<BaseEvaFunction, Eventual<DeducedBaseEvaFunction>> befMap  = new HashMap<>();
	private static Map<EvaConstructor, Eventual<DeducedEvaConstructor>>   econMap = new HashMap<>();
	private static Map<EvaClass, Eventual<DeducedEvaClass>>               ecMap   = new HashMap<>();
	private static Map<EvaNamespace, Eventual<DeducedEvaNamespace>>       enMap   = new HashMap<>();
}
