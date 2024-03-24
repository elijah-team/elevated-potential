package tripleo.elijah_durable_elevated.elijah.comp.nextgen;

import org.apache.commons.codec.digest.DigestUtils;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.comp.IO;
import tripleo.elijah.util.DeferredAction;
import tripleo.elijah_durable_elevated.elijah.comp.IO_;

import java.util.List;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

class CY_HashDeferredAction implements DeferredAction<String> {
    private final Eventual<String> e = new Eventual<>(); // FIXME SingleShotEventual
    private final IO               io;

    static boolean started;

    public CY_HashDeferredAction(IO aIo) {
        io           = aIo;

//        if (started) throw new Error();
        started = true;
    }

    @Override
    public String description() {
        return "__HashDeferredAction";
    }

    @Override
    public boolean completed() {
        return this.e.isResolved();
    }

    @Override
    public Eventual<String> promise() {
        return this.e;
    }

    @Override
    public void calculate() {
        if (completed() || e._prom_isRejected()) return; // README only once, no retry

        final DigestUtils           digestUtils   = new DigestUtils(SHA_256);
        final StringBuilder          sb1           = new StringBuilder();
        final List<IO_._IO_ReadFile> recordedreads = io.recordedreads_io();

        recordedreads.stream()
                .map(IO_._IO_ReadFile::getFileName)
                .sorted()
                .map(digestUtils::digestAsHex)
                .forEach(x -> CP_OutputPath.append_sha_string_then_newline(sb1, x));

        final String c_name = digestUtils.digestAsHex(sb1.toString());
        e.resolve(c_name);
    }
}
