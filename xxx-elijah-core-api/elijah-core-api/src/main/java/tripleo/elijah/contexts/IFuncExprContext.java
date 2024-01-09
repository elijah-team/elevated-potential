package tripleo.elijah.contexts;

import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.ISearchList;
import tripleo.elijah.lang.i.LookupResultList;

public interface IFuncExprContext extends IFunctionContext {

	Context getParent();

	LookupResultList lookup(String name, int level, LookupResultList Result, ISearchList alreadySearched, boolean one);

}
