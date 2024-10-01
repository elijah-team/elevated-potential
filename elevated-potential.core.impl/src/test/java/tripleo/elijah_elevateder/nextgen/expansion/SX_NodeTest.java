package tripleo.elijah_elevateder.nextgen.expansion;

import org.junit.Test;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.test_help.Boilerplate;
import tripleo.elijah_elevated_durable.world_impl.DefaultWorldModule;

@SuppressWarnings("NewClassNamingConvention")
public class SX_NodeTest {

	@Test
	public void testFullText() {
		final Boilerplate b = new Boilerplate();
		b.get();
		final Compilation comp = (Compilation) b.comp;

		final OS_Module mod = comp.moduleBuilder().withFileName("filename.elijah").addToCompilation().build();

		var wm = new DefaultWorldModule(mod, comp.getCompilationEnclosure());

		final OutputFileFactoryParams p = new OutputFileFactoryParams(wm, comp.getCompilationEnclosure());
		// final GenerateFiles fgen =
		// OutputFileFactory.create(CompilationAlways.defaultPrelude(), p, fileGen);

		/*
		 * final SM_ClassDeclaration node = new SM_ClassDeclaration() {
		 * 
		 * @Override public @Nullable SM_ClassBody classBody() { return null; }
		 * 
		 * @Override public @NotNull SM_ClassInheritance inheritance() { return new
		 * SM_ClassInheritance() {
		 * 
		 * @Override public @NotNull List<SM_Name> names() { return List_of(new
		 * SM_Name() {
		 * 
		 * @Override public @NotNull String getText() { return "Arguments"; } }); } }; }
		 * 
		 * @Override public @NotNull SM_Name name() { return new SM_Name() {
		 * 
		 * @Override public @NotNull String getText() { return "Main"; } }; }
		 * 
		 * @Override public @NotNull SM_ClassSubtype subType() { return
		 * SM_ClassSubtype.NORMAL; } };
		 */

		// fgen.forNode(node);
	}
}
