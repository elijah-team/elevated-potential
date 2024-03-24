package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.*;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeduceTypes2.DeduceTypes2Injector;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.FoundElement;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.IdentIA;

public class DeduceElement3_ConstantTableEntry implements IDeduceElement3 {

	private final    ConstantTableEntry principal;
	public           DeduceTypes2       deduceTypes2;
	public           Diagnostic         diagnostic;
	public           IDeduceElement3    deduceElement3;
	public           BaseEvaFunction    generatedFunction;
	public @Nullable OS_Type            osType;
	private          GenType            genType;

	@Contract(pure = true)
	public DeduceElement3_ConstantTableEntry(final ConstantTableEntry aConstantTableEntry) {
		principal = aConstantTableEntry;
	}

	private DeduceTypes2Injector _inj() {
		return deduceTypes2()._inj();
	}

	@Override
	public DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	@Override
	public @NotNull DED elementDiscriminator() {
		return new DED.DED_CTE(principal);
	}

	@Override
	public BaseEvaFunction generatedFunction() {
		return generatedFunction;
	}

	@Override
	public GenType genType() {
		return genType;
	}

	@Override
	public OS_Element getPrincipal() {
		return principal.getDeduceElement3().getPrincipal();
	}

	@Override
	public @NotNull DeduceElement3_Kind kind() {
		return DeduceElement3_Kind.GEN_FN__CTE;
	}

	@Override
	public void resolve(final Context aContext, final DeduceTypes2 aDeduceTypes2) {
		// deduceTypes2.resolveIdentIA_(aContext, aIdentIA, generatedFunction,
		// aFoundElement);
		throw new NotImplementedException();
		// careful with this
		// throw new UnsupportedOperationException("Should not be reached");
	}

	@Override
	public void resolve(final @NotNull IdentIA aIdentIA, final @NotNull Context aContext,
						final @NotNull FoundElement aFoundElement) {
		// FoundElement is the "disease"
		deduceTypes2.resolveIdentIA_(aContext, aIdentIA, generatedFunction, aFoundElement);
	}

}
