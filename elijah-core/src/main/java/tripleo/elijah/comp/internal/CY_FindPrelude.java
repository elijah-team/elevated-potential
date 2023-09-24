package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.*;

import java.io.*;

class CY_FindPrelude {
    @NotNull
    private static File local_prelude_file(final String prelude_name) {
        return new File("lib_elijjah/lib-" + prelude_name + "/Prelude.elijjah");
    }

    private final ErrSink errSink;

    private final USE x;

    CY_FindPrelude(final ErrSink aErrSink1, final USE aX) {
        errSink = aErrSink1;
        x = aX;
    }

    public Operation2<OS_Module> findPrelude(final String prelude_name) {
        final File local_prelude = local_prelude_file(prelude_name);

        if (!(local_prelude.exists())) {
            return Operation2.failure(new FileNotFoundDiagnostic(local_prelude));
        }

        try {
            return try_parse(local_prelude);
        } catch (final Exception e) {
            errSink.exception(e);
            return Operation2.failure(new ExceptionDiagnostic(e));
        }
    }

    @NotNull
    private Operation2<OS_Module> try_parse(final File local_prelude) {
        Operation2<OS_Module> om;

        try {
            om = x.realParseElijjahFile(local_prelude.getName(), local_prelude, false);

            switch (om.mode()) {
                case SUCCESS -> {
                    final CompilerInstructions instructions = new CompilerInstructionsImpl();
                    instructions.setName("prelude");
                    final GenerateStatement generateStatement = new GenerateStatementImpl();
                    final StringExpression expression = new StringExpressionImpl(Helpers.makeToken("\"c\"")); // TODO
                    generateStatement.addDirective(Helpers.makeToken("gen"), expression);
                    instructions.add(generateStatement);
                    final LibraryStatementPart lsp = new LibraryStatementPartImpl();
                    lsp.setInstructions(instructions);
                    //lsp.setDirName();
                    final OS_Module module = om.success();
                    module.setLsp(lsp);
                    return Operation2.success(module);
                }
                case FAILURE -> {
                    return om;
                }
                default -> throw new IllegalStateException("Unexpected value: " + om.mode());
            }

        } catch (final Exception aE) {
            om = Operation2.failure(new ExceptionDiagnostic(aE));
        }

        return om;
    }
}
