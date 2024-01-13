package tripleo.elijah.comp.generated;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.DriverToken;
import tripleo.elijah_elevated.comp.model.CM_Prelude;
import tripleo.elijah_elevated.comp.model.CM_Prelude_;

public enum CompilationAlways { ;

	private static final CM_Prelude c_prelude = new CM_Prelude_();

	@NotNull
	public static String defaultPrelude() {
		return c_prelude.getLangString();
	}

	public enum Tokens {
		;
		// README 10/20 Disjointed needs annotation
		//  12/04 ServiceLoader
		public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
		public static final DriverToken COMPILATION_RUNNER_START        = DriverToken.makeToken("COMPILATION_RUNNER_START");
	}
}
