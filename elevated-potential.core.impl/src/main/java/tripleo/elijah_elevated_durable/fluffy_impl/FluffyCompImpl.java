package tripleo.elijah_elevated_durable.fluffy_impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah.fluffy.FluffyComp;
import tripleo.elijah.fluffy.FluffyModule;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevated_durable.lang_impl.OS_ModuleImpl;
import tripleo.elijah_elevateder.entrypoints.MainClassEntryPoint;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.*;
import java.util.stream.Collectors;

public class FluffyCompImpl implements FluffyComp {

	private final EDL_Compilation              _comp;
	private final Map<OS_Module, FluffyModule> fluffyModuleMap = new HashMap<>();
	FluffyCompImplInjector __inj = new FluffyCompImplInjector();
	private List<Eventual<?>> _eventuals = new ArrayList<>();

	public FluffyCompImpl(final EDL_Compilation aComp) {
		_comp = aComp;
	}

	public static boolean isMainClassEntryPoint(@NotNull final OS_NamedElement input) {
		// TODO 08/27 Use understanding/~ processor for this
		final FunctionDef fd = (FunctionDef) input;
		return MainClassEntryPoint.is_main_function_with_no_args(fd);
	}

	@Override
	public void find_multiple_items(final @NotNull OS_Module aModule, OS_ModuleImpl.Complaint aComplaint) {
		final Multimap<String, ModuleItem> items_map = ArrayListMultimap.create(aModule.getItems().size(), 1);

		aModule.getItems().stream()
				.filter(Objects::nonNull)
				.filter(x -> !(x instanceof ImportStatement))
				.forEach(item -> {
					// README likely for member functions.
					// README Also note elijah has single namespace
					items_map.put(item.name(), item);
				});

		for (final String key : items_map.keys()) {
			boolean warn = false;

			final Collection<ModuleItem> moduleItems = items_map.get(key);
			if (moduleItems.size() == 1)
				continue;

			final Collection<ElObjectType> t = moduleItems.stream()
					.map(DecideElObjectType::getElObjectType)
					.collect(Collectors.toList());

			final Set<ElObjectType> st = new HashSet<ElObjectType>(t);
			if (st.size() > 1)
				warn = true;
			if (moduleItems.size() > 1) {
				if (!(moduleItems.iterator().next() instanceof NamespaceStatement) || st.size() != 1) {
					warn = true;
				}
			}

			//
			//
			//

			if (warn) {
				aComplaint.reportWarning(aModule, key);
			}
		}

	}

	@Override
	public FluffyModule module(final OS_Module aModule) {
		if (fluffyModuleMap.containsKey(aModule)) {
			return fluffyModuleMap.get(aModule);
		}

		final FluffyModuleImpl fluffyModule = _inj().new_FluffyModuleImpl(aModule, _comp);
		fluffyModuleMap.put(aModule, fluffyModule);
		return fluffyModule;
	}

	private FluffyCompImplInjector _inj() {
		return __inj;
	}

	@Override
	public void checkFinishEventuals() {
		int y = 0;
		for (Eventual<?> eventual : _eventuals) {
			if (eventual.isResolved()) {
				SimplePrintLoggerToRemoveSoon.println_err_4("[FluffyCompImpl::checkEventual] ok for " + eventual.description());
			} else {
				SimplePrintLoggerToRemoveSoon.println_err_4("[FluffyCompImpl::checkEventual] failed for " + eventual.description());
			}
		}
	}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}

	static class FluffyCompImplInjector {
		public FluffyModuleImpl new_FluffyModuleImpl(final OS_Module aModule, final EDL_Compilation aComp) {
			return new FluffyModuleImpl(aModule, aComp);
		}
	}
}
