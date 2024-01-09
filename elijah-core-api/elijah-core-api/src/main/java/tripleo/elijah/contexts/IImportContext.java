package tripleo.elijah.contexts;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.ISearchList;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Module;

public interface IImportContext {

	Compilation0 compilation();

	Context getParent();

	LookupResultList lookup(String name, int level, LookupResultList Result, ISearchList alreadySearched, boolean one);

	OS_Module module();
	
}
