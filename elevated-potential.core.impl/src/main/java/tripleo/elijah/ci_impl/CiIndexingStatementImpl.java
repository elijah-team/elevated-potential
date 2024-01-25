package tripleo.elijah.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiIndexingStatement;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.lang.i.OS_Module;

/**
 * @author Tripleo
 * <p>
 * Created 	Apr 15, 2020 at 4:59:21 AM
 * Created 1/8/21 7:19 AM
 */
public class CiIndexingStatementImpl implements CiIndexingStatement {
	private CiExpressionList exprs;
	private Token            name;
	private final @Nullable CompilerInstructions parent;

	public CiIndexingStatementImpl(final CompilerInstructions module) {
		this.parent = module;
	}

	public CiIndexingStatementImpl(final OS_Module module) {
		//this.parent = module;
		this.parent = null;
	}

	@Override
	public void setExprs(final CiExpressionList el) {
		this.exprs = el;
	}

	@Override
	public void setName(final Token i1) {
		this.name = i1;
	}
}
