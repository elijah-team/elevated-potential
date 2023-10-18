package tripleo.elijah;

import tripleo.elijah.comp.Compilation;

public interface SimpleTest {
	SimpleTest setFile(String aS);

	SimpleTest run() throws Exception;

	int errorCount();

	boolean assertLiveClass(String aClassName);

	AssertingLiveClass assertingLiveClass(String aClassName);

	Compilation c();
}
