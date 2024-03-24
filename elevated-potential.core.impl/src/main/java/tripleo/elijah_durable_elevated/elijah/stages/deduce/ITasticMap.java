package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import tripleo.elijah_durable_elevated.elijah.stages.deduce.tastic.ITastic;

interface ITasticMap {

	boolean containsKey(Object aO);

	ITastic get(Object aO);

	void put(Object aO, ITastic aR);

}
