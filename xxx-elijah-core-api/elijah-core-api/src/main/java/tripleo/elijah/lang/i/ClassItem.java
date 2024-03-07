package tripleo.elijah.lang.i;

/**
 * Marker interface to represent elements that can be added to a class or a
 * namespace or an enum
 *
 * @see {@link ClassStatement#add(OS_Element)}
 * @see {@link NamespaceStatement#add(OS_Element)}
 * @see {@link EnumStatement#add(OS_Element)}
 */
public interface ClassItem extends OS_Element {

	AccessNotation getAccess();

	El_Category getCategory();

	void setAccess(AccessNotation aNotation);

	void setCategory(El_Category aCategory);
}
