/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah_elevateder.stages.generate;

import org.junit.*;
import tripleo.elijah.comp.Compilation0;

public class ElSystemTest {

	ElSystem     sys;
	Compilation0 c;

	@Ignore
	@Test
	public void generateOutputs() {
		OutputStrategy os = new OutputStrategy();
		os.per(OutputStrategy.Per.PER_CLASS);
		// sys.setOutputStrategy(os);
		// sys.generateOutputs(c.pipelineLogic().gr);
	}

	@Before //Each
	public void setUp() throws Exception {
		// final StdErrSink eee = new StdErrSink();
		// c = new Compilation(eee, new IO());
		//
		// final String f = "test/basic1/backlink3";
		//
		// sys = new ElSystem(c, OutputStrategy::new);
		// sys.setCompilation(c);
		//
		// c.feedCmdLine(Helpers.List_of(f));
	}
}

//
//
//
