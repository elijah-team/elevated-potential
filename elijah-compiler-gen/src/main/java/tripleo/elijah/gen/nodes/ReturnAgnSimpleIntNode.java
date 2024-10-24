/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.gen.nodes;

import tripleo.elijah.lang.i.*;

/**
 * @author Tripleo(sb)
 */
public class ReturnAgnSimpleIntNode {

	private final NumericExpression integer;

	public ReturnAgnSimpleIntNode(final NumericExpression integer) {
		// TODO Auto-generated constructor stub
		this.integer = integer;
	}

	public int getValue() {
		return integer.getValue();
	}
}
