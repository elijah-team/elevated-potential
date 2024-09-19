package tripleo.vendor.batoull22;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;

public class Expert__SystemTest {

	@Test
	@Ignore
	public void testOpenfile() {
		final EK_ExpertSystem i = new EK_ExpertSystem();

		final Operation<EK_Reader> ovo2 = i.openfile_2();
		Assertions.assertNotSame(ovo2.mode(), Mode.FAILURE);

		final EK_Reader reader = ovo2.success();

		reader.readfile();
		// reader.print();
		reader.closefile();

		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("------------------------");
		boolean f = i.Forwardchaining();
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(" ");
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("Result of Forwardchaining: " + f);

		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(" ");
		// i.print();

		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("------------------------");
		boolean b = i.Backwardchaining();
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("Result of Backwardchaining: " + b);
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(" ");
	}
}
