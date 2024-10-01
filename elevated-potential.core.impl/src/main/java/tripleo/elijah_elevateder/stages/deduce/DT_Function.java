package tripleo.elijah_elevateder.stages.deduce;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;

import java.util.List;

interface DT_Function {
	List<VariableTableEntry> vte_list();

	List<IdentTableEntry> idte_list();

	List<ProcTableEntry> prte_list();

	BaseEvaFunction get();

	List<ConstantTableEntry> cte_list();

	void add_proc_table_listeners();

	void resolve_ident_table(Context aContext);

	void resolve_arguments_table(Context aContext);

	void do_vte(Context aContext, DeduceTypes2 aDeduceTypes2);

	void do_idte(Context aContext, DeduceTypes2 aDeduceTypes2);

	void do_prte(Context aContext, DeduceTypes2 aDeduceTypes2);

	void resolve_cte(Context aContext, DeduceTypes2 aDeduceTypes2);

	void onEnterFunction(Context aContext, DeduceTypes2 aDeduceTypes2);

	void onExitFunction2(@NotNull BaseEvaFunction generatedFunction, Context aFd_ctx, Context aContext, DeduceTypes2 aDeduceTypes2);

	void do_assign_normal(@NotNull BaseEvaFunction generatedFunction, @NotNull Context aFd_ctx,
	                      @NotNull Instruction instruction, @NotNull Context aContext, DeduceTypes2 aDeduceTypes2);

	void do_agnk(@NotNull BaseEvaFunction generatedFunction, @NotNull Instruction instruction, DeduceTypes2 aDeduceTypes2);

	void do_call(@NotNull BaseEvaFunction generatedFunction, @NotNull FunctionDef fd,
	             @NotNull Instruction instruction, @NotNull Context context, DeduceTypes2 aDeduceTypes2);

	void do_calls(@NotNull BaseEvaFunction generatedFunction, @NotNull Context fd_ctx,
	              @NotNull Instruction instruction, DeduceTypes2 aDeduceTypes2);

	void implement_is_a(@NotNull BaseEvaFunction gf, @NotNull Instruction instruction, DeduceTypes2 aDeduceTypes2);

	void implement_construct(BaseEvaFunction generatedFunction, Instruction instruction, Context aContext, DeduceTypes2 aDeduceTypes2);

	DT_Function prelude(@NotNull BaseEvaFunction generatedFunction, @NotNull FunctionDef fd, DeduceTypes2 aDeduceTypes2);

	void fix_tables(@NotNull BaseEvaFunction evaFunction, DeduceTypes2 aDeduceTypes2);

	void __post_vte_list_001(@NotNull BaseEvaFunction generatedFunction);

	void __post_vte_list_002(@NotNull BaseEvaFunction generatedFunction, Context fd_ctx, DeduceTypes2 aDeduceTypes2);

	void __post_deferred_calls(@NotNull BaseEvaFunction generatedFunction,
	                           @NotNull Context fd_ctx, DeduceTypes2 aDeduceTypes2);

	void deduce_generated_function_base(@NotNull BaseEvaFunction generatedFunction, @NotNull FunctionDef fd, DeduceTypes2 aDeduceTypes2);
}
