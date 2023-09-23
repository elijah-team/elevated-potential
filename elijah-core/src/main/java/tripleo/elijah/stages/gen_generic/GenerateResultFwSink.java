package tripleo.elijah.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;
import tripleo.util.buffer.Buffer;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GenerateResultFwSink implements GenerateResult {
	GenerateResultSink resultSink;
	private final GenerateC generateC;

	public GenerateResultFwSink(@NotNull IPipelineAccess aPa) {
		resultSink = new DefaultGenerateResultSink(aPa);
//        generateC = aPa.getCompilationEnclosure().
		generateC = null;
	}

	@Override
	public void close() {
//        resultSink.close();
	}

	@Override
	public void add(Buffer b, EvaNode n, TY ty, LibraryStatementPart aLsp, Dependency d) {
//        resultSink.add
	}

	@Override
	public void addClass(TY ty, EvaClass aClass, Buffer aBuf, LibraryStatementPart aLsp) {
		var garishClass = aClass._living.getGarish();

		resultSink.addClass_1(garishClass, this, generateC);
	}

	@Override
	public void additional(GenerateResult aGenerateResult) {
		resultSink.additional(this);
	}

	@Override
	public void addConstructor(EvaConstructor aEvaConstructor, Buffer aBuffer, TY aTY, LibraryStatementPart aLsp) {
//        var l = aEvaConstructor._living.

		final WhyNotGarish_Constructor x    = generateC.a_lookup(aEvaConstructor);
		final Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC._LOG());
		final GenerateResultEnv        env  = new GenerateResultEnv(resultSink, this, new WorkManager(), new WorkList(), null);
		final List<C2C_Result>         l    = x.getResults();

		gcfm.generateCodeForMethod2(aEvaConstructor, env);

		resultSink.addFunction(aEvaConstructor, l, generateC);
	}

	@Override
	public void addFunction(BaseEvaFunction aGeneratedFunction, Buffer aBuffer, TY aTY, LibraryStatementPart aLsp) {
//        var l = aEvaConstructor._living.

		final List<C2C_Result>      l  = Helpers.List_of();
		final WhyNotGarish_Function x  = generateC.a_lookup(aGeneratedFunction);
		final BaseEvaFunction       gf = aGeneratedFunction;

		Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC._LOG());
		GenerateResultEnv        env  = new GenerateResultEnv(resultSink, this, new WorkManager(), new WorkList(), null);
		gcfm.generateCodeForMethod2(gf, env);

		resultSink.addFunction(aGeneratedFunction, l, generateC);
	}

	@Override
	public void completeItem(GenerateResultItem item) {
//		resultSink.additional(Helpers.List_of(item));
	}

	@Override
	public void addNamespace(TY ty, EvaNamespace aNamespace, Buffer aBuf, LibraryStatementPart aLsp) {
		final GarishNamespace gn = aNamespace._living.getGarish();

		resultSink.addNamespace_1(gn, this, generateC);
	}

	@Override
	public void addWatcher(IGenerateResultWatcher w) {
		throw new UnintendedUseException();
	}

	@Override
	public void observe(Observer<GenerateResultItem> obs) {
		throw new UnintendedUseException();
	}

	@Override
	public void signalDone() {
		throw new UnintendedUseException();
	}

	@Override
	public void outputFiles(Consumer<Map<String, OutputFileC>> cmso) {
		throw new UnintendedUseException();
	}

	@Override
	public List<Old_GenerateResultItem> results() {
		throw new UnintendedUseException();
//		return null;
	}

	@Override
	public void signalDone(Map<String, OutputFileC> aOutputFiles) {
		throw new UnintendedUseException();
	}

	@Override
	public void subscribeCompletedItems(Observer<GenerateResultItem> aGenerateResultItemObserver) {
		throw new UnintendedUseException();
	}

	public GenerateResultSink getResultSink() {
		return resultSink;
	}
}
