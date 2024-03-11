package tripleo.elijah_durable_elevated.elijah.lang.imports;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.IImportContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah_durable_elevated.elijah.lang.impl.QualidentListImpl;
import tripleo.elijah_durable_elevated.elijah.lang.nextgen.names.impl.*;

import java.util.List;

/**
 * Created 8/7/20 2:10 AM
 */
public class NormalImportStatement extends _BaseImportStatement {
	private final QualidentList importList = new QualidentListImpl();
	private Context _ctx;
	final OS_Element parent;

	public NormalImportStatement(final OS_Element aParent) {
		parent = aParent;
		if (parent instanceof OS_Container) {
			((OS_Container) parent).add(this);
		} else
			throw new NotImplementedException();
	}

	public void addNormalPart(final @NotNull Qualident aQualident) {
		importList.add(aQualident);

		// README a lot of code on the first shot.
		// satisfies spec, tho
		var size = aQualident.parts().size();

		switch (size) {
		case 1 -> {
			var first = aQualident.parts().get(0);

			var fn = first.getName();

			// README at first glance, I don't like this as it seems redundant or something
			// like that
			fn.addUnderstanding(new ENU_PackageRoot());
			fn.addUnderstanding(new ENU_PackageTerminator());
		}
		case 2 -> {
			var first = aQualident.parts().get(0);
			var last = aQualident.parts().get(size - 1);

			// README same as above, just a little less
			var fn = first.getName();
			fn.addUnderstanding(new ENU_PackageRoot());
			var ln = last.getName();
			ln.addUnderstanding(new ENU_PackageTerminator());
		}
		default -> {
			var first = aQualident.parts().get(0);
			var last = aQualident.parts().get(size - 1);

			var fn = first.getName();
			fn.addUnderstanding(new ENU_PackageRoot());
			var ln = last.getName();
			ln.addUnderstanding(new ENU_PackageTerminator());

			// for (int i = 1; i < size-2; i++) {
			// var n = first.getName();
			// n.addUnderstanding(new ENU_PackageElement());
			// }
		}
		}

		for (IdentExpression part : aQualident.parts()) {
			part.getName().addUnderstanding(new ENU_PackageElement());
		}
	}

	@Override
	public Context getContext() {
		return parent.getContext();
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	public @NotNull Context myContext() {
		assert _ctx != null;
		return _ctx;
	}

	@Override
	public @Nullable String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Qualident> parts() {
		return importList.parts();
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