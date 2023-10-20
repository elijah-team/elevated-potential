/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.comp

import tripleo.elijah.comp.i.CB_Output
import tripleo.elijah.comp.internal.CR_State
import tripleo.elijah.comp.i.GPipelineMember

/**
 * //Created 8/21/21 10:10 PM
 * Created 10/18/23 13:00 ish
 */
/* // can't do interfaces?
interface PipelineMember {
	def abstract void run( CR_State aState,  CB_Output aOutput)  throws Exception
}
*/
abstract class PipelineMember implements GPipelineMember {
	def abstract void run( CR_State aState,  CB_Output aOutput)  throws Exception
	override abstract String finishPipeline_asString();
}
// https://youtrack.jetbrains.com/issue/IDEA-104897
// https://youtrack.jetbrains.com/issue/IDEA-168555
// https://youtrack.jetbrains.com/issue/IDEA-161285
// https://github.com/wanghuan9/intellij-plugin-save-action-tool


//
//
//
