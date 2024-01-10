/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.*;
import tripleo.elijah.util.*;
import tripleo.elijah.util2.UnintendedUseException;

import java.io.*;
import java.util.*;

/**
 * @author Tripleo(sb)
 * <p>
 * Created Apr 1, 2019 at 3:21:26 PM
 */
public class IdentExpressionImpl implements IdentExpression {

	private final @NotNull EN_Name  name;
	private @NotNull       Attached _a;
	private @NotNull       Token    text;
	private @Nullable      String   _fileName;

	public IdentExpressionImpl(final @NotNull Token r1, @NotNull String aFilename) {
		this.text = r1;
		this._a   = new AttachedImpl(null);

		this.name = EN_Name.create(text.getText());

		this._fileName = aFilename;
	}

	public IdentExpressionImpl(final @NotNull Token r1, @NotNull String aFilename, final @NotNull Context cur) {
		this.text = r1;
		this._a   = new AttachedImpl(cur);
		setContext(cur);

		this.name = EN_Name.create(text.getText());
		cur.addName(name);

		this._fileName = aFilename;
	}

	public IdentExpressionImpl(final @NotNull Token r1, final @NotNull Context cur) {
		this.text = r1;
		this._a   = new AttachedImpl(cur);
		//setContext(cur);

		this.name = EN_Name.create(text.getText());
		cur.addName(name);

		this._fileName = null;
	}

	@Contract("_ -> new")
	public static @NotNull IdentExpression forString(String string) {
		return new IdentExpressionImpl(Helpers0.makeToken(string), "<inline-absent2>");
	}

	public @NotNull List<FormalArgListItem> getArgs() {
		throw new UnintendedUseException();
	}

	public void setArgs(final ExpressionList ael) {
		throw new UnintendedUseException();
	}

	@Override
	public int getColumn() {
		return token().getColumn();
	}

	@Override
	public int getColumnEnd() {
		return token().getColumn();
	}

	@Override
	public Context getContext() {
		return _a.getContext();
	}

	@Override
	public void setContext(final Context cur) {
		_a.setContext(cur);
	}

	@Override
	public @NotNull File getFile() {
		return new File(_fileName);
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.IDENT;
	}

	@Override
	public void setKind(final @NotNull ExpressionKind aExpressionKind) {
		// log and ignore
		SimplePrintLoggerToRemoveSoon
				.println_err_2("Trying to set ExpressionType of IdentExpression to " + aExpressionKind.toString());
	}

	@Override
	public @NotNull IExpression getLeft() {
		return this;
	}

	@Override
	public void setLeft(final @NotNull IExpression iexpression) {
////		if (iexpression instanceof IdentExpression) {
////			text = ((IdentExpression) iexpression).text;
////		} else {
////			// NOTE was tripleo.elijah.util.Stupidity.println_err_2
//		throw new IllegalArgumentException("Trying to set left-side of IdentExpression to " + iexpression.toString());
////		}
		throw new UnintendedUseException();
	}

	@Override
	public String asString() {
		return getText();
	}

	@Override
	public int getLine() {
		return token().getLine();
	}

	@Override
	public int getLineEnd() {
		return token().getLine();
	}

	@Override
	public EN_Name getName() {
		return name;
	}

	@Override
	public OS_Element getParent() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
//		return null;
	}

	@Override
	public @NotNull String getText() {
		return text.getText();
	}

	public OS_Type getType() {
		throw new UnintendedUseException();
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return String.format("IdentExpression(%s)", text.getText());
	}

	@Override
	public void serializeTo(@NotNull SmallWriter sw) {
		sw.fieldString("fileName", _fileName);
		sw.fieldToken("text", text);
		sw.fieldInteger("line", getLine());
		sw.fieldInteger("column", getColumn());
	}

	public Token token() {
		return text;
	}

	/**
	 * same as getText()
	 */
	@Override
	public @NotNull String toString() {
		return asString();
	}

	// endregion

	@Override
	public void visitGen(final tripleo.elijah.lang2.@NotNull ElElementVisitor visit) {
		visit.visitIdentExpression(this);
	}

}

//
//
//
