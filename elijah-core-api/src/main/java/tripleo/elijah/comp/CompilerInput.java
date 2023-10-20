package tripleo.elijah.comp;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;

public interface CompilerInput {
	void accept_ci(Maybe<ILazyCompilerInstructions> compilerInstructionsMaybe);

	void accept_hash(String hash);

	Maybe<ILazyCompilerInstructions> acceptance_ci();

	void certifyRoot();

	File getDirectory();

	void setDirectory(File f);

	boolean isElijjahFile();

	boolean isEzFile();

	boolean isNull();

	boolean isSourceRoot();

	void setArg();

	void setDirectoryResults(List<Operation2<CompilerInstructions>> aLoci);

	void setMaster(CompilerInputMaster master);

	void setSourceRoot();

	@Override
	String toString();

	Ty ty();

	File makeFile();

	String getInp();

	List<Operation2<CompilerInstructions>> getDirectoryResults();

	Object getExt(Class<?> aClass);
	
	void putExt(Class<?> aClass, Object o);

	enum CompilerInputField {
		TY, HASH, ACCEPT_CI, DIRECTORY_RESULTS
	}

	enum Ty {
		NULL, /* as is from command line/ */
		SOURCE_ROOT,
		ROOT, /* the base of the compilation */
		ARG, /* represents a compiler change (CC) */
		STDLIB
	}
}
