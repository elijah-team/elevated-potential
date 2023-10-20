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
import com.google.common.base.*;
import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;

import java.util.*;

/**
 * Created 8/30/20 1:49 PM
 */
public class SyntacticBlockImpl
		implements OS_Element, OS_Container, StatementItem, tripleo.elijah.lang.i.SyntacticBlock {

	private final List<FunctionItem> _items = new ArrayList<FunctionItem>();
	private final OS_Element             _parent;
	private       ISyntacticBlockContext ctx;
	private       Scope3                 scope3;

	public SyntacticBlockImpl(final OS_Element aParent) {
		_parent = aParent;
	}

	@Override
	public void add(final OS_Element anElement) {
		if (!(anElement instanceof FunctionItem))
			return; // TODO throw?
		scope3.add(anElement);
	}

	@Override
	public void addDocString(Token s1) {
		scope3.addDocString(s1);
	}

	@Override
	public Context getContext() {
		return ctx;
	}

	@Override
	public @NotNull List<FunctionItem> getItems() {
		List<FunctionItem> collection = new ArrayList<FunctionItem>();
		for (OS_Element element : scope3.items()) {
			if (element instanceof FunctionItem)
				collection.add((FunctionItem) element);
		}
		return collection;
		// return _items;
	}

	@Override
	public OS_Element getParent() {
		return _parent;
	}

	@Override
	public @NotNull List<OS_Element2> items() {
		final Collection<OS_Element> items = Collections2.filter(scope3.items(), new Predicate<OS_Element>() {
			@Override
			public boolean apply(@Nullable OS_Element input) {
				return input instanceof OS_Element2;
			}
		});
		Collection<OS_Element2> c = Collections2.transform(items, new Function<OS_Element, OS_Element2>() {
			@Nullable
			@Override
			public @org.jetbrains.annotations.Nullable OS_Element2 apply(@Nullable OS_Element input) {
				return (OS_Element2) input;
			}
		});
		return new ArrayList<OS_Element2>(c);
	}

	@Override
	public void postConstruct() {
	}

	@Override
	public void scope(Scope3 sco) {
		scope3 = sco;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void setContext(final ISyntacticBlockContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitSyntacticBlock(this);
	}

}

//
//
//
