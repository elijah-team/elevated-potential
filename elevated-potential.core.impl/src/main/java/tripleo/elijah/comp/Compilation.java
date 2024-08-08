package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.graph.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.percy.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.comp_model.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_elevated.comp.backbone.*;

import java.util.*;

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

	CIS _cis();

	CompFactory con();

	ICompilationAccess3 getCompilationAccess3();

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

	CompilationRunner getRunner();

	CompilationConfig _cfg();

	CM_CompilerInput get(CompilerInput aInput);

	void ____m();

	void spi(Object aObject);

	class CompilationConfig implements GCompilationConfig {
		private boolean showTree = false; // FIXME make something out of this, i dare you
		private boolean silent   = false;

		@Override
		public void setSilent(final boolean b) {
			silent = b;
		}

		public ElLog.Verbosity verbosity() {
			return silent
					? ElLog_.Verbosity.SILENT
					: ElLog_.Verbosity.VERBOSE;
		}
	}

	CPX_Signals signals();
}
