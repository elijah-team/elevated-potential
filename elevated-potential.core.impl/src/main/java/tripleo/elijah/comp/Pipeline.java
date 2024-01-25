/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import tripleo.elijah.__Extensionable;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.g.GPipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 8/21/21 10:09 PM
 */
public class Pipeline implements GPipeline {
	private final List<PipelineMember> pls = new ArrayList<>();

	public void add(PipelineMember aPipelineMember) {
		pls.add(aPipelineMember);
	}

	public void run(final CR_State aSt, final CB_Output aOutput) throws Exception {
		for (final PipelineMember pl : pls) {
			pl.run(null, aOutput);
		}
	}

	public int size() {
		return pls.size();
	}
	
	public static class RP_Context_1 extends __Extensionable implements RP_Context {
		public RP_Context_1(CR_State aSt, CB_Output aOutput) {
			putExt(CR_State.class, aSt);
			putExt(CB_Output.class, aOutput);
		}

		public CR_State getState() {
			// README this is only necessary in an object-oriented fantasy world
			//  Honestly, it was done to remove casting, but here we are anyway
			return (CR_State) getExt(CR_State.class);
		}

		public CB_Output getContext() {
			return (CB_Output) getExt(CB_Output.class);
		}
	}
}

//
//
//
