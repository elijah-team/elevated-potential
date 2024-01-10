package tripleo.elijah;

import clojure.lang.AFn;
import clojure.lang.Compiler;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import clojure.lang.LockingTransaction;
import clojure.lang.PersistentList;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Tuple;
import clojure.lang.Var;

import java.util.Arrays;
import java.util.concurrent.Callable;

@SuppressWarnings("ALL")
public class clojure_support__init {
	public static Var const__0;
	public static AFn const__1;
	public static AFn const__2;
	public static Var const__3;
	public static AFn const__11;
	public static Var const__12;
	public static AFn const__15;
	public static Var const__16;
	public static AFn const__19;
	public static Var const__20;
	public static AFn const__23;

	static {
		__init0();
		Compiler.pushNSandLoader(RT.classForName("tripleo.elijah.clojure_support__init").getClassLoader());

		try {
			load();
		} catch (Throwable var1) {
			Var.popThreadBindings();
			//throw var1; // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		}

		//Var.popThreadBindings();
	}

	private static final String name = "tripleo.elijah.clojure-support";

	public static void load() throws Exception {
		((IFn) const__0.getRawRoot()).invoke(const__1);
		((IFn) (new clojure_support.loading__6789__auto____1648())).invoke();
		Object var10002;
		if (((Symbol) const__1).equals(const__2)) {
			var10002 = null;
		} else {
			LockingTransaction.runInTransaction((Callable) (new clojure_support.fn__1650()));
			var10002 = null;
		}

		Var var10003 = const__3;
		var10003.setMeta((IPersistentMap) const__11);
		var10003.bindRoot(new clojure_support.el_make_chan());
		Var var10004 = const__12;
		var10004.setMeta((IPersistentMap) const__15);
		var10004.bindRoot(new clojure_support.el_run_loop());
		Var var10005 = const__16;
		var10005.setMeta((IPersistentMap) const__19);
		var10005.bindRoot(new clojure_support.el_run_mult());
		Var var10006 = const__20;
		var10006.setMeta((IPersistentMap) const__23);
		var10006.bindRoot(new clojure_support.el_nothing());
		Object var10007 = null;
		Object var10008 = null;
	}

	public static void __init0() {
		const__0  = (Var) RT.var("clojure.core", "in-ns");
		const__1  = (AFn) Symbol.intern((String) null, name);
		const__2  = (AFn) Symbol.intern((String) null, "clojure.core");
		const__3  = (Var) RT.var(name, "el-make-chan");
		const__11 = (AFn) RT.map(new Object[]{RT.keyword((String) null, "arglists"), PersistentList.create(Arrays.asList(Tuple.create())), RT.keyword((String) null, "line"), 8, RT.keyword((String) null, "column"), 1, RT.keyword((String) null, "file"), "tripleo/elijah/clojure_support.clj"});
		const__12 = (Var) RT.var(name, "el-run-loop");
		const__15 = (AFn) RT.map(new Object[]{RT.keyword((String) null, "arglists"), PersistentList.create(Arrays.asList(Tuple.create(Symbol.intern((String) null, "chans")))), RT.keyword((String) null, "line"), 10, RT.keyword((String) null, "column"), 1, RT.keyword((String) null, "file"), "tripleo/elijah/clojure_support.clj"});
		const__16 = (Var) RT.var(name, "el-run-mult");
		const__19 = (AFn) RT.map(new Object[]{RT.keyword((String) null, "arglists"), PersistentList.create(Arrays.asList(Tuple.create(Symbol.intern((String) null, "chans")))), RT.keyword((String) null, "line"), 35, RT.keyword((String) null, "column"), 1, RT.keyword((String) null, "file"), "tripleo/elijah/clojure_support.clj"});
		const__20 = (Var) RT.var(name, "el-nothing");
		const__23 = (AFn) RT.map(new Object[]{RT.keyword((String) null, "arglists"), PersistentList.create(Arrays.asList(Tuple.create())), RT.keyword((String) null, "line"), 53, RT.keyword((String) null, "column"), 1, RT.keyword((String) null, "file"), "tripleo/elijah/clojure_support.clj"});
	}
}
