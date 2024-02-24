package tripleo.elijah.ci_impl;

import antlr.Token;
import tripleo.elijah.ci.*;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.io.File;

/**
 * @author Tripleo(sb)
 */
public class CiIdentExpressionImpl implements CiIdentExpression {
	private final Token text;

	public CiIdentExpressionImpl(final Token r1) {
		this.text = r1;
	}

	public CiIdentExpressionImpl(final Token aThissed, final Object aCur) {
		this(aThissed);
	}

	//public IdentExpressionImpl(final Token r1, final Context cur) {
	//	this.text = r1;
	//	setContext(cur);
	//}


	//public @NotNull List<FormalArgListItem> getArgs() {
	//	return null;
	//}


	@Override
	public int getColumn() {
		return token().getColumn();
	}

	@Override
	public int getColumnEnd() {
		return token().getColumn();
	}

	@Override
	public File getFile() {
		final String filename = token().getFilename();
		if (filename == null)
			return null;
		return new File(filename);
	}

	@Override
	public int getLine() {
		return token().getLine();
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.IDENT;
	}

	@Override
	public CiExpression getLeft() {
		return this;
	}

	@Override
	public int getLineEnd() {
		return token().getLine();
	}

	//@Override
	//public Context getContext() {
	//	return _a.getContext();
	//}

//	@Override
//	public OS_Element getParent() {
//		// TODO Auto-generated method stub
//		throw new NotImplementedException();
////		return null;
//	}

	//@Override
	//public void visitGen(final tripleo.elijah.lang2.ElElementVisitor visit) {
	//	visit.visitIdentExpression(this);
	//}

	//@Override
	//public OS_Element getResolvedElement() {
	//	return _resolvedElement;
	//}

	//@Override
	//public boolean hasResolvedElement() {
	//	return _resolvedElement != null;
	//}

	public void setArgs(final CiExpressionList ael) {

	}

	/**
	 * same as getText()
	 */
	@Override
	public String toString() {
		return getText();
	}

	@Override
	public String getText() {
		return text.getText();
	}

	//@Override
	//public void setContext(final Context cur) {
	//	_a.setContext(cur);
	//}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return String.format("IdentExpression(%s)", text.getText());
	}

	// region Locatable

	@Override
	public void setKind(final CiExpressionKind aIncrement) {
		// log and ignore
		SimplePrintLoggerToRemoveSoon
				.println_err_2("Trying to set ExpressionType of IdentExpression to " + aIncrement.toString());
	}

	public Token token() {
		return text;
	}

	@Override
	public void setLeft(final CiExpression iexpression) {
//		if (iexpression instanceof IdentExpression) {
//			text = ((IdentExpression) iexpression).text;
//		} else {
//			// NOTE was tripleo.elijah.util.Stupidity.println_err_2
		throw new IllegalArgumentException("Trying to set left-side of IdentExpression to " + iexpression.toString());
//		}
	}

	// endregion
}
