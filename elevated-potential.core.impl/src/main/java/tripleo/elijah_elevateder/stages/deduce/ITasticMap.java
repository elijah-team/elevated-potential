package tripleo.elijah_elevateder.stages.deduce;

import tripleo.elijah_elevateder.stages.deduce.tastic.ITastic;

interface ITasticMap {

	boolean containsKey(Object aO);

	ITastic get(Object aO);

	void put(Object aO, ITastic aR);

}
