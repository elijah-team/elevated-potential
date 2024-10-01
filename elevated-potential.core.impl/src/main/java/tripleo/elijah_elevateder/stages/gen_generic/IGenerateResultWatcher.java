package tripleo.elijah_elevateder.stages.gen_generic;

public interface IGenerateResultWatcher {
	public void complete();

	public void item(GenerateResultItem item);
}