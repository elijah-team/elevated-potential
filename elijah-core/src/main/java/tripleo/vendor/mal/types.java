package tripleo.vendor.mal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class types {
	public interface ILambda {
		MalVal apply(MalList args) throws MalThrowable;
	}
	public static class MalAtom extends MalVal {
		MalVal value;

		public MalAtom(final MalVal value) {
			this.value = value;
		}

		public @NotNull MalAtom copy() throws MalThrowable {
			return new MalAtom(value);
		}

		@Override
		public @NotNull String toString() {
			return "(atom " + printer._pr_str(value, true) + ")";
		}

		public @NotNull String toString(final Boolean print_readably) {
			return "(atom " + printer._pr_str(value, print_readably) + ")";
		}
	}
	public static class MalConstant extends MalVal {
		String value;

		public MalConstant(final String name) {
			value = name;
		}

		public @NotNull MalConstant copy() throws MalThrowable {
			return this;
		}

		public String toString() {
			return value;
		}
	}

	public static class MalContinue extends MalThrowable {
	}

	//
	// General functions
	//

	public static class MalError extends MalThrowable {
		public MalError(final String msg) {
			super(msg);
		}
	}

	// Thrown by throw function
	public static class MalException extends MalThrowable {
		MalVal value;

		public MalException(final MalVal value) {
			this.value = value;
		}

		public MalException(final String value) {
			this.value = new MalString(value);
		}

		public MalVal getValue() {
			return value;
		}
	}

	public static abstract class MalFunction extends MalVal
			implements ILambda, java.lang.Cloneable {
		public @Nullable MalVal                               ast    = null;
		public           tripleo.vendor.mal.env.@Nullable Env env    = null;
		public @Nullable MalList                              params = null;
		public           Boolean                              macro  = false;

		public MalFunction() {
		}

		public MalFunction(final MalVal ast, final tripleo.vendor.mal.env.Env env, final MalList params) {
			this.ast    = ast;
			this.env    = env;
			this.params = params;
		}

		public @NotNull MalFunction copy() throws MalThrowable {
			try {
				// WARNING: clone() is broken:
				//   http://www.artima.com/intv/bloch13.html
				// However, this doesn't work:
				//   MalFunction new_mf = this.getClass().newInstance();
				// So for now it's clone.
				final MalFunction new_mf = (MalFunction) this.clone();
				new_mf.ast    = ast;
				new_mf.env    = env;
				new_mf.params = params;
				new_mf.macro  = macro;
				return new_mf;
			} catch (final Throwable t) {
				// not much we can do
				t.printStackTrace();
				throw new MalError("Could not copy MalFunction: " + this);
			}
		}

		public tripleo.vendor.mal.env.@NotNull Env genEnv(final @NotNull MalList args) {
			return new env.Env(env, params, args);
		}

		public MalVal getAst() {
			return ast;
		}

		public tripleo.vendor.mal.env.Env getEnv() {
			return env;
		}

		public MalList getParams() {
			return params;
		}

		public Boolean isMacro() {
			return macro;
		}

		public void setMacro() {
			macro = true;
		}
	}

	public static class MalHashMap extends MalVal {
		Map value;

		public MalHashMap(final @NotNull MalList lst) {
			value = new HashMap<String, MalVal>();
			assoc_BANG(lst);
		}

		public MalHashMap(final MalVal... mvs) {
			value = new HashMap<String, MalVal>();
			assoc_BANG(mvs);
		}

		public MalHashMap(final Map val) {
			value = val;
		}

		public @NotNull Set _entries() {
			return value.entrySet();
		}

		public @NotNull MalHashMap assoc_BANG(final @NotNull MalList lst) {
			for (Integer i = 0; i < lst.value.size(); i += 2) {
				value.put(((MalString) lst.nth(i)).getValue(),
						  lst.nth(i + 1));
			}
			return this;
		}

		public @NotNull MalHashMap assoc_BANG(final MalVal @NotNull ... mvs) {
			for (Integer i = 0; i < mvs.length; i += 2) {
				value.put(((MalSymbol) mvs[i]).getName(),
						  mvs[i + 1]);
			}
			return this;
		}

		public @NotNull MalHashMap copy() throws MalThrowable {
			final Map<String, MalVal> shallowCopy = new HashMap<String, MalVal>();
			shallowCopy.putAll(value);
			final MalHashMap new_hm = new MalHashMap(shallowCopy);
			meta = meta;
			return new_hm;
		}

		public @NotNull MalHashMap dissoc_BANG(final @NotNull MalList lst) {
			for (Integer i = 0; i < lst.value.size(); i++) {
				value.remove(((MalString) lst.nth(i)).getValue());
			}
			return this;
		}

		public @NotNull Integer size() {
			return value.size();
		}

		@Override
		public @NotNull String toString() {
			return "{" + printer.join(value, " ", true) + "}";
		}

		public @NotNull String toString(final Boolean print_readably) {
			return "{" + printer.join(value, " ", print_readably) + "}";
		}
	}

	public static class MalInteger extends MalVal {
		Integer value;

		public MalInteger(final Integer v) {
			value = v;
		}

		public @NotNull MalInteger add(final @NotNull MalInteger other) {
			return new MalInteger(value + other.getValue());
		}

		public @NotNull MalInteger copy() throws MalThrowable {
			return this;
		}

		public @NotNull MalInteger divide(final @NotNull MalInteger other) {
			return new MalInteger(value / other.getValue());
		}

		public Integer getValue() {
			return value;
		}

		public MalConstant gt(final @NotNull MalInteger other) {
			return (value > other.getValue()) ? True : False;
		}

		public MalConstant gte(final @NotNull MalInteger other) {
			return (value >= other.getValue()) ? True : False;
		}

		public MalConstant lt(final @NotNull MalInteger other) {
			return (value < other.getValue()) ? True : False;
		}

		public MalConstant lte(final @NotNull MalInteger other) {
			return (value <= other.getValue()) ? True : False;
		}

		public @NotNull MalInteger multiply(final @NotNull MalInteger other) {
			return new MalInteger(value * other.getValue());
		}

		public @NotNull MalInteger subtract(final @NotNull MalInteger other) {
			return new MalInteger(value - other.getValue());
		}

		@Override
		public @NotNull String toString() {
			return value.toString();
		}
	}

	public static class MalList extends MalVal {
		@NotNull String start = "(", end = ")";
		List value;

		public MalList(final List val) {
			value = val;
		}

		public MalList(final MalVal... mvs) {
			value = new ArrayList<MalVal>();
			conj_BANG(mvs);
		}

		public @NotNull MalList conj_BANG(final MalVal... mvs) {
			Collections.addAll(value, mvs);
			return this;
		}

		public @NotNull MalList copy() throws MalThrowable {
			final MalList new_ml = new MalList();
			new_ml.value.addAll(value);
			meta = meta;
			return new_ml;
		}

		public List getList() {
			return value;
		}

		public @NotNull Boolean list_Q() {
			return true;
		}

		public MalVal nth(final Integer idx) {
			return (MalVal) value.get(idx);
		}

		public @NotNull MalList rest() {
			if (size() > 0) {
				return new MalList(value.subList(1, value.size()));
			} else {
				return new MalList();
			}
		}

		public @NotNull Integer size() {
			return value.size();
		}

		public MalList slice(final Integer start) {
			return slice(start, value.size());
		}

		public @NotNull MalList slice(final Integer start, final Integer end) {
			return new MalList(value.subList(start, end));
		}

		@Override
		public @NotNull String toString() {
			return start + printer.join(value, " ", true) + end;
		}

		public @NotNull String toString(final Boolean print_readably) {
			return start + printer.join(value, " ", print_readably) + end;
		}
	}

	public static class MalString extends MalVal {
		String value;

		public MalString(final String v) {
			value = v;
		}

		public @NotNull MalString copy() throws MalThrowable {
			return this;
		}

		public String getValue() {
			return value;
		}

		@Override
		public @NotNull String toString() {
			return "\"" + value + "\"";
		}

		public @NotNull String toString(final Boolean print_readably) {
			if (value.length() > 0 && value.charAt(0) == '\u029e') {
				return ":" + value.substring(1);
			} else if (print_readably) {
				return "\"" + printer.escapeString(value) + "\"";
			} else {
				return value;
			}
		}
	}

	public static class MalSymbol extends MalVal {
		String value;

		public MalSymbol(final @NotNull MalString v) {
			value = v.getValue();
		}

		public MalSymbol(final String v) {
			value = v;
		}

		public @NotNull MalSymbol copy() throws MalThrowable {
			return this;
		}

		public String getName() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	//
	// Exceptions/Errors
	//
	public static class MalThrowable extends Exception {
		public MalThrowable() {
		}

		public MalThrowable(final String msg) {
			super(msg);
		}
	}

	//
	// Mal boxed types
	//
	abstract public static class MalVal {
		static MalVal meta = Nil;

		abstract public MalVal copy() throws MalThrowable;

		public MalVal getMeta() {
			return meta;
		}

		public @NotNull Boolean list_Q() {
			return false;
		}

		public void setMeta(final MalVal m) {
			meta = m;
		}

		// Default is just to call regular toString()
		public String toString(final Boolean print_readably) {
			return this.toString();
		}
	}

	public static class MalVector extends MalList {
		// Same implementation except for instantiation methods
		public MalVector(final List val) {
			value = val;
			start = "[";
			end   = "]";
		}

		public MalVector(final MalVal... mvs) {
			super(mvs);
			start = "[";
			end   = "]";
		}

		public @NotNull MalVector copy() throws MalThrowable {
			final MalVector new_mv = new MalVector();
			new_mv.value.addAll(value);
			meta = meta;
			return new_mv;
		}

		public @NotNull Boolean list_Q() {
			return false;
		}

		public @NotNull MalVector slice(final Integer start, final Integer end) {
			return new MalVector(value.subList(start, end));
		}
	}

	public static @NotNull MalConstant Nil   = new MalConstant("nil");

	public static @NotNull MalConstant True  = new MalConstant("true");

	public static @NotNull MalConstant False = new MalConstant("false");

	public static @NotNull Boolean _equal_Q(final @NotNull MalVal a, final @NotNull MalVal b) {
		final Class ota = a.getClass();
		final Class otb = b.getClass();
		if (!((ota == otb) ||
				(a instanceof MalList && b instanceof MalList))) {
			return false;
		} else {
			if (a instanceof MalInteger) {
				return ((MalInteger) a).getValue() ==
						((MalInteger) b).getValue();
			} else if (a instanceof MalSymbol) {
				return ((MalSymbol) a).getName().equals(
						((MalSymbol) b).getName());
			} else if (a instanceof MalString) {
				return ((MalString) a).getValue().equals(
						((MalString) b).getValue());
			} else if (a instanceof MalList) {
				if (((MalList) a).size() != ((MalList) b).size()) {
					return false;
				}
				for (Integer i = 0; i < ((MalList) a).size(); i++) {
					if (!_equal_Q(((MalList) a).nth(i),
								  ((MalList) b).nth(i))) {
						return false;
					}
				}
				return true;
			} else if (a instanceof final @NotNull MalHashMap mhm) {
				if (((MalHashMap) a).value.size() != ((MalHashMap) b).value.size()) {
					return false;
				}
				//HashMap<String,MalVal> hm = (HashMap<String,MalVal>)a.value;
				final HashMap<String, MalVal> hm = (HashMap<String, MalVal>) mhm.value;
				for (final String k : hm.keySet()) {
					if (!_equal_Q(((MalVal) ((MalHashMap) a).value.get(k)),
								  ((MalVal) ((MalHashMap) b).value.get(k)))) {
						return false;
					}
				}
				return true;
			} else {
				return a == b;
			}
		}
	}
}
