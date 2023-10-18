package tripleo.elijah.lang.nextgen.names.impl;

import tripleo.elijah.lang.i.AliasStatement;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;

import java.util.Objects;

public final class ENU_AliasedFrom implements EN_Understanding {
	private final AliasStatement aliasStatement;

	public ENU_AliasedFrom(AliasStatement aliasStatement) {
		this.aliasStatement = aliasStatement;
	}

	public AliasStatement aliasStatement() {
		return aliasStatement;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ENU_AliasedFrom) obj;
		return Objects.equals(this.aliasStatement, that.aliasStatement);
	}

	@Override
	public int hashCode() {
		return Objects.hash(aliasStatement);
	}

	@Override
	public String toString() {
		return "ENU_AliasedFrom[" +
				"aliasStatement=" + aliasStatement + ']';
	}

}
