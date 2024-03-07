package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.GenericTypeName;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.i.TypeModifiers;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.util.NotImplementedException;

import java.io.File;

/**
 * Created 8/16/20 7:42 AM
 */
public class GenericTypeNameImpl implements GenericTypeName {
	private final Context       _ctx;
	private       Qualident     _typeName;
	private       TypeName      constraint; //
	private       TypeModifiers modifiers;

	public GenericTypeNameImpl(final Context cur) {
		_ctx = cur;
	}

	@Override
	public int getColumn() {
		return _typeName.parts().get(0).getColumn();
	}

	@Override
	public int getColumnEnd() {
		return _typeName.parts().get(_typeName.parts().size() - 1).getColumnEnd();
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
		return _typeName.parts().get(0).getFile();
	}

	@Override
	public int getLine() {
		return _typeName.parts().get(0).getLine();
	}

	@Override
	public int getLineEnd() {
		return _typeName.parts().get(_typeName.parts().size() - 1).getLineEnd();
	}

	@Override
	public boolean isNull() {
		return _typeName == null;
	}

	// region Locatable

	@Override
	public @NotNull Type kindOfType() {
		return Type.GENERIC;
	}

	@Override
	public void set(final TypeModifiers modifiers_) {
		modifiers = modifiers_;
	}

	@Override
	public void setConstraint(final TypeName aConstraint) {
		constraint = aConstraint;
	}

	@Override
	public void setContext(final Context context) {
		throw new NotImplementedException();
	}

	@Override
	public String toString() {
		return "gen {" + _typeName.asSimpleString() + "}";
	}

	@Override
	public void typeName(final Qualident xy) {
		_typeName = xy;
	}

	// endregion
}
