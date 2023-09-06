package tripleo.elijah.world.impl;

//import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.stages.inter.ModuleThing;
import tripleo.elijah.world.i.WorldModule;

public class DefaultWorldModule implements WorldModule {
	private final OS_Module   mod;
	private       ModuleThing thing;

//	@Getter
	private GN_PL_Run2.GenerateFunctionsRequest rq;

	public DefaultWorldModule(final OS_Module aMod, final @NotNull CompilationEnclosure ce) {
		this(aMod);
		final ModuleThing mt = ce.addModuleThing(mod);
		setThing(mt);
	}

	public DefaultWorldModule(final OS_Module aMod, final GN_PL_Run2.GenerateFunctionsRequest aRq) {
		mod = aMod;
		rq  = aRq;
	}

	public DefaultWorldModule(final OS_Module aModule) {
		mod = aModule;
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
	}

	public void setRq(final GN_PL_Run2.GenerateFunctionsRequest aRq) {
		rq = aRq;
	}

	public ModuleThing thing() {
		return thing;
	}

	public void setThing(final ModuleThing aThing) {
		thing = aThing;
	}
}
