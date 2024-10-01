package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.Finally_Input;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;
import tripleo.elijah.nextgen.outputtree.*;

import java.util.*;

public class EDL_Finally implements Finally {
	@Override
	public int codeOutputSize() {
		return outputs.size();
	}

	@Override
	public List<String> getCodeOutputs() {
		List<String> l = new ArrayList<>();
		for (Output output : outputs) {
			l.add(((Output_) output).fileNameProvider.getFilename());
		}
		return l;
	}

	@Override
	public int inputCount() {
		return finallyInputs.size();
	}

	@Override
	public int outputCount() {
		return outputs.size();
	}

	private final Set<Outs> outputOffs = new HashSet<>();

	private final List<Finally_Input> finallyInputs = new ArrayList<>();

//	public void addInput(final CompilerInput aInp, final Out2 ty) {
//		inputs.add(new Input(aInp, ty));
//	}

	private final List<Output> outputs = new ArrayList<>();

	private boolean turnAllOutputOff;

//	public void addInput(final CompFactory.InputRequest aInp, final Out2 ty) {
//		inputs.add(new Input(aInp, ty));
//	}

	@Override
	public Output addCodeOutput(final EOT_FileNameProvider aFileNameProvider, final EOT_OutputFile aOff) {
		final Output_ e = new Output_(aFileNameProvider, aOff);
		outputs.add(e);
		return e;
	}

	@Override
	public void addInput(final EOT_Nameable aNameable, final EIT_InputType ty) {
		finallyInputs.add(new FinallyInput_(aNameable, ty));
	}

	@Override
	public boolean containsCodeOutput(@NotNull final String s) {
		return outputs.stream().anyMatch(i -> i.name().equals(s));
	}

	@Override
	public boolean containsInput(final String aS) {
		return finallyInputs.stream().anyMatch(i -> i.name().equals(aS));
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

		@Override
		public String getFilename() {
			return fileNameProvider.getFilename();
		}
	}

	public static class FinallyInput_ implements Finally_Input {
		private final EOT_Nameable  nameable;
		private final EIT_InputType ty;

		public FinallyInput_(final EOT_Nameable aNameable, final EIT_InputType aTy) {
//			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("66 Add Input >> " + aNameable.getName());
			nameable = aNameable;
			ty       = aTy;
		}

		@Override
		public String name() {
			return nameable.getNameableString();
		}

		@Override
		public String toString() {
			return "Input{" + "name=" + nameable.getNameableString() + ", ty=" + ty + '}';
		}
	}
}
