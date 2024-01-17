package tripleo.elijah.javac_model.lang.model.type;


public interface WildcardType extends TypeMirror {

	TypeMirror getExtendsBound();

	TypeMirror getSuperBound();
}
