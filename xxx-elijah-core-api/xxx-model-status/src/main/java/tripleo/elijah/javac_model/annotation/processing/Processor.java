package tripleo.elijah.javac_model.annotation.processing;


import tripleo.elijah.javac_model.lang.model.element.*;

import javax.lang.model.SourceVersion;
import java.util.Set;

public interface Processor {
	Set<String> getSupportedOptions();

	Set<String> getSupportedAnnotationTypes();

	SourceVersion getSupportedSourceVersion();

	void init(ProcessingEnvironment processingEnv);

	boolean process(Set<? extends TypeElement> annotations,
					RoundEnvironment roundEnv);

	Iterable<? extends Completion> getCompletions(Element element,
												  AnnotationMirror annotation,
												  ExecutableElement member,
												  String userText);
}
