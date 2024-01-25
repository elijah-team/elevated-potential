package tripleo.elijah.lang.i;

import java.util.*;

public interface ClassHeader {
	List<AnnotationClause> annos();

	TypeNameList genericPart();

	ClassInheritance inheritancePart();

	IdentExpression nameToken();

	void setConst(boolean aIsConst);

	void setGenericPart(TypeNameList aTypeNameList);

	void setName(IdentExpression aNameToken);

	void setType(ClassTypes ct);

	ClassTypes type();
}
