package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AbstractExpression;
import tripleo.elijah.lang.impl.StatementWrapperImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.lang2.ElElementVisitor;

/**
 * Created 9/18/21 4:03 AM
 */
public class WrappedStatementWrapper extends StatementWrapperImpl implements OS_Element {
	private final VariableStatementImpl vs;
	private final @NotNull Wrapped wrapped;

	public WrappedStatementWrapper(final IExpression aExpression,
								   final Context aContext,
								   final OS_Element aParent,
								   final VariableStatementImpl aVariableStatement) {
		super(aExpression, aContext, aParent);
		vs      = aVariableStatement;
		wrapped = new Wrapped(aVariableStatement, aExpression);
	}

	@Override
	public @Nullable Context getContext() {
		throw new UnintendedUseException("niy");
	}

	@Override
	public @Nullable OS_Element getParent() {
		throw new UnintendedUseException("niy");
	}

	public VariableStatementImpl getVariableStatement() {
		return vs;
	}

	public Wrapped getWrapped() {
		return wrapped;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnintendedUseException("niy");
	}

	@Override
	public void visitGen(ElElementVisitor visit) {
		throw new UnintendedUseException("niy");
	}

	class Wrapped extends AbstractExpression {
		private final IExpression expression;
		private final VariableStatementImpl variableStatement;

		public Wrapped(final VariableStatementImpl aVariableStatement, final IExpression aExpression) {
			variableStatement = aVariableStatement;
			expression        = aExpression;
		}

		@Override
		public boolean is_simple() {
			return expression.is_simple();
		}
	}
}
