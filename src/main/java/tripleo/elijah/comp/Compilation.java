package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.CM_Ez;
import tripleo.elijah.comp.graph.i.CM_Module;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.local.CPX_Signals;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pn.PN_Ping;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.GCompilationConfig;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.LivingRepo;

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

	void addCompilerInputWatcher(CN_CompilerInputWatcher aCNCompilerInputWatcher);

	void compilerInputWatcher_Event(CN_CompilerInputWatcher.e aEvent, CompilerInput aCompilerInput, Object aO);

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

	CM_Ez megaGrande(EzSpec aEzSpec);

	LCM_CompilerAccess getLCMAccess();

	ICompilationRunner getRunner();

	CompilationConfig _cfg();

	CM_CompilerInput get(CompilerInput aInput);

	void ____m();

	PW_Controller __pw_controller();

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
