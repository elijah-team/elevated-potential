package tripleo.elijah.comp;

import com.google.common.base.*;
import lombok.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CompilerInput {
    @Getter
    private final String inp;
    private final Map<Object, Object> exts = new HashMap<>();
    private Maybe<ILazyCompilerInstructions> accept_ci;
    private File dir_carrier;
    //	@Getter(fluent)
    private Ty ty;
    private String hash;
    private CompilerInputMaster master;
    @SuppressWarnings("unchecked") // README squiggly line in idea
    @Getter
    private List<Operation2<CompilerInstructions>> directoryResults = Collections.EMPTY_LIST;

    public CompilerInput(final String aS) {
        inp = aS;
        ty = Ty.NULL;
    }

    public void setMaster(CompilerInputMaster master) {
        this.master = master;
    }

    public void accept_ci(final Maybe<ILazyCompilerInstructions> compilerInstructionsMaybe) {
        accept_ci = compilerInstructionsMaybe;

        if (master != null)
            master.notifyChange(this, CompilerInputField.ACCEPT_CI);
    }

    public Maybe<ILazyCompilerInstructions> acceptance_ci() {
        return accept_ci;
    }

    public void setSourceRoot() {
        ty = Ty.SOURCE_ROOT;

        if (master != null)
            master.notifyChange(this, CompilerInputField.TY);
    }

    public void setArg() {
        ty = Ty.ARG;

        if (master != null)
            master.notifyChange(this, CompilerInputField.TY);
    }

    public void certifyRoot() {
        ty = Ty.ROOT;

        if (master != null)
            master.notifyChange(this, CompilerInputField.TY);
    }

    public void accept_hash(final String hash) {
        this.hash = hash;

        if (master != null)
            master.notifyChange(this, CompilerInputField.HASH);
    }


    public boolean isNull() {
        return ty == Ty.NULL;
    }
    public boolean isSourceRoot() {
        return ty == Ty.SOURCE_ROOT;
    }


    public boolean isEzFile() {
        //new QuerySearchEzFiles.EzFilesFilter().accept()
        return Pattern.matches(".+\\.ez$", inp);
    }

    public boolean isElijjahFile() {
        return Pattern.matches(".+\\.elijjah$", inp) ||
                Pattern.matches(".+\\.elijah$", inp);
    }


    public File getDirectory() {
        Preconditions.checkNotNull(dir_carrier);

        return dir_carrier;
    }

    public void setDirectory(File f) {
        ty = Ty.SOURCE_ROOT;
        dir_carrier = f;

        if (master != null)
            master.notifyChange(this, CompilerInputField.TY);
    }

    public void setDirectoryResults(final List<Operation2<CompilerInstructions>> aLoci) {
        this.directoryResults = aLoci;

        if (master != null)
            master.notifyChange(this, CompilerInputField.DIRECTORY_RESULTS);
    }

    @Override
    public String toString() {
        return "CompilerInput{" +
                "ty=" + ty +
                ", inp='" + inp + '\'' +
                '}';
    }

    public Object getExt(Class<?> aClass) {
        if (exts.containsKey(aClass)) {
            return exts.get(aClass);
        }
        return null;
    }
    public void putExt(Class<?> aClass, Object o) {
        exts.put(aClass, o);
    }

    public Ty ty() {
        return ty;
    }

    public enum CompilerInputField {
        TY,
        HASH,
        ACCEPT_CI, DIRECTORY_RESULTS
    }

    public enum Ty {
        NULL, /*guess we don't know yet*/
        SOURCE_ROOT,
        ROOT /*the base of the compilation*/,
        ARG /*represents a compiler change (CC)*/
    }
}
