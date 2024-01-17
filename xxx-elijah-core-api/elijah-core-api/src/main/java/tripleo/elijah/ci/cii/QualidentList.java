package tripleo.elijah.ci.cii;

import java.util.List;

public interface QualidentList {
	void add(Qualident qid);

	List<Qualident> parts();
}
