package tripleo.elijah.lang.imports;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.*;

import java.util.*;

/**
 * Created 8/7/20 2:09 AM
 */
public class RootedImportStatement extends _BaseImportStatement {
	private Context _ctx;
	private QualidentList importList = new QualidentListImpl(); // remove final for ImportStatementBuilder
	final OS_Element parent;
	/**
	 * Used in from syntax
	 *
	 * @category from
	 */
	private Qualident root;

	public RootedImportStatement(final OS_Element aParent) {
		parent = aParent;
		if (parent instanceof OS_Container) {
			((OS_Container) parent).add(this);
		} else
			throw new NotImplementedException();
	}

	@Override
	public Context getContext() {
		return parent.getContext();
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	public Qualident getRoot() {
		return root;
	}

	public QualidentList importList() {
		return importList;
	}

	/**
	 * Used in from syntax
	 *
	 * @category from
	 */
	public void importRoot(final Qualident xyz) {
		setRoot(xyz);
	}

	@Override
	public @Nullable String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<Qualident> parts() {
		final List<Qualident> r = new ArrayList<Qualident>();
		for (final Qualident qualident : importList.parts()) {
			final Qualident q = new QualidentImpl();
			// TODO what the hell does this do? Should it be `root'
			for (final IdentExpression part : q.parts()) {
				q.append(part);
			}
			for (final IdentExpression part : qualident.parts()) {
				q.append(part);
			}
			r.add(q);
		}
		return r;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void setContext(final IImportContext ctx) {
		_ctx = (Context) ctx;
	}

	public void setImportList(QualidentList qil) {
		importList = qil;
	}

	public void setRoot(final Qualident root) {
		this.root = root;
	}
}

//
//
//
