package tripleo.elijah.javac_model.annotation.processing;

import tripleo.elijah.javac_model.lang.model.element.AnnotationMirror;
import tripleo.elijah.javac_model.lang.model.element.AnnotationValue;
import tripleo.elijah.javac_model.lang.model.element.Element;

import javax.tools.Diagnostic;

public interface Messager {
    void printMessage(Diagnostic.Kind kind, CharSequence msg);

    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e);

    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a);

    void printMessage(Diagnostic.Kind kind,
                      CharSequence msg,
                      Element e,
                      AnnotationMirror a,
                      AnnotationValue v);
    default void printError(CharSequence msg) {
        printMessage(Diagnostic.Kind.ERROR, msg);
    }

    default void printError(CharSequence msg, Element e) {
        printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    default void printWarning(CharSequence msg) {
        printMessage(Diagnostic.Kind.WARNING, msg);
    }

    default void printWarning(CharSequence msg, Element e) {
        printMessage(Diagnostic.Kind.WARNING, msg, e);
    }

    default void printNote(CharSequence msg) {
        printMessage(Diagnostic.Kind.NOTE, msg);
    }

    default void printNote(CharSequence msg, Element e) {
        printMessage(Diagnostic.Kind.NOTE, msg, e);
    }
}
