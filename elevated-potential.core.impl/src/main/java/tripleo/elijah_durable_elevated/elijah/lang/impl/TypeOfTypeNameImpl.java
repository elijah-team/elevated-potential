package tripleo.elijah_durable_elevated.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.g.GResolveError;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeduceTypes2;

import java.io.File;

/**
 * Created 8/16/20 7:42 AM
 */
public class TypeOfTypeNameImpl implements TypeName, tripleo.elijah.lang.i.TypeOfTypeName {
	private Context _ctx;
	private Qualident _typeOf;
	private TypeModifiers modifiers;

	public TypeOfTypeNameImpl(final Context cur) {
		_ctx = cur;
	}

	// TODO what about keyword
	@Override
	public int getColumn() {
		return _typeOf.parts().get(0).getColumn();
	}

	@Override
	public int getColumnEnd() {
		return _typeOf.parts().get(_typeOf.parts().size()).getColumnEnd();
	}

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public File getFile() {
		return _typeOf.parts().get(0).getFile();
	}

	// TODO what about keyword
	@Override
	public int getLine() {
		return _typeOf.parts().get(0).getLine();
	}

	@Override
	public int getLineEnd() {
		return _typeOf.parts().get(_typeOf.parts().size()).getLineEnd();
	}

	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public @NotNull Type kindOfType() {
		return Type.TYPE_OF;
	}

	@Override
	public TypeName resolve(final Context ctx, final GDeduceTypes2 deduceTypes2) throws GResolveError {
		return resolve(ctx, (DeduceTypes2) deduceTypes2);
	}

	// region Locatable

	public @Nullable TypeName resolve(@NotNull Context ctx, @NotNull DeduceTypes2 deduceTypes2) throws GResolveError {
		return deduceTypes2.HACKMAYBE_resolve_TypeName(this, ctx);
	}

	@Override
	public void set(final TypeModifiers modifiers_) {
		modifiers = modifiers_;
	}

	@Override
	public void setContext(final Context context) {
		_ctx = context;
	}

	@Override
	public Qualident typeOf() {
		return _typeOf;
	}

	@Override
	public void typeOf(final Qualident xy) {
		_typeOf = xy;
	}

	// endregion
}

//
//
//
