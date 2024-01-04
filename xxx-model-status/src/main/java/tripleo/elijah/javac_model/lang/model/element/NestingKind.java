package tripleo.elijah.javac_model.lang.model.element;

public enum NestingKind {
    TOP_LEVEL,

    MEMBER,

    LOCAL,

    ANONYMOUS;

    public boolean isNested() {
        return this != TOP_LEVEL;
    }
}
