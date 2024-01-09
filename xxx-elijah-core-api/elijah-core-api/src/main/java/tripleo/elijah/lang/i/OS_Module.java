package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.entrypoints.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface OS_Module extends OS_Element, GOS_Module {
	void add(OS_Element anElement);

	@NotNull
	List<EntryPoint> entryPoints();

	List<ClassStatement> findClassesNamed(String aClassName);

	void finish();

	@NotNull
	Compilation0 getCompilation();

	@Override
	Context getContext();

	String getFileName();

	@NotNull
	Collection<ModuleItem> getItems();

	LibraryStatementPart getLsp();

	@Override
	@org.jetbrains.annotations.Nullable
	OS_Element getParent();

	boolean hasClass(String className); // OS_Container

	boolean isPrelude();

	void postConstruct();

	OS_Module prelude();

	OS_Package pullPackageName();

	OS_Package pushPackageNamed(Qualident aPackageName);

	@Override
	void serializeTo(SmallWriter sw);

	void setContext(ModuleContext mctx);

	void setFileName(String fileName);

	void setIndexingStatement(IndexingStatement idx);

	void setLsp(@NotNull LibraryStatementPart lsp);

	void setParent(@NotNull Compilation0 parent);

	void setPrelude(GOS_Module success);

	@Override
	void visitGen(@NotNull ElElementVisitor visit);
}
