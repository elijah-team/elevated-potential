HOME=/home/pasCAL/
IDEA_ROOT=$HOME/programs/idea-community

$HOME/.sdkman/candidates/java/17.0.10-graal/bin/java -ea \
-Didea.test.cyclic.buffer.size=1048576 -javaagent:$IDEA_ROOT//lib/idea_rt.jar=33169:$IDEA_ROOT//bin \
-Dfile.encoding=UTF-8 \
-classpath $HOME/.m2/repository/org/junit/platform/junit-platform-launcher/1.10.2/junit-platform-launcher-1.10.2.jar:\
$HOME/.m2/repository/org/junit/vintage/junit-vintage-engine/5.10.2/junit-vintage-engine-5.10.2.jar:\
$IDEA_ROOT//lib/idea_rt.jar:$IDEA_ROOT//plugins/junit/lib/junit5-rt.jar:\
$IDEA_ROOT//plugins/junit/lib/junit-rt.jar:\
$HOME/IdeaProjects/elevated-potential/elevated-potential.core.impl/target/test-classes:\
$HOME/IdeaProjects/elevated-potential/elevated-potential.core.impl/target/classes:\
$HOME/.m2/repository/junit/junit/4.13.2/junit-4.13.2.jar:\
$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:\
$HOME/.m2/repository/org/assertj/assertj-core/3.25.3/assertj-core-3.25.3.jar:\
$HOME/.m2/repository/net/bytebuddy/byte-buddy/1.14.11/byte-buddy-1.14.11.jar:\
$HOME/.m2/repository/com/google/truth/truth/1.4.0/truth-1.4.0.jar:\
$HOME/.m2/repository/org/checkerframework/checker-qual/3.42.0/checker-qual-3.42.0.jar:\
$HOME/.m2/repository/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar:\
$HOME/.m2/repository/com/google/errorprone/error_prone_annotations/2.24.1/error_prone_annotations-2.24.1.jar:\
$HOME/.m2/repository/org/ow2/asm/asm/9.6/asm-9.6.jar:\
$HOME/.m2/repository/org/awaitility/awaitility/4.2.0/awaitility-4.2.0.jar:\
$HOME/.m2/repository/org/hamcrest/hamcrest/2.1/hamcrest-2.1.jar:\
$HOME/IdeaProjects/elevated-potential/xxx-elijah-core-api/target/classes:\
$HOME/IdeaProjects/elevated-potential/elevated-potential.core.api/target/classes:\
$HOME/.m2/repository/org/antlr/antlr-runtime/3.5.3/antlr-runtime-3.5.3.jar:\
$HOME/.m2/repository/io/smallrye/reactive/mutiny/2.5.6/mutiny-2.5.6.jar:\
$HOME/.m2/repository/io/smallrye/common/smallrye-common-annotation/2.2.0/smallrye-common-annotation-2.2.0.jar:\
$HOME/.m2/repository/org/clojure/clojure/1.10.3/clojure-1.10.3.jar:\
$HOME/.m2/repository/org/clojure/spec.alpha/0.2.194/spec.alpha-0.2.194.jar:\
$HOME/.m2/repository/org/clojure/core.specs.alpha/0.2.56/core.specs.alpha-0.2.56.jar:\
$HOME/.m2/repository/org/mockito/mockito-core/5.10.0/mockito-core-5.10.0.jar:\
$HOME/.m2/repository/net/bytebuddy/byte-buddy-agent/1.14.11/byte-buddy-agent-1.14.11.jar:\
$HOME/.m2/repository/org/objenesis/objenesis/3.3/objenesis-3.3.jar:\
$HOME/.m2/repository/com/gitlab/tripleo1/buffers/3038ff102c/buffers-3038ff102c.jar:\
$HOME/.m2/repository/com/gitlab/Tripleo/range/v0.0.3b/range-v0.0.3b.jar:\
$HOME/.m2/repository/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar:\
$HOME/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.annotation/2.2.800/org.eclipse.jdt.annotation-2.2.800.jar:\
$HOME/.m2/repository/org/apache/commons/commons-lang3/3.14.0/commons-lang3-3.14.0.jar:\
$HOME/.m2/repository/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar:\
$HOME/.m2/repository/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar:\
$HOME/.m2/repository/com/github/spotbugs/spotbugs-annotations/4.8.3/spotbugs-annotations-4.8.3.jar:\
$HOME/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:\
$HOME/.m2/repository/com/google/guava/guava/33.0.0-jre/guava-33.0.0-jre.jar:\
$HOME/.m2/repository/com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar:\
$HOME/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:\
$HOME/.m2/repository/com/google/j2objc/j2objc-annotations/2.8/j2objc-annotations-2.8.jar:\
$HOME/.m2/repository/com/tngtech/archunit/archunit-junit4/1.2.1/archunit-junit4-1.2.1.jar:\
$HOME/.m2/repository/com/tngtech/archunit/archunit/1.2.1/archunit-1.2.1.jar:\
$HOME/.m2/repository/io/reactivex/rxjava3/rxjava/3.1.8/rxjava-3.1.8.jar:\
$HOME/.m2/repository/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar:\
$HOME/.m2/repository/commons-codec/commons-codec/1.16.1/commons-codec-1.16.1.jar:\
$HOME/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:\
$HOME/.m2/repository/org/projectlombok/lombok/1.18.30/lombok-1.18.30.jar:\
$HOME/.m2/repository/org/jdeferred/v2/jdeferred-core/2.0.0/jdeferred-core-2.0.0.jar:\
$HOME/.m2/repository/org/jetbrains/annotations/24.1.0/annotations-24.1.0.jar:\
$HOME/.m2/repository/com/google/auto/service/auto-service/1.1.1/auto-service-1.1.1.jar:\
$HOME/.m2/repository/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar:\
$HOME/.m2/repository/com/google/auto/auto-common/1.2.1/auto-common-1.2.1.jar:\
$HOME/.m2/repository/org/jctools/jctools-core/4.0.3/jctools-core-4.0.3.jar:\
$HOME/.m2/repository/org/junit/jupiter/junit-jupiter/5.10.2/junit-jupiter-5.10.2.jar:\
$HOME/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.10.2/junit-jupiter-api-5.10.2.jar:\
$HOME/.m2/repository/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar:\
$HOME/.m2/repository/org/junit/platform/junit-platform-commons/1.10.2/junit-platform-commons-1.10.2.jar:\
$HOME/.m2/repository/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar:\
$HOME/.m2/repository/org/junit/jupiter/junit-jupiter-params/5.10.2/junit-jupiter-params-5.10.2.jar:\
$HOME/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.10.2/junit-jupiter-engine-5.10.2.jar:\
$HOME/.m2/repository/org/junit/platform/junit-platform-engine/1.10.2/junit-platform-engine-1.10.2.jar \
com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 \
tripleo.elijah.TestBasic,testBasic_fact1_002
