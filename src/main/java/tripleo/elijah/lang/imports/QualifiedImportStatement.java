package tripleo.elijah.lang.imports;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;

import java.util.*;

/**
 * Created 8/7/20 2:09 AM
 */
public class QualifiedImportStatement extends _BaseImportStatement {
	public static class Part {
		public Qualident base;
		public IdentList idents;

		public Part(Qualident q3, IdentList il) {
			base = q3;
			idents = il;
		}
	}

	final OS_Element parent;
	private final List<Part> _parts = new ArrayList<Part>();

	private Context _ctx;

	public QualifiedImportStatement(final OS_Element aParent) {
		parent = aParent;
		if (parent instanceof OS_Container) {
			((OS_Container) parent).add(this);
		} else
			throw new IllegalStateException();
	}

	public void addPart(final Part p) {
		_parts.add(p);
	}

	public void addSelectivePart(final Qualident aQualident, final IdentList il) {
		final Part p = new Part(aQualident, il);
//		p.base = aQualident;
//		p.idents = il;
		addPart(p);
	}

	@Override
	public Context getContext() {
		return parent.getContext();
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	@Override
	public @Nullable String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<Qualident> parts() {
		final List<Qualident> r = new ArrayList<Qualident>();
		for (final Part part : _parts) {
			r.add(part.base);
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

}

//
//
//
