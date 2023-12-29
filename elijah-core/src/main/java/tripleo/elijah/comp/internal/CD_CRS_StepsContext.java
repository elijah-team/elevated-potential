package tripleo.elijah.comp.internal;

//import lombok.Getter;
import tripleo.elijah.comp.graph.i.CK_AbstractStepsContext;
import tripleo.elijah.comp.i.CB_Output;

class CD_CRS_StepsContext extends CK_AbstractStepsContext {
//	@Getter
	private final CR_State  state;
//	@Getter
	private final CB_Output output;

	public CD_CRS_StepsContext(final CR_State aState, final CB_Output aOutput) {
		state  = aState;
		output = aOutput;
	}

	//void addOutputString(CB_OutputString os);
	//void addDiagnostic(Diagnostic d);
}
