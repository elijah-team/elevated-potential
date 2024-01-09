package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.*;

import java.util.List;

public interface OS_Package {
	void addElement(OS_Element element);

	Context getContext();

	List<OS_Element> getElements();

	String getName();

	Qualident getName2();

	void setContext(IPackageContext cur);
}
