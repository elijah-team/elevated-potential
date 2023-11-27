package tripleo.elijah.comp.i;

import com.sun.source.tree.CompilationUnitTree;
import tripleo.elijah.comp.Compilation0;

public interface CB_Action {
	void execute(CB_Monitor monitor);

	String name();
}
