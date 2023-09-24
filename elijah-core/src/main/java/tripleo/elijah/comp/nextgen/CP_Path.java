package tripleo.elijah.comp.nextgen;

import org.jdeferred2.*;

import java.io.*;
import java.nio.file.*;

public interface CP_Path {
	CP_Path child(String aPath0);

	String getName();

	CP_Path getParent();

	Path getPath();

	Promise<Path, Void, Void> getPathPromise();

	File getRootFile();

	_CP_RootPath getRootPath();

	CP_SubFile subFile(String aFile);

	File toFile();
}
