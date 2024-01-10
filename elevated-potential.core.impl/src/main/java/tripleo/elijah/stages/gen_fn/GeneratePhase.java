/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.i.ModuleListener;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.stages.logging.ElLog.Verbosity;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created 5/16/21 12:35 AM
 */
public class GeneratePhase implements ReactiveDimension, ModuleListener {
	private @NotNull final PipelineLogic   pipelineLogic;
	private @NotNull final IPipelineAccess pa;

	@Getter
	private final @NotNull ElLog_.Verbosity verbosity;
	@Getter
	private final @NotNull WorkManager      wm = new WorkManager__();
	private final @NotNull Map<OS_Module, GenerateFunctions> generateFunctions = new HashMap<OS_Module, GenerateFunctions>();
	@Getter
	private @Nullable ICodeRegistrar codeRegistrar;

	public GeneratePhase(ElLog_.Verbosity aVerbosity, final @NotNull IPipelineAccess aPa, PipelineLogic aPipelineLogic) {
		verbosity = aVerbosity;
		pipelineLogic = aPipelineLogic;
		pa = aPa;

		pa.getCompilationEnclosure().addReactiveDimension(this);
		// pa.getCompilationEnclosure().addModuleListener(this);
	}

	@Override
	public void close() {
		NotImplementedException.raise_stop();
	}

	@NotNull
	public GenerateFunctions getGenerateFunctions(@NotNull OS_Module mod) {
		final GenerateFunctions Result;
		if (generateFunctions.containsKey(mod)) {
			Result = generateFunctions.get(mod);
		} else {
			Result = new GenerateFunctions(mod, pipelineLogic, pa);
			generateFunctions.put(mod, Result);
		}
		return Result;
	}

	@Override
	public void listen(final @NotNull GWorldModule module) {
		//final GenerateFunctions x = getGenerateFunctions(module.module());
	}

	public void setCodeRegistrar(ICodeRegistrar aCodeRegistrar) {
		codeRegistrar = aCodeRegistrar;
	}

	public WorkManager getWm() {
		// 24/01/04 back and forth
		return this.wm;
	}

	public Verbosity getVerbosity() {
		// 24/01/04 back and forth
		return this.verbosity;
	}

	public ICodeRegistrar getCodeRegistrar() {
		// 24/01/04 back and forth
		return this.codeRegistrar;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
