/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_durable_elevated.elijah.lang.impl.*;

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
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable OS_Element getParent() {
		throw new UnintendedUseException();
	}

	public VariableStatementImpl getVariableStatement() {
		return vs;
	}

	public Wrapped getWrapped() {
		return wrapped;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnintendedUseException();
	}

	@Override
	public void visitGen(ElElementVisitor visit) {
		// TODO Auto-generated method stub
//		visit.visitStatementWrapper(this);
		throw new UnintendedUseException();
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

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
