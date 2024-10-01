package tripleo.elijah_elevateder.comp.i;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah_elevated.comp.pushwork.PW_CompilerController;
import tripleo.elijah_elevated_durable.comp.EDL_CIS;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;
import tripleo.elijah_elevated_durable.comp_model.CM_Ez;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevateder.comp.ModuleBuilder;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pn.PN_Ping;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.CM_Module;
import tripleo.elijah.g.GCompilationConfig;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah.fluffy.FluffyComp;
import tripleo.elijah_elevateder.world.i.LivingRepo;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_fluffy.util.*;

import java.util.List;

// TODO 01/19 might be fluffy
public interface Compilation extends Compilation0 {
	CM_Module megaGrande(OS_Module aModule);

	LivingRepo world2();

	Operation<Ok> hasInstructions2(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	@Override
	IPipelineAccess pa();

	@Override
	Operation2<GWorldModule> findPrelude(String prelude_name);

	IPipelineAccess get_pa();

	void set_pa(IPipelineAccess a_pa);

	void addCompilerInputWatcher(CN_CompilerInputWatcher aCNCompilerInputWatcher);

	void compilerInputWatcher_Event(CN_CompilerInputWatcher.e aEvent, CompilerInput aCompilerInput, Object aO);

	@Override
	CompilationEnclosure getCompilationEnclosure();

	EDL_CIS _cis();

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

	CM_Ez megaGrande(EzSpec aEzSpec);

	LCM_CompilerAccess getLCMAccess();

	EDL_CompilationRunner getRunner();

	CompilationConfig _cfg();

	CM_CompilerInput get(CompilerInput aInput);

	void ____m();

	PW_CompilerController get_pw();

	class CompilationConfig implements GCompilationConfig {
		public          boolean showTree = false;
		public          boolean silent   = false;

		@Override
		public void setSilent(final boolean b) {
			silent = b;
		}

	}

	CPX_Signals signals();
}
