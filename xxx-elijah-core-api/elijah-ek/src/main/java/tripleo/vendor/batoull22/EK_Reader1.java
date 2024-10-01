package tripleo.vendor.batoull22;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author tripleo
 */
final class EK_Reader1 implements EK_Reader {
	private final           EK_ExpertSystem _system;
	private final @Nullable Scanner         input_;

	@Contract(pure = true)
	EK_Reader1(final EK_ExpertSystem aExpertSystem, final InputStream aStream) {
		_system = aExpertSystem;
		if (aStream != null) {
			input_ = new Scanner(Objects.requireNonNull(aStream));
		} else {
			input_ = null;
		}
	}

	@Override
	public void closefile() {
		if (input_ != null) {
			input_.close();
		}
	}

	@Override
	public void print() {
		SimplePrintLoggerToRemoveSoon.println_out_4("factlist:" + _system.getListfacts());
		SimplePrintLoggerToRemoveSoon.println_out_4("rulelist:" + _system.getListrule());
		SimplePrintLoggerToRemoveSoon.println_out_4("goal:" + _system.getGoal());
		SimplePrintLoggerToRemoveSoon.println_out_4(" ");
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4( c);
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4( j);
	}

	@Override
	public void readfile() {
		// Read the line
		if (input_ != null) {
			while (input_.hasNext()) {
				String a = input_.nextLine();

				_system.proof(a);
			}
		}
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("factlist:"+ Listfacts);
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("goal:"+ goal);
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4( "rulelist:"+Listrule);
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4( " ");
	}
}
