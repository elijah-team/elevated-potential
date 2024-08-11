package tripleo.elijah.nextgen;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.composable.*;

public interface ClassDefinition {
	IComposable getComposable();

	@NotNull
	Set<ClassStatement> getExtended();

	GClassInvocation getInvocation();

	GEvaClass getNode();

	ClassStatement getPrimary();

	void setComposable(IComposable aComposable);

	void setInvocation(GClassInvocation aInvocation);

	void setNode(GEvaClass aNode);
}
