package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.wrap.File;

import java.util.HashMap;
import java.util.Map;

public class Default__Elevated_CM_Factory implements Elevated_CM_Factory {
	private final Compilation                        compilation;
	private final Map<OS_Module, Elevated_CM_Module> modules      = new HashMap<>();
	private final Map<File, CM_ResourceDir>          resourceDirs = new HashMap<>();
	private final Map<File, CM_Resource>             resources    = new HashMap<>();

	public Default__Elevated_CM_Factory(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public Elevated_CM_Module singleModule(final OS_Module aModule) {
		if (modules.containsKey(aModule)) {
			return modules.get(aModule);
		}

		final Elevated_CM_Module result = new Elevated_CM_ModuleImpl(aModule, compilation);
		modules.put(aModule, result);
		return result;
	}

	@Override
	public CM_ResourceDir resourceDir(final File aInstructionDir) {
		if (resourceDirs.containsKey(aInstructionDir)) {
			return resourceDirs.get(aInstructionDir);
		}

		final CM_ResourceDir result = new CM_ResourceDirImpl(aInstructionDir, compilation);
		resourceDirs.put(aInstructionDir, result);
		return result;
	}

	@Override
	public CM_Resource resourceFor(final CM_ResourceDir aParentDir, final File aFile) {
		final File f = new File(aParentDir.getFile(), aFile.getName());

		if (resources.containsKey(f)) {
			return resources.get(f);
		}
		if (resourceDirs.containsKey(f)) {
			return resourceDirs.get(f);
		}

		final CM_Resource result = new CM_ResourceImpl(f, compilation);
		if (result.getFile().isDirectory()) {
			final CM_ResourceDirImpl result2 = new CM_ResourceDirImpl(f, compilation);
			result2.setParentResource(aParentDir);
			resourceDirs.put(f, result2);
		} else {
			result.setParentResource(aParentDir);
			resources.put(f, result);
		}
		return result;
	}

	private CM_Resource __tryResource(final File f) {
		if (resources.containsKey(f)) {
			return resources.get(f);
		}
		if (resourceDirs.containsKey(f)) {
			return resourceDirs.get(f);
		}

		final CM_Resource result = new CM_ResourceImpl(f, compilation);
		if (result.getFile().isDirectory()) {
			final CM_ResourceDir result2 = new CM_ResourceDirImpl(f, compilation);
			resourceDirs.put(f, result2);
		} else {
			resources.put(f, result);
		}
		return result;
	}

	@Override
	public CM_Resource resourceFor(final String aFilename) {
		final File f = new File(aFilename);

		return __tryResource(f);
	}
}
