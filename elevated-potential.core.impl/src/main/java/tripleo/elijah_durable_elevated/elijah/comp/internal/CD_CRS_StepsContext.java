package tripleo.elijah_durable_elevated.elijah.comp.internal;

//import lombok.Getter;
import lombok.Getter;
import tripleo.elijah.comp.graph.i.CK_AbstractStepsContext;
import tripleo.elijah.comp.i.CB_Output;

@Getter
class CD_CRS_StepsContext extends CK_AbstractStepsContext {
	private final CR_State  state;
	private final CB_Output output;

	public CD_CRS_StepsContext(final CR_State aState, final CB_Output aOutput) {
		state  = aState;
		output = aOutput;
	}

	public CR_State getState() {
		// 24/01/04 back and forth
		return this.state;
	}

	public CB_Output getOutput() {
		// 24/01/04 back and forth
		return this.output;
	}

	//void addOutputString(CB_OutputString os);
	//void addDiagnostic(Diagnostic d);
}
