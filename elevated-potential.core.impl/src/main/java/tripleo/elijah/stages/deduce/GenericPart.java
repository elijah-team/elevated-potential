package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.*;

class GenericPart {
	private final ClassStatement           classStatement;
	private final TypeName                 genericTypeName;

	public Eventual<NormalTypeName> normalTypeName() {
		return _p_NormalTypeName;
	}

	private final Eventual<NormalTypeName> _p_NormalTypeName = new Eventual<>();

	@Contract(pure = true)
	public GenericPart(final ClassStatement aClassStatement, final TypeName aGenericTypeName) {
		classStatement  = aClassStatement;
		genericTypeName = aGenericTypeName;
	}

	@Contract(pure = true)
	public @Nullable TypeNameList getGenericPartFromTypeName() {
		final NormalTypeName ntn = getGenericTypeName();
		if (ntn == null)
			return null;
		return ntn.getGenericPart();
	}

	@Contract(pure = true)
	private @Nullable NormalTypeName getGenericTypeName() {
		@Nullable NormalTypeName x = null;
		if (_p_NormalTypeName.isPending()) {
			final String s = "GenericPart " + classStatement.getEnName().getText();
			// README "dont" complain about kotlin here
			if (genericTypeName == null) {
				final Diagnostic d = Diagnostic.withMessage("1860", "Provided genericTypeName is null in " + s, Diagnostic.Severity.ERROR);
				_p_NormalTypeName.reject(d);
			} else {
				if (genericTypeName instanceof NormalTypeName) {
					x = (NormalTypeName) genericTypeName;
					_p_NormalTypeName.resolve((NormalTypeName) genericTypeName);
				} else {
					//noinspection DataFlowIssue
					x = null;
					final Diagnostic d = Diagnostic.withMessage("1861/2529", "Provided genericTypeName is not NormalTypeName in " +
																		s + ", is actually " + genericTypeName.getClass().getName()
							, Diagnostic.Severity.ERROR);
					_p_NormalTypeName.reject(d);
				}
			}
		}
		// README not using EE here because locally calculated
		return x;
	}

	@Contract(pure = true)
	public boolean hasGenericPart() {
		return !classStatement.getGenericPart().isEmpty();
	}
}
