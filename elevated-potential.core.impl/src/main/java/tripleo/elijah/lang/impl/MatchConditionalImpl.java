package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.contexts.MatchConditionalContext;
import tripleo.elijah.contexts.MatchContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tripleo
 *         <p>
 *         Created Apr 15, 2020 at 10:11:16 PM
 */
public class MatchConditionalImpl implements MatchConditional, OS_Element, StatementItem, FunctionItem {
	public MatchConditionalImpl(final OS_Element parent, final Context ignoredParentContext) {
		this.parent = parent;
//		this._ctx = new SingleIdentContext(parentContext, this);
	}

	public class MatchArm_TypeMatch__ implements MatchArm_TypeMatch {
		// private final List<FunctionItem> items = new ArrayList<FunctionItem>();
		private final Context ___ctx = new MatchConditionalContext(// MatchConditional.this.getContext(), this);
				getParent().getParent().getContext(), this);
		TypeName tn /* = new RegularTypeName() */;
		private Scope3 scope3;
		// private List<Token> docstrings = new ArrayList<Token>();
		private IdentExpression ident;

		@Override
		public void add(final FunctionItem aItem) {
			scope3.add(aItem);
		}

		@Override
		public void addDocString(final Token text) {
			scope3.addDocString(text);
		}

		@Override
		public @NotNull Context getContext() {
			return ___ctx;
		}

		@Override
		public IdentExpression getIdent() {
			return ident;
		}

		@Override
		public @NotNull List<FunctionItem> getItems() {
			List<FunctionItem> collection = new ArrayList<>();
			for (OS_Element element : scope3.items()) {
				if (element instanceof FunctionItem)
					collection.add((FunctionItem) element);
			}
			return collection;
//			return items;
		}

		@Override
		public @NotNull OS_Element getParent() {
			return MatchConditionalImpl.this;
		}

		@Override
		public TypeName getTypeName() {
			return tn;
		}

		@Override
		public void ident(final IdentExpression i1) {
			this.ident = i1;
		}

		@Override
		public void scope(Scope3 sco) {
			scope3 = sco;
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnintendedUseException("niy");
		}

		@Override
		public void setTypeName(final TypeName typeName) {
			tn = typeName;
		}
	}

	public class MatchConditionalPart2 implements MC1, IMatchConditionalPart2 {
		private final Context ___ctx = new MatchConditionalContext(MatchConditionalImpl.this.getContext(), this);
		private IExpression matching_expression;
		private Scope3 scope3;

		@Override
		public void add(final FunctionItem aItem) {
			scope3.add(aItem);
		}

		@Override
		public void addDocString(final Token text) {
			scope3.addDocString(text);
		}

		@Override
		public void expr(final IExpression expr) {
			this.matching_expression = expr;
		}

		@Override
		public @NotNull Context getContext() {
			return ___ctx;
		}

		@Override
		public @NotNull List<FunctionItem> getItems() {
			List<FunctionItem> collection = new ArrayList<FunctionItem>();
			for (OS_Element element : scope3.items()) {
				if (element instanceof FunctionItem)
					collection.add((FunctionItem) element);
			}
			return collection;
//			return items;
		}

		@Override
		public IExpression getMatchingExpression() {
			return matching_expression;
		}

		@Override
		public @NotNull OS_Element getParent() {
			return MatchConditionalImpl.this;
		}

		@Override
		public void scope(Scope3 sco) {
			scope3 = sco;
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnintendedUseException("niy");
		}
	}

	// private final SingleIdentContext _ctx;
	private final List<MC1> parts = new ArrayList<MC1>();

	private MatchContext __ctx;

	private IExpression expr;

	private OS_Element parent;

	public class MatchConditionalPart3__ implements MatchConditionalPart3 {

		private final Context ___ctx = new MatchConditionalContext(MatchConditionalImpl.this.getContext(), this);

		// private final List<FunctionItem> items = new ArrayList<FunctionItem>();
		private final @Nullable List<Token>     docstrings = null;
		private                 IdentExpression matching_expression;
		private Scope3 scope3;

		@Override
		public void add(final FunctionItem aItem) {
			scope3.add(aItem);
			// items.add(aItem);
		}

		@Override
		public void addDocString(final Token text) {
//			if (docstrings == null)
//				docstrings = new ArrayList<Token>();
//			docstrings.add(text);
			scope3.addDocString(text);
		}

		@Override
		public void expr(final IdentExpression expr) {
			this.matching_expression = expr;
		}

		@Override
		public @NotNull Context getContext() {
			return ___ctx;
		}

		@Override
		public @NotNull List<FunctionItem> getItems() {
			List<FunctionItem> collection = new ArrayList<FunctionItem>();
			for (OS_Element element : scope3.items()) {
				if (element instanceof FunctionItem)
					collection.add((FunctionItem) element);
			}
			return collection;
//			return items;
		}

		@Override
		public @NotNull OS_Element getParent() {
			return MatchConditionalImpl.this;
		}

		@Override
		public void scope(Scope3 sco) {
			scope3 = sco;
		}

		@Override
		public void serializeTo(final SmallWriter sw) {
			throw new UnintendedUseException("niy");
		}
	}

	@Override
	public void expr(final IExpression expr) {
		this.expr = expr;
	}

	// region OS_Element

	@Override
	public Context getContext() {
		return __ctx;
	}

	@Override
	public IExpression getExpr() {
		return expr;
	}

	// endregion

	/**
	 * @category OS_Element
	 */
	@Override
	public OS_Element getParent() {
		return this.parent;
	}

	@Override
	public @NotNull List<MC1> getParts() {
		return parts;
	}

	//
	// EXPR
	//

	@Override
	public @NotNull IMatchConditionalPart2 normal() {
		final IMatchConditionalPart2 p = new MatchConditionalPart2();
		parts.add(p);
		return p;
	}

	@Override
	public void postConstruct() {
	}

	public void setContext(final MatchContext ctx) {
		__ctx = ctx;
	}

	@Override
	public void setParent(final OS_Element aParent) {
		this.parent = aParent;
	}

	//
	//
	//
	@Override
	public @NotNull MatchArm_TypeMatch typeMatch() {
		final MatchArm_TypeMatch p = new MatchArm_TypeMatch__();
		parts.add(p);
		return p;
	}

	@Override
	public @NotNull MatchConditionalPart3 valNormal() {
		final MatchConditionalPart3 p = new MatchConditionalPart3__();
		parts.add(p);
		return p;
	}

	/**
	 * @category OS_Element
	 */
	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitMatchConditional(this);
	}
}
