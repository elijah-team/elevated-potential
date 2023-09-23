/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.nextgen.diagnostic;

import org.jetbrains.annotations.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;

/**
 * Created 3/5/22 4:55 PM
 */
public class CouldntGenerateClass implements Diagnostic {
    private final ClassDefinition classDefinition;
    private final ClassInvocation classInvocation;
    private final GenerateFunctions generateFunctions;
    private final WlGenerateClass gen;
    private final DeducePhase deducePhase;

    public CouldntGenerateClass(final ClassDefinition aClassDefinition,
                                final GenerateFunctions aGenerateFunctions,
                                final ClassInvocation aClassInvocation) {
        classDefinition = aClassDefinition;
        generateFunctions = aGenerateFunctions;
        classInvocation = aClassInvocation;

        gen = null;
        deducePhase = null;
    }

    public CouldntGenerateClass(WlGenerateClass gen, DeducePhase deducePhase) {
        this.gen = gen;
        this.deducePhase = deducePhase;

        classDefinition = null;
        generateFunctions = null;
        classInvocation = null;
    }

    @Override
    public @NotNull String code() {
        return "E2000";
    }

    public ClassDefinition getClassDefinition() {
        if (gen != null) {
            return null; // !!
        } else {
            return classDefinition;
        }
    }

    public ClassInvocation getClassInvocation() {
        if (gen != null) {
            return gen.getClassInvocation();
        } else {
            return classInvocation;
        }
    }

    public GenerateFunctions getGenerateFunctions() {
        if (gen != null) {
            return gen.getGenerateFunctions();
        } else {
            return generateFunctions;
        }
    }

    @Override
    public @NotNull Locatable primary() {
        return null;
    }

    @Override
    public void report(final PrintStream stream) {
        NotImplementedException.raise();
    }

    @Override
    public @NotNull List<Locatable> secondary() {
        return null;
    }

    @Override
    public @NotNull Severity severity() {
        return Severity.ERROR;
    }
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
