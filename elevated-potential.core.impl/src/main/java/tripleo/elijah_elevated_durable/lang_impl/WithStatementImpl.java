/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah_elevateder.contexts.WithContext;

import java.util.*;

/**
 * Created 8/30/20 1:51 PM
 */
public class WithStatementImpl implements OS_Element, OS_Container, StatementItem, tripleo.elijah.lang.i.WithStatement {
	private final List<FunctionItem> _items = new ArrayList<FunctionItem>();
	private final OS_Element  _parent;
	private       WithContext ctx;
	@NotNull
	VariableSequence hidden_seq = new VariableSequenceImpl();
	// private final List<String> mDocs = new ArrayList<String>();
	private Scope3 scope3;

	public WithStatementImpl(final OS_Element aParent) {
		_parent = aParent;
	}

	@Override
	public void add(final OS_Element anElement) {
		if (!(anElement instanceof FunctionItem))
			return;
		_items.add((FunctionItem) anElement);
	}

	@Override
	public void addDocString(final Token aText) {
		scope3.addDocString(aText);
//		mDocs.add(aText.getText());
	}

	@Override
	public @Nullable Context getContext() {
		return null;
	}

	@Override
	public @NotNull List<FunctionItem> getItems() {
		return _items;
	}

	@Override
	public OS_Element getParent() {
		return _parent;
	}

	@Override
	public Collection<VariableStatement> getVarItems() {
		return hidden_seq.items();
	}

	@Override
	public @Nullable List<OS_NamedElement> items() {
		return null;
	}

	@Override
	public VariableStatement nextVarStmt() {
		return hidden_seq.next();
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
	public void setContext(final IWithContext ctx) {
		this.ctx = (WithContext) ctx;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitWithStatement(this);
	}

}

//
//
//
