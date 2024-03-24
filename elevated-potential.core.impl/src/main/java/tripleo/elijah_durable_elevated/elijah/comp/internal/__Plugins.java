package tripleo.elijah_durable_elevated.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.comp.*;

public abstract class __Plugins {
	public static class WriteMakefilePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new WriteMakefilePipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "WriteMakefilePipeline";
		}
	}

	public static class WriteMesonPipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new WriteMesonPipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "WriteMesonPipeline";
		}
	}

	public static class WriteOutputTreePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new WriteOutputTreePipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "WriteOutputTreePipeline";
		}
	}

	public static class WritePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new WritePipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "WritePipeline";
		}
	}

	public static class DeducePipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new DeducePipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "DeducePipeline";
		}
	}

	public static class EvaPipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new EvaPipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "EvaPipeline";
		}
	}

	public static class LawabidingcitizenPipelinePlugin implements PipelinePlugin {
		@Override
		public @NotNull PipelineMember instance(final @NotNull GPipelineAccess ab0) {
			return new LawabidingcitizenPipeline(ab0);
		}

		@Override
		public @NotNull String name() {
			return "LawabidingcitizenPipeline";
		}
	}
}
