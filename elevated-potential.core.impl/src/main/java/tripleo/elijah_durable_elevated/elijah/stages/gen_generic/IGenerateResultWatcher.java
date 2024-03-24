package tripleo.elijah_durable_elevated.elijah.stages.gen_generic;

public interface IGenerateResultWatcher {
	void complete();

	void item(GenerateResultItem item);
}