package tripleo.elijah_elevated.fluffy.i;

import tripleo.elijah_elevated_durable.lang_impl.OS_ModuleImpl;

import java.util.List;

public interface FluffyModule {
	void find_all_entry_points();

	void find_multiple_items(FluffyComp aFluffyComp, OS_ModuleImpl.Complaint c);

	FluffyLsp lsp();

	List<FluffyMember> members();

	String name();
}
