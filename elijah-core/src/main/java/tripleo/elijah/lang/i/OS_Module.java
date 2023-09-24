package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.entrypoints.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface OS_Module extends OS_Element {
	void add(OS_Element anElement);

	@NotNull
	List<EntryPoint> entryPoints();

	@org.jetbrains.annotations.Nullable
	OS_Element findClass(String aClassName);

	void finish();

	@NotNull
	Compilation getCompilation();

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

	void setParent(@NotNull Compilation parent);

	void setPrelude(OS_Module success);

	@Override
	void visitGen(@NotNull ElElementVisitor visit);
}
