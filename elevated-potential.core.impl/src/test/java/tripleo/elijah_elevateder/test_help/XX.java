package tripleo.elijah_elevateder.test_help;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevateder.lang.types.OS_UserType;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.stages.gen_fn.TypeTableEntry;
import tripleo.elijah_elevateder.util.Helpers0;

public enum XX {
	;

	public static @NotNull IdentExpression ident(final String aX) {
		final IdentExpression identExpression = Helpers0.string_to_ident(aX);
		return identExpression;
	}

	public static @NotNull TypeTableEntry regularTypeName_specifyTableEntry(final IdentExpression aIdentExpression,
	                                                                        final @NotNull BaseEvaFunction aBaseGeneratedFunction, final @NotNull String aTypeName) {
		final RegularTypeName typeName = RegularTypeNameImpl.makeWithStringTypeName(aTypeName);
		final OS_Type type = new OS_UserType(typeName);
		final TypeTableEntry tte = aBaseGeneratedFunction.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, type,
				aIdentExpression);

		return tte;
	}

	public static @NotNull VariableStatementImpl sequenceAndVarNamed(final IdentExpression aIdentExpression) {
		final VariableSequenceImpl  seq   = new VariableSequenceImpl();
		final VariableStatementImpl x_var = new VariableStatementImpl(seq);

		x_var.setName(aIdentExpression);

		return x_var;
	}

	public static IdentExpression ident(final String aX, final Context aContext) {
		final IdentExpression identExpression = Helpers0.string_to_ident(aX);
		identExpression.setContext(aContext);
		return identExpression;
	}
}
