/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

/*
 * Created on Sep 2, 2005 2:08:03 PM
 */
package tripleo.elijah.lang.i;

public interface IExpression {

	// @NotNull List<FormalArgListItem> getArgs();
	//
	// void setArgs(ExpressionList ael);

	ExpressionKind getKind();

	IExpression getLeft();

	boolean is_simple();

	String repr_();

//	default boolean is_simple() {
//		switch(getKind()) {
//		case STRING_LITERAL:
//		case CHAR_LITERAL:
//		case NUMERIC:
//		case FLOAT:
//			return true;
//		default:
//			return false;
//		}
//	}

	void setKind(ExpressionKind aExpressionKind);

	void setLeft(IExpression iexpression);

}

//
//
//
