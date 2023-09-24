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
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.*;

import java.util.*;

/**
 * Created 3/29/21 5:11 PM
 */
public abstract class _CommonNC {
	protected final  List<ClassItem>        items       = new ArrayList<ClassItem>();
	private final    List<String>           mDocs       = new ArrayList<String>();
	public @NotNull  Attached               _a          = new AttachedImpl();
	protected        OS_Package             _packageName;
	protected        IdentExpression        nameToken;
	@Nullable        List<AnnotationClause> annotations = null;
	// region ClassItem
	private          AccessNotation         access_note;
	private @NotNull List<AccessNotation>   accesses    = new ArrayList<AccessNotation>();
	private          El_Category            category;

	protected EN_Name __common_nc__n;

	public void addAccess(final AccessNotation acs) {
		accesses.add(acs);
	}

	public void addAnnotation(final AnnotationClause a) {
		if (annotations == null)
			annotations = new ArrayList<AnnotationClause>();
		annotations.add(a);
	}

	public void addAnnotations(@Nullable List<AnnotationClause> as) {
		if (as == null)
			return;
		for (AnnotationClause annotationClause : as) {
			addAnnotation(annotationClause);
		}
	}

	public void addDocString(final @NotNull Token aText) {
		mDocs.add(aText.getText());
	}

	public AccessNotation getAccess() {
		return access_note;
	}

	public El_Category getCategory() {
		return category;
	}

	public EN_Name getEnName() {
		if (__common_nc__n == null) {
			__common_nc__n = EN_Name.create(name());
		}
		return __common_nc__n;
	}

	public @NotNull List<ClassItem> getItems() {
		return items;
	}

	public String getName() {
		if (nameToken == null)
			return "";
		return nameToken.getText();
	}

	public OS_Package getPackageName() {
		return _packageName;
	}

	public boolean hasItem(OS_Element element) {
		if (!(element instanceof ClassItem))
			return false;
		return items.contains(element);
	}

	// OS_Container
	public @NotNull List<OS_Element2> items() {
		final ArrayList<OS_Element2> a = new ArrayList<OS_Element2>();
		for (final ClassItem functionItem : getItems()) {
			final boolean b = functionItem instanceof OS_Element2;
			if (b)
				a.add((OS_Element2) functionItem);
		}
		return a;
	}

	// OS_Element2
	public String name() {
		return getName();
	}

	public void setAccess(AccessNotation aNotation) {
		access_note = aNotation;
	}

	public void setCategory(El_Category aCategory) {
		category = aCategory;
	}

	public void setName(final IdentExpression i1) {
		nameToken = i1;
	}

	// endregion

	public void setPackageName(final OS_Package aPackageName) {
		_packageName = aPackageName;
	}

	public void walkAnnotations(@NotNull AnnotationWalker annotationWalker) {
		if (annotations == null)
			return;
		for (AnnotationClause annotationClause : annotations) {
			for (AnnotationPart annotationPart : annotationClause.aps()) {
				annotationWalker.annotation(annotationPart);
			}
		}
	}
}

//
//
//
