package tripleo.elijah_elevated_durable.world_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_elevateder.comp.notation.GN_PL_Run2;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.stages.inter.ModuleThing;
import tripleo.elijah_elevateder.nextgen.inputtree.EIT_ModuleInputImpl;
import tripleo.elijah_elevateder.world.i.WorldModule;

public class DefaultWorldModule implements WorldModule {
	private final OS_Module                                     mod;
	private final CompilationEnclosure                          ce;
	private final Eventual<GN_PL_Run2.GenerateFunctionsRequest> erq = new Eventual<>();
	private       ModuleThing                                   thing;
	private       GN_PL_Run2.GenerateFunctionsRequest           rq;

	public DefaultWorldModule(final OS_Module aMod, final @NotNull CompilationEnclosure ace) {
		mod = aMod;
		ce  = ace;
		final ModuleThing mt = ce.addModuleThing(mod);
		setThing(mt);
	}

	@Override
	public EIT_ModuleInput getEITInput() {
		return new EIT_ModuleInputImpl(mod, ce.getCompilation());
	}

	@Override
	public Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq() {
		return erq;
	}

	@Override
	public OS_Module module() {
		return mod;
	}

	@Override
	public GN_PL_Run2.GenerateFunctionsRequest rq() {
		return rq;
		// throw new NotImplementedException("Unexpected");
	}

	public void setRq(final GN_PL_Run2.GenerateFunctionsRequest aRq) {
		rq = aRq;
		// throw new NotImplementedException("Unexpected");

		erq.resolve(rq);
	}

	public void setThing(final ModuleThing aThing) {
		thing = aThing;
	}

	public ModuleThing thing() {
		return thing;
	}

	@Override
	public String toString() {
		return "DefaultWorldModule{%s}".formatted(mod.getFileName());
	}
}
