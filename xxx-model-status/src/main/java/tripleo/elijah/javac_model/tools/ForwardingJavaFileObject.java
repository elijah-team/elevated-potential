package tripleo.elijah.javac_model.tools;

import tripleo.elijah.javac_model.lang.model.element.Modifier;
import tripleo.elijah.javac_model.lang.model.element.NestingKind;

public class ForwardingJavaFileObject<F extends JavaFileObject>
		extends ForwardingFileObject<F>
		implements JavaFileObject {

	protected ForwardingJavaFileObject(F fileObject) {
		super(fileObject);
	}

	@Override
	public Kind getKind() {
		return fileObject.getKind();
	}

	@Override
	public boolean isNameCompatible(String simpleName, Kind kind) {
		return fileObject.isNameCompatible(simpleName, kind);
	}

	@Override
	public NestingKind getNestingKind() {
		return fileObject.getNestingKind();
	}

	@Override
	public Modifier getAccessLevel() {
		return fileObject.getAccessLevel();
	}

}
