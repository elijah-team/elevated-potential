/**
 *
 */
package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.nextgen.names.i.*;

/**
 * @author Tripleo
 *         <p>
 *         Created Mar 23, 2020 at 12:40:27 AM
 */
public interface OS_Element2 {

	EN_Name getEnName();

	/**
	 * The name of the element TODO Should this be a {@link IdentExpression}?
	 *
	 * @return a String
	 */
	@Contract(pure = true)
	String name();
}

//
//
//
