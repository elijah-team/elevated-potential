package tripleo.elijah.javac_model.tools;

public interface DiagnosticListener<S> {
    void report(Diagnostic<? extends S> diagnostic);
}
