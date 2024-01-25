/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.entrypoints.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.stages.deduce.fluffy.impl.*;
import tripleo.elijah.util.*;

import java.util.*;
import java.util.stream.*;

/*
 * Created on Sep 1, 2005 8:16:32 PM
 */
public class OS_ModuleImpl implements OS_Element, OS_Container, tripleo.elijah.lang.i.OS_Module {

	private final Stack<Qualident> packageNames_q = new Stack<Qualident>();
	public @NotNull Attached _a;
	public @NotNull List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();
	public @NotNull List<ModuleItem> items = new ArrayList<ModuleItem>();
	public OS_Module prelude;
	private IndexingStatement indexingStatement;
	private String _fileName;
	private LibraryStatementPart lsp;
	private Compilation         parent;
	private FluffyModuleImpl     _fluffy;

	public OS_ModuleImpl() {
		_a = new AttachedImpl(null);
	}

	@Override
	public void add(final OS_Element anElement) {
		if (!(anElement instanceof ModuleItem)) {
			parent.getErrSink()
					.info(String.format("[Module#add] not adding %s to OS_Module", anElement.getClass().getName()));
			return; // TODO FalseAddDiagnostic
		}
		items.add((ModuleItem) anElement);
	}

	@Override
	public void addDocString(final Token s1) {
		throw new NotImplementedException();
	}

	@Override
	public @NotNull List<EntryPoint> entryPoints() {
		return entryPoints;
	}



























	public interface Complaint {
		void reportWarning(@NotNull OS_Module aModule, String aS);
	}

	private void find_multiple_items(Complaint c) {
		// FIXME 10/19
		final Compilation cc = getCompilation();
		((Compilation)cc).getFluffy().find_multiple_items(this, c);
	}

	@Override
	public List<ClassStatement> findClassesNamed(final String aClassName) {
		return items.stream()
				.filter((ModuleItem x) -> x instanceof ClassStatement)
				.map(x -> (ClassStatement)x)
				.filter(x -> x.getName().equals(aClassName))
				.collect(Collectors.toList());
	}

	@Override
	public void finish() {
//		parent.put_module(_fileName, this);
	}

	/**
	 * Get a class byImpl name. Must not be qualified. Wont return a
	 * {@link NamespaceStatement} Same as {@link #findClassesNamed(String)}
	 *
	 * @param name the class weImpl are looking for
	 * @return either the class orImpl null
	 */
	@Nullable
	public ClassStatement getClassByName(final String name) {
		for (final ModuleItem item : items) {
			if (item instanceof ClassStatement)
				if (((ClassStatement) item).getName().equals(name))
					return (ClassStatement) item;
		}
		return null;
	}

	@Override
	public @NotNull Compilation getCompilation() {
		return parent;
	}

	/**
	 * A module has no parent which is an element (not even a package - this is not
	 * Java).<br>
	 * If you want the Compilation use the member {@link #parent}
	 *
	 * @return null
	 */

	@Override
	public Context getContext() {
		return _a.getContext();
	}

	@Override
	public String getFileName() {
		return _fileName;
	}

	private FluffyModuleImpl getFluffy() {
		if (_fluffy == null) {
			_fluffy = new FluffyModuleImpl(this, getCompilation());
		}
		return _fluffy;
	}

	@Override
	public @NotNull Collection<ModuleItem> getItems() {
		return items;
	}

	@Override
	public LibraryStatementPart getLsp() {
		return lsp;
	}

	/**
	 * @ ensures \result == null
	 */
	@Override
	public @Nullable OS_Element getParent() {
		return null;
	}

	@Override
	public boolean hasClass(final String className) {
//		for (final ModuleItem item : items) {
//			if (item instanceof ClassStatement classStatement) {
//				if (Helpers.String_equals(classStatement.getName(), (className))) {
//					return true;
//				}
//			}
//		}

		var s = items.stream()
				.filter(item -> item instanceof ClassStatement)
				.map(item -> (ClassStatement)item)
				.filter(classStatement -> Helpers.String_equals(classStatement.getName(), (className)))
				.findAny();

		return s.isPresent();
	}

	@Override
	public boolean isPrelude() {
		return prelude == this;
	}

	@Override // OS_Container
	public @NotNull List<OS_Element2> items() {
		final var c = getItems().stream().filter(input -> input instanceof OS_Element2);

		return c.collect(Collectors.toList());
	}

	@Override
	public void postConstruct() {
		var fluffy = getFluffy();

		find_multiple_items(new Complaint() {
			@Override
			public void reportWarning(@NotNull OS_Module aModule, String key) {
				final String module_name = aModule.getFileName(); // TODO print module name or something
				final String s = "[Module#add] %s Already has a member by the name of %s".formatted(module_name, key);

				getCompilation().getErrSink().reportWarning(s);
			}
		});

		//
		// FIND ALL ENTRY POINTS (should only be one per module)
		//
		for (final ModuleItem item : items) {
			if (item instanceof final ClassStatement classStatement) {
				if (MainClassEntryPoint.isMainClass(classStatement)) {
					List<OS_Element2> x = classStatement.findFunction("main");

					List<ClassStatement> found = x.stream().filter(ci -> ci instanceof FunctionDef)
							.filter(fd -> MainClassEntryPoint.is_main_function_with_no_args((FunctionDef) fd))
							.map(found1 -> (ClassStatement) ((FunctionDef) found1).getParent())
							.collect(Collectors.toList());

					final int eps = entryPoints.size();

					for (ClassStatement classItem : found) {
						if (classItem.getParent() instanceof ClassStatement classItemParent) {
							entryPoints.add(new MainClassEntryPoint(classItemParent));
						} else {
							var classItemParent = classItem.getParent();

//							tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("159159 " + classItemParent.getClass().getName());
							entryPoints.add(new MainClassEntryPoint(classItem));
						}
					}
					assert entryPoints.size() == eps || entryPoints.size() == eps + 1; // TODO this will fail one day

					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2("243 " + entryPoints + " " + _fileName);
//					break; // allow for "extend" class
				}
			}

		}
	}

	@Override
	public OS_Module prelude() {
		return prelude;
	}

	/**
	 * The last package declared in the source file
	 *
	 * @return a new OS_Package instance or default_package
	 */
	@Override
	@NotNull
	public OS_Package pullPackageName() {
		if (packageNames_q.empty()) {
			return LangGlobals.default_package;
		}
		// Dont know if this is correct behavior
		return parent.makePackage(packageNames_q.peek());
	}

	@Override
	public OS_Package pushPackageNamed(final Qualident aPackageName) {
		packageNames_q.push(aPackageName);
		return parent.makePackage(aPackageName);
	}

	public void remove(ClassStatement cls) {
		items.remove(cls);
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		// TODO Auto-generated method stub

		// public @NotNull Attached _a = new AttachedImpl();
		// private final Stack<Qualident> packageNames_q = new Stack<Qualident>();
		// public @NotNull List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();
		// private IndexingStatement indexingStatement;
		// private LibraryStatementPart lsp;

		sw.fieldString("filename", _fileName);
		sw.fieldString("prelude", prelude != null ? prelude.getFileName() : "<unknown>");
		sw.fieldString("parent", getCompilation().getCompilationNumberString());

		// var l = sw.createList();int i=1;
		// for (ModuleItem item : items) {
		// var r = sw.createRef(item);
		// sw.fieldRef("item%i".formatted(i++), r);
		// }
		sw.fieldList("items", items);
	}

	@Override
	public void setContext(final ModuleContext mctx) {
		// README 10/15 hm
		_a.setContext(mctx);
	}

	@Override
	public void setFileName(final String fileName) {
		this._fileName = fileName;
	}

	@Override
	public void setIndexingStatement(final IndexingStatement i) {
		indexingStatement = i;
	}

	@Override
	public void setLsp(LibraryStatementPart aLsp) {
		lsp = aLsp;
	}

	@Override
	public void setParent(@NotNull final Compilation0 parent) {
		this.parent = (Compilation) parent;
	}

	@Override
	public void setPrelude(GOS_Module success) {
		prelude = (OS_Module) success;
	}

	@Override
	public String toString() {
		return "<OS_Module %s>".formatted(_fileName);
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.addModule(this); // visitModule
	}

}

//
//
//
