package tripleo.elijah;

import clojure.lang.AFn;
import clojure.lang.AFunction;
import clojure.lang.Compiler;
import clojure.lang.IFn;
import clojure.lang.IObj;
import clojure.lang.Keyword;
import clojure.lang.Numbers;
import clojure.lang.PersistentList;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Tuple;
import clojure.lang.Util;
import clojure.lang.Var;

import java.util.Arrays;

@SuppressWarnings("ALL")
public final class clojure_support {
   public static class loading__6789__auto____1648 extends AFunction {
      public static final Var const__0 = RT.var("clojure.core", "refer");
      public static final AFn const__1 = Symbol.intern(null, "clojure.core");
      public static final Var const__2 = RT.var("clojure.core", "require");
      public static final AFn const__3 = (AFn) Tuple.create(Symbol.intern(null, "promesa.exec.csp"), RT.keyword(null, "as"), Symbol.intern(null, "sp"));

      @Override
      public Object invoke() {
         Var.pushThreadBindings(RT.mapUniqueKeys(new Object[]{Compiler.LOADER, this.getClass().getClassLoader()}));

         Object var1;
         try {
            ((IFn) const__0.getRawRoot()).invoke(const__1);
            var1 = ((IFn) const__2.getRawRoot()).invoke(const__3);
         } finally {
            Var.popThreadBindings();
         }

         return var1;
      }
   }

   public static final class el_make_chan extends AFunction {
      public static final Var const__0 = RT.var("promesa.exec.csp", "chan");

      public static Object invokeStatic() {
         return ((IFn)const__0.getRawRoot()).invoke();
      }

      @Override
      public Object invoke() {
         return invokeStatic();
      }
   }

   @SuppressWarnings("UnusedAssignment")
   public static final class el_nothing extends AFunction {
      public static final Var const__0 = RT.var("tripleo.elijah.clojure-support", "el-make-chan");
      public static final Var const__1 = RT.var("clojure.core", "vec");
      public static final Object const__2 = ((IObj)PersistentList.create(Arrays.asList(Symbol.intern(null, "c1"), Symbol.intern(null, "c2")))).withMeta(RT.map(new Object[]{RT.keyword(null, "line"), 57, RT.keyword(null, "column"), 21}));
      public static final Var const__3 = RT.var("tripleo.elijah.clojure-support", "el-run-loop");
      public static final Var const__4 = RT.var("promesa.exec.csp", ">!");
      public static final Var const__5 = RT.var("promesa.exec.csp", "close!");

      public static Object invokeStatic() {
         Object c1 = ((IFn)const__0.getRawRoot()).invoke();
         Object c2 = ((IFn)const__0.getRawRoot()).invoke();
         Object chans = ((IFn)const__1.getRawRoot()).invoke(const__2);
         IFn var10000 = (IFn)const__3.getRawRoot();
         Object var10001 = chans;
         chans = null;
         var10000.invoke(var10001);
         ((IFn)const__4.getRawRoot()).invoke(c1, "hi");
         ((IFn)const__4.getRawRoot()).invoke(c2, "there");
         var10000 = (IFn)const__5.getRawRoot();
         var10001 = c1;
         c1 = null;
         var10000.invoke(var10001);
         var10000 = (IFn)const__5.getRawRoot();
         var10001 = c2;
         c2 = null;
         return var10000.invoke(var10001);
      }

      @Override
      public Object invoke() {
         return invokeStatic();
      }
   }

   public static final class el_run_loop$fn__1655 extends AFunction {
      Object chans;
      Object control;
      public static final Var const__0 = RT.var("promesa.exec.csp", "alts!");
      public static final Var const__1 = RT.var("clojure.core", "cons");
      public static final Var const__6 = RT.var("clojure.core", "vector?");
      public static final Var const__7 = RT.var("clojure.core", "first");
      public static final Var const__8 = RT.var("clojure.core", "second");
      public static final Var const__9 = RT.var("clojure.core", "println");

      public el_run_loop$fn__1655(Object var1, Object var2) {
         this.chans = var1;
         this.control = var2;
      }

      @Override
      public Object invoke() {
         Object var10000;
         while(true) {
            Object vec__1656 = ((IFn)const__0.getRawRoot()).invoke(((IFn)const__1.getRawRoot()).invoke(this.control, this.chans));
            Object v = RT.nth(vec__1656, RT.intCast(0L), null);
            var10000 = vec__1656;
            vec__1656 = null;
            Object ch = RT.nth(var10000, RT.intCast(1L), null);
            if (v != null) {
               if (v != Boolean.FALSE) {
                  Object var10002;
                  Object var10004;
                  IFn var7;
                  if (!Util.equiv(ch, this.control)) {
                     var7 = (IFn)const__9.getRawRoot();
                     var10002 = v;
                     v = null;
                     var10004 = ch;
                     ch = null;
                     var7.invoke("Read", var10002, "from", var10004);
                     continue;
                  }

                  label44: {
                     Object and__5579__auto__1661 = ((IFn)const__6.getRawRoot()).invoke(v);
                     if (and__5579__auto__1661 != null) {
                        if (and__5579__auto__1661 != Boolean.FALSE) {
                           boolean and__5579__auto__1660 = Util.equiv(((IFn)const__7.getRawRoot()).invoke(v), "bye");
                           var10000 = and__5579__auto__1660 ? (Util.equiv(((IFn)const__8.getRawRoot()).invoke(v), null) ? Boolean.TRUE : Boolean.FALSE) : (and__5579__auto__1660 ? Boolean.TRUE : Boolean.FALSE);
                           break label44;
                        }
                     }

                     var10000 = and__5579__auto__1661;
                     and__5579__auto__1661 = null;
                  }

                  if (var10000 != null) {
                     if (var10000 != Boolean.FALSE) {
                        var10000 = null;
                        break;
                     }
                  }

                  var7 = (IFn)const__9.getRawRoot();
                  var10002 = v;
                  v = null;
                  var10004 = ch;
                  ch = null;
                  Object var6 = null;
                  var10000 = var7.invoke("Read", var10002, "from", var10004);
                  break;
               }
            }

            var10000 = null;
            break;
         }

         return var10000;
      }
   }

   public static final class el_run_mult$fn__1663 extends AFunction {
      long i;
      Object mx;
      public static final Var const__0 = RT.var("promesa.exec.csp", "chan");
      public static final Var const__1 = RT.var("promesa.exec.csp", "tap!");
      public static final Var const__2 = RT.var("clojure.core", "println");
      public static final Var const__3 = RT.var("clojure.core", "str");
      public static final Var const__4 = RT.var("promesa.exec.csp", "<!");
      public static final Var const__5 = RT.var("promesa.exec.csp", "close!");

      public el_run_mult$fn__1663(long var1, Object var3) {
         this.i = var1;
         this.mx = var3;
      }

      @Override
      public Object invoke() {
         Object ch = ((IFn)const__0.getRawRoot()).invoke();
         ((IFn)const__1.getRawRoot()).invoke(this.mx, ch);
         ((IFn)const__2.getRawRoot()).invoke(((IFn)const__3.getRawRoot()).invoke("go ", Numbers.num(this.i), ":"), ((IFn)const__4.getRawRoot()).invoke(ch));
         IFn var10000 = (IFn)const__5.getRawRoot();
         Object var10001 = ch;
         ch = null;
         Object var2 = null;
         return var10000.invoke(var10001);
      }
   }

   public static final class el_run_mult extends AFunction {
      public static final Var const__0 = RT.var("promesa.exec.csp", "mult");
      public static final Var const__5 = RT.var("promesa.core", "thread-call");
      public static final Var const__6 = RT.var("promesa.exec.csp", "*executor*");
      public static final Var const__7 = RT.var("promesa.exec", "wrap-bindings");
      public static final Var const__9 = RT.var("promesa.exec.csp", ">!");
      public static final Keyword const__10 = RT.keyword(null, "a");

      public static Object invokeStatic(Object chans) {
         Object mx = ((IFn)const__0.getRawRoot()).invoke();
         Object var10000 = chans;
         chans = null;
         long n__6088__auto__1666 = RT.count(var10000);

         for(long i = 0L; i < n__6088__auto__1666; ++i) {
            ((IFn)const__5.getRawRoot()).invoke(const__6.get(), ((IFn)const__7.getRawRoot()).invoke(new el_run_mult$fn__1663(i, mx)));
         }

         var10000 = null;
         IFn var6 = (IFn)const__9.getRawRoot();
         Object var10001 = mx;
         mx = null;
         return var6.invoke(var10001, const__10);
      }

      @Override
      public Object invoke(Object var1) {
         Object var10000 = var1;
         var1 = null;
         return invokeStatic(var10000);
      }
   }

   public static final class fn__1650 extends AFunction {
      public static final Var const__0 = RT.var("clojure.core", "commute");
      public static final Var const__1 = RT.var("clojure.core", "deref");
      public static final Var const__2 = RT.var("clojure.core", "*loaded-libs*");
      public static final Var const__3 = RT.var("clojure.core", "conj");
      public static final AFn const__4 = Symbol.intern(null, "tripleo.elijah.clojure-support");

      public static Object invokeStatic() {
         return ((IFn)const__0.getRawRoot()).invoke(((IFn)const__1.getRawRoot()).invoke(const__2), const__3.getRawRoot(), const__4);
      }

      @Override
      public Object invoke() {
         return invokeStatic();
      }
   }

   public static final class el_run_loop$fn__1653 extends AFunction {
      Object control;
      public static final Var const__0 = RT.var("promesa.exec.csp", "take!");
      public static final Var const__1 = RT.var("promesa.exec.csp", "timeout-chan");
      public static final Object const__2 = 5000L;
      public static final Var const__3 = RT.var("promesa.exec.csp", ">!");
      public static final AFn const__4 = (AFn)Tuple.create("bye", null);
      public static final Object const__5 = 1L;

      public el_run_loop$fn__1653(Object var1) {
         this.control = var1;
      }

      @Override
      public Object invoke() {
         ((IFn)const__0.getRawRoot()).invoke(((IFn)const__1.getRawRoot()).invoke(const__2));
         ((IFn)const__3.getRawRoot()).invoke(this.control, const__4);
         return const__5;
      }
   }

   public static final class el_run_loop extends AFunction {
      public static final Var const__0 = RT.var("promesa.exec.csp", "chan");
      public static final Var const__1 = RT.var("promesa.core", "thread-call");
      public static final Var const__2 = RT.var("promesa.exec.csp", "*executor*");
      public static final Var const__3 = RT.var("promesa.exec", "wrap-bindings");
      public static final Object const__4 = 1L;

      public static Object invokeStatic(Object chans) {
         Object control = ((IFn)const__0.getRawRoot()).invoke();
         ((IFn)const__1.getRawRoot()).invoke(const__2.get(), ((IFn)const__3.getRawRoot()).invoke(new /*el_run_loop$*/fn__1653(control)));
         IFn var10000 = (IFn)const__1.getRawRoot();
         Object var10001 = const__2.get();
         IFn var10002 = (IFn)const__3.getRawRoot();
         Object var10005 = chans;
         chans = null;
         Object var10006 = control;
         control = null;
         var10000.invoke(var10001, var10002.invoke(new el_run_loop$fn__1655(var10005, var10006)));
         return const__4;
      }

      @Override
      public Object invoke(Object var1) {
         Object var10000 = var1;
         var1 = null;
         return invokeStatic(var10000);
      }

      public static final class fn__1653 extends AFunction {
         Object control;
         public static final Var const__0 = RT.var("promesa.exec.csp", "take!");
         public static final Var const__1 = RT.var("promesa.exec.csp", "timeout-chan");
         public static final Object const__2 = 5000L;
         public static final Var const__3 = RT.var("promesa.exec.csp", ">!");
         public static final AFn const__4 = (AFn)Tuple.create("bye", null);
         public static final Object const__5 = 1L;

         public fn__1653(Object var1) {
            this.control = var1;
         }

         @Override
         public Object invoke() {
            ((IFn)const__0.getRawRoot()).invoke(((IFn)const__1.getRawRoot()).invoke(const__2));
            ((IFn)const__3.getRawRoot()).invoke(this.control, const__4);
            return const__5;
         }
      }
   }
}
