/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.contexts;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.nextgen.names.i.*;
import tripleo.elijah.lang.nextgen.names.impl.*;

import java.util.*;

/**
 * Created 8/15/20 7:09 PM
 */
public class ImportContext extends ContextImpl implements Context, IImportContext {
	protected class NPC {
		static boolean isModuleNamespace(OS_Element ns) {
			if (!(ns instanceof NamespaceStatement))
				return false;
			if (((NamespaceStatement) ns).getKind() == NamespaceTypes.MODULE)
				return true;

			return false;
		}

		private final List<IdentExpression> x;

		public NPC(final List<IdentExpression> aX) {
			x = aX;
		}

		private void checkLast(final String name, final int level, final @NotNull LookupResultList Result,
							   final @NotNull ISearchList alreadySearched, final @NotNull Compilation compilation) {
			final IdentExpression last = x.get(x.size() - 1);
			if (last.getText().equals(name)) {
				Qualident cl = new QualidentImpl();
				for (int i = 0; i < x.size() - 1; i++) {
					cl.append(x.get(i));
				}
				// SAME AS ABOVE, WITH ADDITIONS
				if (compilation.isPackage(cl.toString())) {

					// TODO iterate here
					// README assert checks, then adds with proveneance

					var pkg = compilation.getPackage(cl);
					var pu = new ENU_PackageRef(pkg);
					EN_Name.assertUnderstanding(cl.parts().get(cl.parts().size() - 1), pu);

					checkLastHelper(name, level, Result, alreadySearched, compilation, cl);
				}
			}
		}

		private void checkLastHelper(final String name, final int level, final @NotNull LookupResultList Result,
									 final @NotNull ISearchList alreadySearched, final @NotNull Compilation compilation,
									 final @NotNull Qualident cl) {
			final OS_Package aPackage = compilation.getPackage(cl);
			// LogEvent.logEvent(4003 , ""+aPackage.getElements());
			for (final OS_Element element : aPackage.getElements()) {
				// tripleo.elijah.util.Stupidity.println_err_2("4002 "+element);

				if (!(element instanceof OS_NamedElement))
					continue;

				if (isModuleNamespace(element)) {
					// LogEvent.logEvent(4103, "");
					final NamespaceContext namespaceContext = (NamespaceContext) element.getContext();
					alreadySearched.add(namespaceContext);
					LookupResultList xxx = namespaceContext.lookup(name, level, Result, alreadySearched, true);
					for (LookupResult result : xxx.results()) {
						Result.add(result.getName(), result.getLevel(), result.getElement(), result.getContext());
					}
				} else {
					final OS_NamedElement classOrNamespace_Element = (OS_NamedElement) element;

					var enl2n = classOrNamespace_Element.getEnName();

					final String element_name = classOrNamespace_Element.name();
					if (element_name.equals(name)) {
						EN_Name.assertUnderstanding(enl2n,
								new ENU_LookupResult(Result, level, (alreadySearched.getList())));

						Result.add(name, level, element, aPackage.getContext()); // TODO which context do we set it to?
					}
				}
			}
		}
	}

	private final Context _parent;

	private final ImportStatement carrier;

	@NotNull
	Map<List<IdentExpression>, NPC> npcs = new HashMap<>();

	public ImportContext(final Context aParent, final ImportStatement imp) {
		_parent = aParent;
		carrier = imp;
	}

	@Override
	public @NotNull Compilation compilation() {
		return (@NotNull Compilation) module().getCompilation();
	}

	private NPC getNonPackageComprehension(final List<IdentExpression> x) {
		if (npcs.containsKey(x)) {
			return npcs.get(x);
		}

		final NPC npc = new NPC(x);
		npcs.put(x, npc);
		return npc;
	}

	@Override
	public Context getParent() {
		return _parent;
	}

	@Override
	public LookupResultList lookup(final String name, final int level, final @NotNull LookupResultList Result,
								   final @NotNull ISearchList alreadySearched, final boolean one) {
		alreadySearched.add(this);
//		tripleo.elijah.util.Stupidity.println_err_2("2002 "+importStatement.importList());
		Compilation compilation = compilation();
		for (final Qualident importStatementItem : carrier.parts()) {
//			tripleo.elijah.util.Stupidity.println_err_2("2005 "+importStatementItem);
			if (compilation.isPackage(importStatementItem.toString())) {
				final OS_Package aPackage = compilation.getPackage(importStatementItem);
//				LogEvent.logEvent(4003 , ""+aPackage.getElements());
				for (final OS_Element element : aPackage.getElements()) {
//					tripleo.elijah.util.Stupidity.println_err_2("4002 "+element);
					if (element instanceof NamespaceStatement
							&& ((NamespaceStatement) element).getKind() == NamespaceTypes.MODULE) {
//		                LogEvent.logEvent(4103, "");
						final NamespaceContext namespaceContext = (NamespaceContext) element.getContext();
						alreadySearched.add(namespaceContext);
						namespaceContext.lookup(name, level, Result, alreadySearched, true);
					} else if (element instanceof final @NotNull OS_NamedElement element2) {
						if (element2.name().equals(name)) {
							Result.add(name, level, element, this);
							break; // shortcut: should only have one in scope
						}
					}
				}
			} else {
				// find directly imported elements
				List<IdentExpression> x = importStatementItem.parts();
				NPC npc = getNonPackageComprehension(x);
				npc.checkLast(name, level, Result, alreadySearched, compilation);
			}
		}
		if (carrier.getParent() != null) {
			final Context context = getParent();
			if (!alreadySearched.contains(context) || !one)
				return context.lookup(name, level + 1, Result, alreadySearched, false);
		}
		return Result;
	}

	@Override
	public @NotNull OS_Module module() {
		return _parent.module();
	}
}

//
//
//
