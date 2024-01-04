package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.Element;
import tripleo.elijah.javac_model.lang.model.element.TypeElement;
import tripleo.elijah.javac_model.lang.model.type.ArrayType;
import tripleo.elijah.javac_model.lang.model.type.DeclaredType;
import tripleo.elijah.javac_model.lang.model.type.ExecutableType;
import tripleo.elijah.javac_model.lang.model.type.NoType;
import tripleo.elijah.javac_model.lang.model.type.NullType;
import tripleo.elijah.javac_model.lang.model.type.PrimitiveType;
import tripleo.elijah.javac_model.lang.model.type.TypeKind;
import tripleo.elijah.javac_model.lang.model.type.TypeMirror;
import tripleo.elijah.javac_model.lang.model.type.WildcardType;

import java.util.List;

public interface Types {

    Element asElement(TypeMirror t);

    boolean isSameType(TypeMirror t1, TypeMirror t2);

    boolean isSubtype(TypeMirror t1, TypeMirror t2);

    boolean isAssignable(TypeMirror t1, TypeMirror t2);

    boolean contains(TypeMirror t1, TypeMirror t2);

    boolean isSubsignature(ExecutableType m1, ExecutableType m2);

    List<? extends TypeMirror> directSupertypes(TypeMirror t);

    TypeMirror erasure(TypeMirror t);

    TypeElement boxedClass(PrimitiveType p);

    PrimitiveType unboxedType(TypeMirror t);

    TypeMirror capture(TypeMirror t);

    PrimitiveType getPrimitiveType(TypeKind kind);

    NullType getNullType();

    NoType getNoType(TypeKind kind);

    ArrayType getArrayType(TypeMirror componentType);

    WildcardType getWildcardType(TypeMirror extendsBound,
                                 TypeMirror superBound);

    DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs);

    DeclaredType getDeclaredType(DeclaredType containing,
                                 TypeElement typeElem, TypeMirror... typeArgs);

    TypeMirror asMemberOf(DeclaredType containing, Element element);
}
