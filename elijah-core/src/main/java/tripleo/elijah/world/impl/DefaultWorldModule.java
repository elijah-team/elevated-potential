package tripleo.elijah.world.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.*;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.world.i.WorldModule;

public class DefaultWorldModule implements WorldModule {
	private final OS_Module   mod;
	private final CompilationEnclosure ce;
	private       ModuleThing thing;

	private GN_PL_Run2.GenerateFunctionsRequest rq;

	public DefaultWorldModule(final OS_Module aMod, final @NotNull CompilationEnclosure ace) {
		mod = aMod;
		ce = ace;
		final ModuleThing mt = ce.addModuleThing(mod);
		setThing(mt);
	}

	public void setThing(final ModuleThing aThing) {
		thing = aThing;
	}

	@Override
	public OS_Module module() {
		return mod;
	}

	@Override
	public EIT_ModuleInput input() {
		return null;
	}

	@Override
	public GN_PL_Run2.GenerateFunctionsRequest rq() {
		return rq;
		//throw new NotImplementedException("Unexpected");
	}

	@Override
	public EIT_ModuleInput getEITInput() {
		return new EIT_ModuleInput(mod, ce.getCompilation());
	}

	public ModuleThing thing() {
		return thing;
	}

	public void setRq(final GN_PL_Run2.GenerateFunctionsRequest aRq) {
		rq = aRq;
		//throw new NotImplementedException("Unexpected");

		erq.resolve(rq);
	}

	@Override
	public Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq() {
		return erq;
	}

	Eventual<GN_PL_Run2.GenerateFunctionsRequest> erq = new Eventual<>();

	@Override
	public String toString() {
		return "DefaultWorldModule{%s}".formatted(mod.getFileName());
	}
}
