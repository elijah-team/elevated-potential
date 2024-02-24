package tripleo.elijah.ci_impl;

import antlr.Token;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiListExpression;
import tripleo.elijah.diagnostic.Locatable;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Created on Sep 1, 2005 8:28:55 PM
 */
public class CiListExpressionImpl extends CiAbstractExpression implements CiListExpression, Locatable {
	public SyntaxImpl syntax = new SyntaxImpl();

	CiExpressionList contents;

	@Override
	public int getColumn() {
		if (syntax.startToken != null)
			return syntax.startToken.getColumn();
		return 0;
	}

	@Override
	public int getColumnEnd() {
		if (syntax.endToken != null)
			return syntax.endToken.getColumn();
		return 0;
	}

	@Override
	public File getFile() {
		if (syntax.startToken != null) {
			String filename = syntax.startToken.getFilename();
			if (filename != null)
				return new File(filename);
		}
		return null;
	}

	// region Syntax

	@Override
	public void setContents(final CiExpressionList aList) {
		contents = aList;
	}

	@Override
	public int getLine() {
		if (syntax.startToken != null)
			return syntax.startToken.getLine();
		return 0;
	}

	// endregion

	// region Locatable

	@Override
	public int getLineEnd() {
		if (syntax.endToken != null)
			return syntax.endToken.getLine();
		return 0;
	}

	@Override
	public boolean is_simple() {
		return false;
	}

	@Override
	public String printableString() {
		throw new UnintendedUseException();
	}

	public class SyntaxImpl {
		List<Token> commas = new ArrayList<Token>();
		Token       endToken;
		Token       startToken;

		public void comma(Token t) {
			commas.add(t);
		}

		public void start_and_end(Token startToken, Token endToken) {
			this.startToken = startToken;
			this.endToken   = endToken;
		}
	}

	// endregion
}
