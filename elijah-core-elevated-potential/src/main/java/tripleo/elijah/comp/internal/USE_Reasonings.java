package tripleo.elijah.comp.internal;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.i.*;

import tripleo.wrap.File;

public class USE_Reasonings {
	public static USE_Reasoning parent(CompilerInstructions aCompilerInstructions, boolean parent, File aInstructionDir, LibraryStatementPart aLsp) {
		return new USE_Reasoning_parent(parent, aInstructionDir, aCompilerInstructions);
	}

	public static USE_Reasoning child(CompilerInstructions aCompilerInstructions, boolean parent, File aInstructionDir, String aDirName, File aDir, LibraryStatementPart aLsp) {
		return new USE_Reasoning_child(aDirName, aInstructionDir, parent, aDir, aCompilerInstructions);
	}

	public static USE_Reasoning default_(CompilerInstructions aCompilerInstructions, boolean parent, File aInstructionDir, LibraryStatementPart aLsp) {
		return new USE_Reasoning_default_(aInstructionDir, aCompilerInstructions);
	}

	public static USE_Reasoning instruction_doer_addon(final CompilerInstructions item) {
		return new USE_Reasoning_instruction_doer_addon(item);
	}

	public static USE_Reasoning findStdLib(final CD_FindStdLib aFindStdLib) {
		return new USE_Reasoning_findStdLib(aFindStdLib);
	}

	public static USE_Reasoning initial(final CK_ProcessInitialAction aCKProcessInitialAction, final CompilationRunner aCompilationRunner, final CB_Output aOutput) {
		return new USE_Reasoning_initial(aCKProcessInitialAction, aCompilationRunner, aOutput);
	}

	private static class USE_Reasoning_initial implements USE_Reasoning {
		private final CK_ProcessInitialAction processInitialAction;
		private       CB_Output               cbOutput;
		private       CompilationRunner       compilationRunner;

		public USE_Reasoning_initial(final CK_ProcessInitialAction aCKProcessInitialAction, final CompilationRunner aCompilationRunner, final CB_Output aOutput) {
			processInitialAction = aCKProcessInitialAction;
			compilationRunner    = aCompilationRunner;
			cbOutput             = aOutput;
		}

		@Override
		public boolean parent() {
			return false;
		}

		@Override
		public File instruction_dir() {
			return null;
		}

		@Override
		public CompilerInstructions compilerInstructions() {
			return processInitialAction.maybeFoundResult();
		}

		@Override
		public USE_Reasoning.Type ty() {
			return USE_Reasoning.Type.USE_Reasoning__initial;
		}
	}

	private static class USE_Reasoning_findStdLib implements USE_Reasoning {
		private final CD_FindStdLib findStdLib;

		public USE_Reasoning_findStdLib(final CD_FindStdLib aFindStdLib) {
			findStdLib = aFindStdLib;
		}

		@Override
		public boolean parent() {
			return false;
		}

		@Override
		public File instruction_dir() {
			return null;
		}

		@Override
		public CompilerInstructions compilerInstructions() {
			return findStdLib.maybeFoundResult();
		}

		@Override
		public USE_Reasoning.Type ty() {
			return Type.USE_Reasoning__findStdLib;
		}
	}

	private static class USE_Reasoning_instruction_doer_addon implements USE_Reasoning {
		private final CompilerInstructions item;

		public USE_Reasoning_instruction_doer_addon(final CompilerInstructions aItem) {
			item = aItem;
		}

		@Override
		public boolean parent() {
			return false;
		}

		@Override
		public File instruction_dir() {
			return null;
		}

		@Override
		public CompilerInstructions compilerInstructions() {
			return item;
		}

		@Override
		public USE_Reasoning.Type ty() {
			return Type.USE_Reasoning__instruction_doer_addon;
		}
	}

	private static class USE_Reasoning_default_ implements USE_Reasoning {
		private final File                 instructionDir;
		private final CompilerInstructions compilerInstructions;

		public USE_Reasoning_default_(final File aInstructionDir, final CompilerInstructions aCompilerInstructions) {
			instructionDir       = aInstructionDir;
			compilerInstructions = aCompilerInstructions;
		}

		@Override
		public boolean parent() {
			return false;
		}

		@Override
		public File instruction_dir() {
			return instructionDir;
		}

		@Override
		public CompilerInstructions compilerInstructions() {
			return compilerInstructions;
		}

		@Override
		public USE_Reasoning.Type ty() {
			return USE_Reasoning.Type.USE_Reasoning___default;
		}
	}

	private static class USE_Reasoning_child implements USE_Reasoning {
		private final String               dirName;
		private final File                 instructionDir;
		private final boolean              parent;
		private final File                 dir;
		private final CompilerInstructions compilerInstructions;

		public USE_Reasoning_child(final String aDirName, final File aInstructionDir, final boolean aParent, final File aDir, final CompilerInstructions aCompilerInstructions) {
			dirName              = aDirName;
			instructionDir       = aInstructionDir;
			parent               = aParent;
			dir                  = aDir;
			compilerInstructions = aCompilerInstructions;
		}

		File top() {
			return new File(dirName);
		}

		File child() {
			return instructionDir;
		}

		@Override
		public boolean parent() {
			return parent;
		}

		@Override
		public File instruction_dir() {
			return dir;
		}

		@Override
		public CompilerInstructions compilerInstructions() {
			return compilerInstructions;
		}

		@Override
		public USE_Reasoning.Type ty() {
			return USE_Reasoning.Type.USE_Reasoning__child;
		}
	}

	private static class USE_Reasoning_parent implements USE_Reasoning {
		private final boolean              parent;
		private final File                 instructionDir;
		private final CompilerInstructions compilerInstructions;

		public USE_Reasoning_parent(final boolean aParent, final File aInstructionDir, final CompilerInstructions aCompilerInstructions) {
			parent               = aParent;
			instructionDir       = aInstructionDir;
			compilerInstructions = aCompilerInstructions;
		}

		@Override
		public boolean parent() {
			return parent;
		}

		@Override
		public File instruction_dir() {
			return instructionDir;
		}

		@Override
		public CompilerInstructions compilerInstructions() {
			return compilerInstructions;
		}

		@Override
		public USE_Reasoning.Type ty() {
			return Type.USE_Reasoning__parent;
		}
	}
}
