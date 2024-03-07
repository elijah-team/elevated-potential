package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Helpers0;

import java.io.*;

/*
 * Created on Aug 30, 2005 9:05:24 PM
 */
public class RegularTypeNameImpl extends AbstractTypeName2 implements NormalTypeName, RegularTypeName { // TODO 10/15 both?
	private @Nullable Context _ctx;
	// private OS_Type _resolved;
	private OS_Element _resolvedElement;
	private TypeNameList genericPart;

	@Deprecated
	public RegularTypeNameImpl() { // TODO remove this
		super();
		_ctx = null;
	}

	public RegularTypeNameImpl(final Context cur) {
		super();
		_ctx = cur;
	}

	/*
	 * Null context. Possibly only for testing.
	 */
	public static @NotNull RegularTypeName makeWithStringTypeName(@NotNull final String aTypeName) {
		final RegularTypeName R = new RegularTypeNameImpl(null);
		R.setName(Helpers0.string_to_qualident(aTypeName));
		return R;
	}

	@Override
	public void addGenericPart(final TypeNameList tn2) {
		genericPart = tn2;
	}

	@Override
	public int getColumn() {
		return getRealName().parts().get(0).getColumn();
	}

	// TODO what about generic part
	@Override
	public int getColumnEnd() {
		return getRealName().parts().get(getRealName().parts().size()).getColumnEnd();
	}

	@Override
	public boolean nameEquals(final String aName) {
		throw new UnintendedUseException("niy");
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public File getFile() {
		return getRealName().parts().get(0).getFile();
	}

	@Override
	public TypeNameList getGenericPart() {
		return genericPart;
	}

	@Override
	public int getLine() {
		return getRealName().parts().get(0).getLine();
	}

	// TODO what about generic part
	@Override
	public int getLineEnd() {
		return getRealName().parts().get(getRealName().parts().size()).getLineEnd();
	}

	@Override
	public @Nullable String getName() {
		if (typeName == null)
			return null;
		return this.typeName.asSimpleString();
	}

	@Override
	public Qualident getRealName() {
		return typeName;
	}

	@Override
	public OS_Element getResolvedElement() {
		return _resolvedElement;
	}

	@Override
	public boolean hasResolvedElement() {
		return _resolvedElement != null;
	}

	// region Locatable

	@Override
	public @NotNull Type kindOfType() {
		return Type.NORMAL;
	}

	@Override
	public void setContext(final Context ctx) {
		_ctx = ctx;
	}

	@Override
	public void setName(final Qualident aS) {
		this.typeName = aS;
	}

	@Override
	public void setResolvedElement(final OS_Element element) {
		_resolvedElement = element;
	}

	@Override
	public @NotNull String toString() {
		return asString();
	}

	//@Override
	public @NotNull String asString() {
		final StringBuilder sb = new StringBuilder();
		for (final TypeModifiers modifier : _ltm) {
			switch (modifier) {
			case CONST:
				sb.append("const ");
				break;
			case REFPAR:
				sb.append("ref ");
				break;
			case FUNCTION:
				sb.append("fn ");
				break; // TODO
			case PROCEDURE:
				sb.append("proc ");
				break; // TODO
			case GC:
				sb.append("gc ");
				break;
			case ONCE:
				sb.append("once ");
				break;
			case INPAR:
				sb.append("in ");
				break;
			case LOCAL:
				sb.append("local ");
				break;
			case MANUAL:
				sb.append("manual ");
				break;
			case OUTPAR:
				sb.append("out ");
				break;
			case POOLED:
				sb.append("pooled ");
				break;
			case TAGGED:
				sb.append("tagged ");
				break;
			case GENERIC:
				sb.append("generic ");
				break; // TODO
			case NORMAL:
				break;
			default:
				throw new IllegalStateException("Cant be here!");
			}
		}

		final String s;
		if (typeName != null) {
			if (genericPart != null) {
				s = String.format("%s[%s]", getName(), genericPart.toString());
			} else {
				s = getName();
			}
		} else {
			s = "<RegularTypeName empty>";
		}
		sb.append(s);

		return sb.toString();
	}

	// endregion
}
