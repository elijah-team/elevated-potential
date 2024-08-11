package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.util.buffer.*;

class Default_C2C_Result implements C2C_Result {
	private final Buffer buffer;
	private final GenerateResult.TY _ty;
	private final String explanation_message;
	private final OS_Module module;
	private final WhyNotGarish_BaseFunction whyNotGarishFunction;

	private boolean _calculated;
	private EG_Statement _my_statement;

	public Default_C2C_Result(final Buffer aBuffer,
							  final GenerateResult.TY aTY,
							  final String aExplanationMessage,
			final @NotNull WhyNotGarish_BaseFunction aWhyNotGarishFunction) {
		buffer = aBuffer;
		_ty = aTY;
		explanation_message = aExplanationMessage;
		whyNotGarishFunction = aWhyNotGarishFunction;
		module = aWhyNotGarishFunction.getGf().module();
	}

	@Override
	public Buffer getBuffer() {
		return buffer;
	}

	@Override
	public OS_Module getDefinedModule() {
		return module;
	}

	@Override
	public @NotNull EG_Statement getStatement() {
		if (!_calculated) {
			_my_statement = EG_Statement.of(buffer.getText(), EX_Explanation.withMessage(explanation_message));
			_calculated = true;
		}
		return _my_statement;
	}

	@Override
	public WhyNotGarish_BaseFunction getWhyNotGarishFunction() {
		return whyNotGarishFunction;
	}

	@Override
	public Old_GenerateResult.TY ty() {
		return _ty;
	}
}
