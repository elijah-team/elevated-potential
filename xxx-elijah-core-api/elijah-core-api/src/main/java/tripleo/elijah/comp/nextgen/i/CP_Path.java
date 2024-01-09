package tripleo.elijah.comp.nextgen.i;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

import tripleo.elijah.Eventual;
import tripleo.elijah.comp.IO;
import tripleo.elijah.util.io.DisposableCharSink;
import tripleo.wrap.File;

public interface CP_Path {
	//
	CP_Path child(String aPath0);

	CP_SubFile subFile(String aFile);

	//
	CP_Path getParent();

	_CP_RootPath getRootPath();

	//
	String getName();

	//
	Eventual<Path> getPathPromise();

	//
	File getRootFile();

	tripleo.wrap.File toFile();
	
	Path getPath();

	Path toPath();

	//
	default InputStream getReadInputStream(IO aIO) throws FileNotFoundException {
		return toFile().getFileInputStream();
	}

	DisposableCharSink newIOWriter(IO io);

	//
	String asString();

	boolean samePath(Path px);
}
