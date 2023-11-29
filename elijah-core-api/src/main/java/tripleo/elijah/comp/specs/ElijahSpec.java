package tripleo.elijah.comp.specs;

import tripleo.elijah.comp.graph.CM_Module;

import java.io.*;

public interface ElijahSpec {
	String getLongPath2();

	String file_name();

	File file();

	InputStream s();

	CM_Module getModule();
}
