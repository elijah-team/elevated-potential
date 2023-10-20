package tripleo.elijah.stages.inter;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.entrypoints.EntryPoint;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.gen_fn.EvaFunction;
import tripleo.small.ES_Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleThing implements GModuleThing {
	public static final class GeneralDescription {
		private final          ES_Symbol    aSymbol;
		private final @NotNull List<Object> aObjects;

		public GeneralDescription(ES_Symbol aSymbol, @NotNull List<Object> aObjects) {
			this.aSymbol  = aSymbol;
			this.aObjects = aObjects;
		}

		public ES_Symbol aSymbol() {
			return aSymbol;
		}

		public @NotNull List<Object> aObjects() {
			return aObjects;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (GeneralDescription) obj;
			return Objects.equals(this.aSymbol, that.aSymbol) &&
					Objects.equals(this.aObjects, that.aObjects);
		}

		@Override
		public int hashCode() {
			return Objects.hash(aSymbol, aObjects);
		}

		@Override
		public String toString() {
			return "GeneralDescription[" +
					"aSymbol=" + aSymbol + ", " +
					"aObjects=" + aObjects + ']';
		}

		}

	private final @NotNull List<EntryPoint> entryPoints;
	private final @NotNull List<EvaFunction> evaFunctions = new ArrayList<>();
	private final OS_Module mod;

	private GeneralDescription generalDescription;

	public ModuleThing(final OS_Module aMod) {
		mod = aMod;
		entryPoints = mod.entryPoints();
	}

	public void addFunction(final EvaFunction aGeneratedFunction) {
		evaFunctions.add(aGeneratedFunction);
	}

	public void describe(final GeneralDescription aGeneralDescription) {
		generalDescription = aGeneralDescription;
	}
}
