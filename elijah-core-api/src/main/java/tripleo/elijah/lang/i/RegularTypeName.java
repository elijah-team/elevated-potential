package tripleo.elijah.lang.i;

public interface RegularTypeName extends NormalTypeName {

	void addGenericPart(TypeNameList tn2);

	Context getContext();

	TypeNameList getGenericPart();

	String getName();

	Qualident getRealName();

	@Override
	OS_Element getResolvedElement();

	@Override
	boolean hasResolvedElement();

	Type kindOfType();

	void setContext(Context ctx);

	void setName(Qualident aS);

	@Override
	void setResolvedElement(OS_Element element);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();
}
