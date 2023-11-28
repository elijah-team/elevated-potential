package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.*;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.graph.CM_Module;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;

// TODO 01/19 might be fluffy
public interface Compilation extends Compilation0 {
	LivingRepo world2();

	Operation<Ok> hasInstructions2(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	@Override
	IPipelineAccess pa();

	@Override
	Operation2<GWorldModule> findPrelude(String prelude_name);

	IPipelineAccess get_pa();

	void set_pa(IPipelineAccess a_pa);

	@Override
	CompilationEnclosure getCompilationEnclosure();

	CIS _cis();

	CompFactory con();

	void setIO(IO io);

	@NotNull
	FluffyComp getFluffy();

	Operation<Ok> hasInstructions(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	ModuleBuilder moduleBuilder();

	@Override
	CP_Paths paths();

	@Override
	void pushItem(CompilerInstructions aci);

	@Override
	Finally reports();

	void subscribeCI(@NotNull Observer<CompilerInstructions> aCio);

	@Override
	CompilationConfig cfg();

	@Override
	List<CompilerInput> getInputs();

	@Override
	void use(@NotNull CompilerInstructions compilerInstructions, USE_Reasoning aReasoning);

	LivingRepo world();

	@Override
	ElijahCache use_elijahCache();

	@Override
	void pushWork(PW_PushWork aInstance, PN_Ping aPing);

	CM_Module megaGrande(ElijahSpec aSpec, Operation2<OS_Module> aModuleOperation);

	class CompilationConfig implements GCompilationConfig {
		public          boolean do_out;
		public          boolean showTree = false;
		public          boolean silent   = false;

		@Override
		public void setDo_out(final boolean b) {
			do_out = b;
		}
		@Override
		public void setShowTree(final boolean b) {
			showTree = b;
		}

		@Override
		public void setSilent(final boolean b) {
			silent = b;
		}

	}
}
