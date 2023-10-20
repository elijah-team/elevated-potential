package tripleo.elijah.comp;

import tripleo.elijah.comp.i.*;

public interface GCompilationConfig {
	GStages stage();

	void setDo_out(boolean b);

	void setShowTree(boolean b);

	void setSilent(boolean b);

	void setStage(String s);
}
