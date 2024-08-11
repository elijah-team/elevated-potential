package tripleo.elijah.stages.deduce;

import java.util.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;

interface DT_Function {
	List<VariableTableEntry> vte_list();

	List<IdentTableEntry> idte_list();

	List<ProcTableEntry> prte_list();

	BaseEvaFunction get();

	List<ConstantTableEntry> cte_list();

	void add_proc_table_listeners();

	void resolve_ident_table(final Context aContext);

	void resolve_arguments_table(Context aContext);
}
