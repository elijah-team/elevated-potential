package tripleo.elijah.ci;

import java.util.List;

public interface CiQualidentList {
	void add(CiQualident qid);

	List<CiQualident> parts();
}
