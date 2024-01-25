package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.CM_Ez;
import tripleo.elijah.comp.i.CompFactory;
import tripleo.elijah.comp.i.LCM_CompilerAccess;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.compiler_model.CM_Module;
import tripleo.elijah.g.GCompilationConfig;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah_elevated.comp.model.CM_ModelFactory;

import java.util.List;

// TODO 01/19 might be fluffy
public interface Compilation extends Compilation0 {
	CM_Module megaGrande(OS_Module aModule);

	LivingRepo world2();

	Operation<Ok> hasInstructions2(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	IPipelineAccess get_pa();

	void set_pa(IPipelineAccess a_pa);

	void addCompilerInputWatcher(CN_CompilerInputWatcher aCNCompilerInputWatcher);

	CIS _cis();

	CompFactory con();

	void setIO(IO io);

	Operation<Ok> hasInstructions(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	ModuleBuilder moduleBuilder();

	void subscribeCI(@NotNull Observer<CompilerInstructions> aCio);

	LivingRepo world();

	CM_Module megaGrande(ElijahSpec aSpec, Operation2<OS_Module> aModuleOperation);

	CM_Ez megaGrande(EzSpec aEzSpec);

	LCM_CompilerAccess getLCMAccess();

	ICompilationRunner getRunner();

	CompilationConfig _cfg();

	CM_CompilerInput get(CompilerInput aInput);

	void ____m();

	PW_CompilerController get_pw();

	CM_ModelFactory modelFactory();

	ICompilationAccess3 getCompilationAccess3();

	LCM lcm();

	void compilerInputWatcher_Event(CN_CompilerInputWatcher.e aEvent, CompilerInput aCompilerInput, Object aO);

	CPX_Signals signals();

	FluffyComp getFluffy();

	class CompilationConfig implements GCompilationConfig {
		private boolean silent   = false;

		@Override
		public void setSilent(final boolean b) {
			silent = b;
		}

		@Override
		public boolean getSilent() {
			return silent;
		}
	}

	//@Override CP_Paths paths();

	//@Override void pushItem(CompilerInstructions aci);

	//@Override Finally reports();

	//@Override List<CompilerInput> getInputs();

	//@Override CompilationConfig cfg();

	//@Override <Yi> void pushWork(PW_PushWork aInstance, PN_Ping<Yi> aPing);

	//@Override void use(@NotNull CompilerInstructions compilerInstructions, USE_Reasoning aReasoning);

	//@Override ElijahCache use_elijahCache();

	//@Override CompilationEnclosure getCompilationEnclosure();

	//@Override IPipelineAccess pa();

	//@Override Operation2<GWorldModule> findPrelude(String prelude_name);
}
