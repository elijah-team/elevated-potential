package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.outputtree.*;

import java.util.*;

public class Finally_ implements Finally {
	@Override
	public int codeOutputSize() {
		return outputs.size();
	}

	@Override
	public List<String> getCodeOutputs() {
		List<String> l = new ArrayList<>();
		for (Output output : outputs) {
			l.add(((Output_)output).fileNameProvider.getFilename());
		}
		return l;
	}

	@Override
	public int inputCount() {
		return inputs.size();
	}

	@Override
	public int outputCount() {
		return outputs.size();
	}

	private final Set<Outs> outputOffs = new HashSet<>();

	private final List<Input> inputs = new ArrayList<>();

//	public void addInput(final CompilerInput aInp, final Out2 ty) {
//		inputs.add(new Input(aInp, ty));
//	}

	private final List<Output> outputs = new ArrayList<>();

	private boolean turnAllOutputOff;

//	public void addInput(final CompFactory.InputRequest aInp, final Out2 ty) {
//		inputs.add(new Input(aInp, ty));
//	}

	@Override
	public void addCodeOutput(final EOT_FileNameProvider aFileNameProvider, final EOT_OutputFile aOff) {
		outputs.add(new Output_(aFileNameProvider, aOff));
	}

	@Override
	public void addInput(final Nameable aNameable, final Out2 ty) {
		inputs.add(new Input_(aNameable, ty));
	}

	@Override
	public boolean containsCodeOutput(@NotNull final String s) {
		return outputs.stream().anyMatch(i -> i.name().equals(s));
	}

	@Override
	public boolean containsInput(final String aS) {
		return inputs.stream().anyMatch(i -> i.name().equals(aS));
	}

	@Override
	public boolean outputOn(final Outs aOuts) {
		return !turnAllOutputOff && !outputOffs.contains(aOuts);
	}

	@Override
	public void turnAllOutputOff() {
		turnAllOutputOff = true;
	}

	@Override
	public void turnOutputOff(final Outs aOut) {
		outputOffs.add(aOut);
	}

	public static class Output_ implements Output {
		private final EOT_FileNameProvider fileNameProvider;
		@SuppressWarnings("FieldCanBeLocal")
		private final EOT_OutputFile       off;

		public Output_(final EOT_FileNameProvider aFileNameProvider, final EOT_OutputFile aOff) {
			fileNameProvider = aFileNameProvider;
			off              = aOff;
		}

		@Override
		public String name() {
			return fileNameProvider.getFilename();
		}
	}

	public static class Input_ implements Input {
		private final Nameable nameable;
		private final Out2     ty;

		public Input_(final Nameable aNameable, final Out2 aTy) {
//			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("66 Add Input >> " + aNameable.getName());
			nameable = aNameable;
			ty       = aTy;
		}

//		public Input(final CompilerInput aInp, final Out2 aTy) {
//			nameable = new Finally._CompilerInputNameable(aInp);
//			ty  = aTy;
//		}

//		public Input(final CompFactory.InputRequest aInp, final Out2 aTy) {
//			nameable = new Finally.InputRequestNameable(aInp);
//			ty = aTy;
//		}

		@Override
		public String name() {
			return nameable.getNameableString();
		}

		@Override
		public String toString() {
			return "Input{" + "name=" + nameable.getNameableString() + ", ty=" + ty + '}';
		}
	}

//	private class _CompilerInputNameable implements Nameable {
//		private final CompilerInput aInp;
//
//		public _CompilerInputNameable(CompilerInput aInp) {
//			this.aInp = aInp;
//		}
//
//		@Override
//		public String getName() {
//			return aInp.getInp();
//		}
//	}

//	private class InputRequestNameable implements Nameable {
//		private final CompFactory.InputRequest aInp;
//
//		public InputRequestNameable(CompFactory.InputRequest aInp) {
//			this.aInp = aInp;
//		}
//
//		@Override
//		public String getName() {
//			return aInp.file().toString();
//		}
//	}
}
