package tripleo.elijah.comp.process;

import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;

public interface CPX_RunStepLoop {
	void notify_CPX_RunStepLoop(ErrSink aErrSink, EOT_OutputTree aOutputTree, CK_ObjectTree aObjectTree);
}
