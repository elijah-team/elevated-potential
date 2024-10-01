/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.IFunctionContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_elevateder.lang.types.OS_FuncTypeImpl;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.*;

/**
 * Created 6/27/21 6:42 AM
 */
public abstract class BaseFunctionDef implements FunctionDef, Documentable, ClassItem, OS_Container, OS_NamedElement {

	protected @NotNull Attached _a;
	protected Species _species;
	protected FormalArgList mFal = new FormalArgListImpl(); // remove final for FunctionDefBuilder
	protected Scope3 scope3;
	@Nullable List<AnnotationClause> annotations = null;
	private AccessNotation access_note;
	private El_Category category;
	protected IdentExpression funName;

	protected FunctionModifiers mod;
	protected TypeName rt;

	// region arglist

	protected EN_Name __n;

	@Override
	public void add(final FunctionItem seq) {

	}

	@Override // OS_Container
	public void add(final OS_Element anElement) {
		if (anElement instanceof FunctionItem) {
//			mScope2.add((StatementItem) anElement);
			scope3.add(anElement);
		} else
			throw new IllegalStateException(String.format("Cant add %s to FunctionDef", anElement));
	}

	public void addAnnotation(final AnnotationClause a) {
		if (annotations == null)
			annotations = new ArrayList<AnnotationClause>();
		annotations.add(a);
	}

	// endregion

	@Override // Documentable
	public void addDocString(final Token aText) {
		scope3.addDocString(aText);
	}

	public @NotNull Iterable<AnnotationPart> annotationIterable() {
		List<AnnotationPart> aps = new ArrayList<AnnotationPart>();
		if (annotations == null)
			return aps;
		for (AnnotationClause annotationClause : annotations) {
			for (AnnotationPart annotationPart : annotationClause.aps()) {
				aps.add(annotationPart);
			}
		}
		return aps;
	}

	// region items

	@Override
	public FormalArgList fal() {
		return mFal;
	}

	@Override
	public AccessNotation getAccess() {
		return access_note;
	}

	@Override
	public @NotNull List<FormalArgListItem> getArgs() {
		return mFal.items();
	}

	@Override
	public El_Category getCategory() {
		return category;
	}

	@Override // OS_Element
	public Context getContext() {
		if (_a == null) {
			SimplePrintLoggerToRemoveSoon.println_err_4("903106 creation logic error [IllegalStateException]");
			return null;
		}
		return _a.getContext();
	}

	// endregion

	// region name

	@Override
	public EN_Name getEnName() {
		if (__n == null) {
			__n = EN_Name.create(name());
		}
		return __n;
	}

	@Override
	public @NotNull List<FunctionItem> getItems() {
		if (scope3 == null)
			return Collections.emptyList(); // README ie. interfaces (parent.hdr.type == INTERFACE)

		List<FunctionItem> collection = new ArrayList<FunctionItem>();
		for (OS_Element element : scope3.items()) {
			if (element instanceof FunctionItem)
				collection.add((FunctionItem) element);
		}
		return collection;
		// return mScope2.items;
	}

	@Override
	public IdentExpression getNameNode() {
		return funName;
	}

	// endregion

	// region context

	@Override
	public OS_FuncType getOS_Type() {
		return new OS_FuncTypeImpl(this);
	}

	@Override // OS_Element
	public abstract @Nullable OS_Element getParent();

	@Override
	public Species getSpecies() {
		return _species;
	}

	// endregion

	// region annotations

	public boolean hasItem(OS_Element element) {
		return scope3.items().contains(element);
	}

	@Override // OS_Container
	public @NotNull List<OS_NamedElement> items() {
		final ArrayList<OS_NamedElement> a = new ArrayList<OS_NamedElement>();
		for (final OS_Element functionItem : scope3.items()) {
			if (functionItem instanceof OS_NamedElement)
				a.add((OS_NamedElement) functionItem);
		}
		return a;
	}

	@Override
	@NotNull // OS_Element2
	public String name() {
		if (funName == null)
			return "";
		return funName.getText();
	}

	@Override
	public abstract void postConstruct();

	@Override
	public @Nullable TypeName returnType() {
		return rt;
	}

	@Override
	public void scope(Scope3 sco) {
		scope3 = sco;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void set(final FunctionModifiers mod) {
		this.mod = mod;
	}

	// endregion

	// region Documentable

	@Override
	public void setAbstract(final boolean b) {

	}

	// endregion

	@Override
	public void setAccess(AccessNotation aNotation) {
		access_note = aNotation;
	}

	@Override
	public void setAnnotations(List<AnnotationClause> aAnnotationClauses) {
		annotations = aAnnotationClauses;
	}

	// region ClassItem

	@Override
	public void setBody(final FunctionBody aFunctionBody) {

	}

	@Override
	public void setCategory(El_Category aCategory) {
		category = aCategory;
	}

	@Override
	public void setContext(final IFunctionContext ctx) {
		if (_a == null) {
			_a = new AttachedImpl(ctx);
		} else
			_a.setContext(ctx);
	}

	@Override
	public void setFal(FormalArgList fal) {
		mFal = fal;
	}

	@Override
	public abstract void setHeader(FunctionHeader aFunctionHeader);

	@Override
	public void setName(final IdentExpression aText) {
		funName = aText;
	}

	@Override
	public void setReturnType(final TypeName tn) {

	}

	@Override
	public void setSpecies(final Species aSpecies) {
		_species = aSpecies;
	}

	// endregion

	@Override
	public void visitGen(final ElElementVisitor visit) {

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
