package tripleo.elijah.javac_model.tools;

import tripleo.elijah.javac_model.lang.model.element.Modifier;
import tripleo.elijah.javac_model.lang.model.element.NestingKind;

import java.util.Objects;

public interface JavaFileObject extends FileObject {

    enum Kind {
        SOURCE(".java"),

        CLASS(".class"),

        HTML(".html"),

        OTHER("");
        public final String extension;
        Kind(String extension) {
            this.extension = Objects.requireNonNull(extension);
        }
    }

    Kind getKind();

    boolean isNameCompatible(String simpleName, Kind kind);

    NestingKind getNestingKind();

    Modifier getAccessLevel();

}
