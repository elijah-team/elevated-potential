package tripleo.elijah.comp.i;

public interface GStages {

	RuntimeProcess getProcess(final ICompilationAccess aCa, final GProcessRecord aPr);

	void writeLogs(final ICompilationAccess aCompilationAccess);

}
