package tripleo.elijah.comp.i;

public interface CB_Monitor {
	void reportFailure(CB_Action aCBAction, CB_Output aCB_output);

	void reportSuccess(CB_Action aCBAction, CB_Output aCB_output);
}
