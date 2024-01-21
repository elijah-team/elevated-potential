package tripleo.elijah.comp.specs;

//import java.io.*;
//
//import tripleo.elijah.comp.graph.i.CM_Module;
//import tripleo.wrap.File;

import tripleo.elijah.comp.graph.i.CM_Module;
import tripleo.wrap.File;

import java.io.InputStream;

public interface ElijahSpec {
	String getLongPath2();

	String file_name();

	File file();

	InputStream s();

	CM_Module getModule();
}
