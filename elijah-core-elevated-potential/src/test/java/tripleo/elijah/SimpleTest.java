package tripleo.elijah;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.test_help.AssertingLiveClass;

public interface SimpleTest {
	SimpleTest setFile(String aS);

	SimpleTest run() throws Exception;

	int errorCount();

	boolean assertLiveClass(String aClassName);

	AssertingLiveClass assertingLiveClass(String aClassName);

	Compilation0 c();
}
