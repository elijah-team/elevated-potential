package tripleo.elijah.stages.gen_c;

import com.google.common.base.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.*;

import java.util.*;

public class ZoneITE__1 implements ZoneITE {
	private final IdentTableEntry identTableEntry;
	private final BaseEvaFunction gf;
	private final GenerateC       generateC;

	public ZoneITE__1(final IdentTableEntry aIdentTableEntry, final BaseEvaFunction aGf, final GenerateC aGenerateC) {
		identTableEntry = aIdentTableEntry;
		gf              = aGf;
		generateC       = aGenerateC;
	}

	@Override
	public String getRealTargetName() {
		//return null;
		throw new UnintendedUseException();
	}

	@Override
	public boolean isPropertyStatement() {
		var resolvedElement = identTableEntry.getResolvedElement();
		return resolvedElement instanceof PropertyStatement;
	}

	@Override
	public boolean isConstructorDef() {
		var resolvedElement = identTableEntry.getResolvedElement();
		return resolvedElement instanceof ConstructorDef;
	}

	@Override
	public boolean isVariableStatement() {
		var resolvedElement = identTableEntry.getResolvedElement();
		return resolvedElement instanceof VariableStatement;
	}

	@Override
	public ZoneVariableStatement getVariableStatement() {
		Preconditions.checkArgument(isVariableStatement());
		return new ZoneVariableStatement((VariableStatement) identTableEntry.getResolvedElement());
	}

	@Override
	@NotNull
	public String getRealTargetName2(Generate_Code_For_Method.AOG aog, String value) {
		int         state = 0;
		final int[] code  = {-1};

		LinkedList<String> ls = new LinkedList<String>();
		// TODO in Deduce set property lookupType to denote what type of lookup it is: MEMBER, LOCAL, or CLOSURE
		InstructionArgument backlink = identTableEntry.getBacklink();
		final String        text     = identTableEntry.getIdent().getText();
		if (backlink == null) {
			final ZoneITE zi = this.generateC._zone.get(identTableEntry, gf);
			if (zi.isVariableStatement()) {
				final ZoneVariableStatement zvs    = zi.getVariableStatement();
				final OS_Element            parent = zvs.getContainerParent();

				if (parent != gf.getFD()) {
					// FIXME 10/19 find out what this here means and implement it vv
					// we want identTableEntry.resolved which will be a EvaMember
					// which will have a container which will be either be a function,
					// statement (semantic block, loop, match, etc) or a EvaContainerNC
					// FIXME 10/19 ^^ ^^
					int     y  = 2;
					EvaNode er = identTableEntry.externalRef(); // FIXME move to onExternalRef
					if (er instanceof final @NotNull EvaContainerNC nc) {
						if (nc instanceof EvaNamespace ns) {
							//if (ns.isInstance()) {}
							state = 1;
							ESwitch.flep(ns, new DoneCallback<DeducedEvaNamespace>() {
								@Override
								public void onDone(final DeducedEvaNamespace result) {
									code[0] = result.getCode();
									;
								}
							});
						} else {
							throw new AssertionError();
						}
					}
				}
			}
			switch (state) {
			case 0 -> ls.add(Emit.emit("/*912*/") + "vsc->vm" + text); // TODO blindly adding "vm" might not always work, also put in loop
			case 1 -> ls.add(Emit.emit("/*845*/") + String.format("zNZ%d_instance->vm%s", code[0], text));
			default -> throw new IllegalStateException("Can't be here");
			}
		} else {
			ls.add(Emit.emit("/*872*/") + "vm" + text); // TODO blindly adding "vm" might not always work, also put in loop
		}

		while (backlink != null) {
			if (backlink instanceof final @NotNull IntegerIA integerIA) {
				String realTargetName = this.generateC.getRealTargetName(gf, integerIA, Generate_Code_For_Method.AOG.ASSIGN);
				ls.addFirst(Emit.emit("/*892*/") + realTargetName);
				backlink = null;
			} else if (backlink instanceof final @NotNull IdentIA identIA) {
				int             identIAIndex        = identIA.getIndex();
				IdentTableEntry identTableEntry1    = gf.getIdentTableEntry(identIAIndex);
				String          identTableEntryName = identTableEntry1.getIdent().getText();
				ls.addFirst(Emit.emit("/*885*/") + "vm" + identTableEntryName); // TODO blindly adding "vm" might not always be right
				backlink = identTableEntry1.getBacklink();
			} else
				throw new IllegalStateException("Invalid InstructionArgument for backlink");
		}

		final CReference reference = new CReference(this.generateC._repo, this.generateC.ce);

		final int index = gf.findIdentTableIndex(identTableEntry);
		assert index != -1;
		final IdentIA target = new IdentIA(index, gf);

		reference.getIdentIAPath(target, aog, value);
		final String path = reference.build();
		this.generateC.LOG.info("932 " + path);
		final String s = Helpers.String_join("->", ls);
		this.generateC.LOG.info("933 " + s);

		final ZoneITE zi = this.generateC._zone.get(identTableEntry, gf);

		if (zi.isConstructorDef() || zi.isPropertyStatement() /* || value != null*/) {
			return path;
		} else {
			return s;
		}
	}

	@Override
	public Garish_TargetName getRealTargetName3(final String aAssignmentValue) {
		return new Garish_TargetName() {
			@Override public String forAOG(final Generate_Code_For_Method.AOG aAOG) {
				return getRealTargetName2(aAOG, aAssignmentValue);
			}

			@Override
			public IReasonedString reasonedForAOG(final Generate_Code_For_Method.AOG aAOG) {
				return new IReasonedString() {
					@Override
					public String text() {
						return forAOG(aAOG);
					}

					@Override
					public String reason() {
						return "<<151: ??>>";
					}
				};
			}
		};
	}
}
