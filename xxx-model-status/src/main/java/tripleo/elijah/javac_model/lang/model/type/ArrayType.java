package tripleo.elijah.javac_model.lang.model.type;


public interface ArrayType extends ReferenceType {

    TypeMirror getComponentType();
}
