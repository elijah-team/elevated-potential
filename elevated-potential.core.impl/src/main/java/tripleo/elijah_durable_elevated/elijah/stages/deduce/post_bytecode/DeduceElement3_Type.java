package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import tripleo.elijah.lang.i.Context;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenType;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.TypeTableEntry;
import tripleo.elijah.util.Operation2;

/**
 * Also {@link DeduceType3}
 */
public interface DeduceElement3_Type {
	GenType genType();

	Operation2<GenType> resolved(Context ectx);

	TypeTableEntry typeTableEntry();
}
