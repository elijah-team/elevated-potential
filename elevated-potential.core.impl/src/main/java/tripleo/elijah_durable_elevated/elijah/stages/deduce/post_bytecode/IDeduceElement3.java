/*
	package tripleo.elijah.stages.deduce.post_bytecode;
       
	import tripleo.elijah.stages.gen_fn.IdentTableEntry;
	import tripleo.elijah.stages.gen_fn.VariableTableEntry;
       
	public class DED_VTE implements DED {
		private final VariableTableEntry variableTableEntry;
       
		public DED_VTE(VariableTableEntry aVariableTableEntry) {
			variableTableEntry = aVariableTableEntry;
		}
       
		public VariableTableEntry getVariableTableEntry() {
			return variableTableEntry;
		}
       
		@Override
		public Kind kind() {
			return Kind.DED_Kind_VariableTableEntry;
		}
       
	}
*/
package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.FoundElement;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenType;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.IdentIA;

public interface IDeduceElement3 {
	enum DeduceElement3_Kind {
		CLASS, FUNCTION, GEN_FN__CTE, GEN_FN__GC_VTE, GEN_FN__ITE, GEN_FN__PTE,
		// ...,
		GEN_FN__VTE, NAMESPACE
	}

	DeduceTypes2 deduceTypes2();

	DED elementDiscriminator();

	BaseEvaFunction generatedFunction();

	GenType genType();

	OS_Element getPrincipal();

	/**
	 * how is this different from {@link DED.Kind} ??
	 *
	 * @return
	 */
	DeduceElement3_Kind kind();

	void resolve(Context aContext, final DeduceTypes2 dt2);

	void resolve(IdentIA aIdentIA, Context aContext, FoundElement aFoundElement);
}
