package tripleo.elijah_durable_elevated.elijah.lang.nextgen.names.impl;

import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;

import java.util.Objects;

public final class EN_NameUsage
		implements EN_Usage {
	private final EN_Name                        theName;
	private final DeduceElement3_IdentTableEntry deduceElement3_identTableEntry;

	public EN_NameUsage(EN_Name theName, DeduceElement3_IdentTableEntry deduceElement3_identTableEntry) {
		this.theName                        = theName;
		this.deduceElement3_identTableEntry = deduceElement3_identTableEntry;
	}

	public EN_Name theName() {
		return theName;
	}

	public DeduceElement3_IdentTableEntry deduceElement3_identTableEntry() {
		return deduceElement3_identTableEntry;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (EN_NameUsage) obj;
		return Objects.equals(this.theName, that.theName) &&
				Objects.equals(this.deduceElement3_identTableEntry, that.deduceElement3_identTableEntry);
	}

	@Override
	public int hashCode() {
		return Objects.hash(theName, deduceElement3_identTableEntry);
	}

	@Override
	public String toString() {
		return "EN_NameUsage[" +
				"theName=" + theName + ", " +
				"deduceElement3_identTableEntry=" + deduceElement3_identTableEntry + ']';
	}

}
