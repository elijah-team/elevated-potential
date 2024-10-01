package tripleo.elijah_elevateder.stages.garish;

import org.jetbrains.annotations.*;
import tripleo.elijah.g.GGarishClass;
import tripleo.elijah.lang.i.*;


import tripleo.elijah_elevateder.lang.types.OS_UnknownType;
import tripleo.elijah_elevateder.lang.types.OS_UserClassType;
import tripleo.elijah_elevateder.stages.deduce.ClassInvocation;
import tripleo.elijah_elevateder.stages.gen_c.CClassDecl;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.world.i.LivingClass;
import tripleo.elijah_fluffy.util.BufferTabbedOutputStream;

import java.util.*;

public class GarishClass implements GGarishClass {
	private final LivingClass _lc;

	@Contract(pure = true)
	public GarishClass(final LivingClass aLivingClass) {
		_lc = aLivingClass;
		// _lc.setGarish(this);
	}

	public @NotNull String finalizedGenericPrintable(final @NotNull EvaClass evaClass) {
		final ClassStatement                 klass = evaClass.getKlass();
		final ClassInvocation.CI_GenericPart x     = evaClass.ci.genericPart();

		final String        name = klass.getName();
		final StringBuilder sb   = new StringBuilder();
		sb.append(name);

		sb.append('[');

		if (x != null) {
			for (Map.Entry<TypeName, OS_Type> entry : x.entrySet()) {
				final OS_Type y = entry.getValue();

				if (y instanceof OS_UnknownType unk) {
				} else {
					if (y instanceof OS_UserClassType uct) {
						sb.append("%s,".formatted(uct.getClassOf().getName()));
					}
				}
			}
		}

		sb.append(']');

		return sb.toString();
	}

	public void garish(final GenerateC aGenerateC, final GenerateResult gr, final @NotNull GenerateResultSink aResultSink) {
		getLiving().generateWith(aResultSink, this, gr, aGenerateC);
	}

	public @NotNull BufferTabbedOutputStream getImplBuffer(final @NotNull EvaClass x, final @NotNull CClassDecl decl, final String class_name, final int class_code) {
		final BufferTabbedOutputStream tos = new BufferTabbedOutputStream();

		// TODO remove this block when constructors are added in dependent functions,
		// etc in Deduce

		// TODO what about named constructors and ctor$0 and "the debug stack"
		tos.put_string_ln(String.format("%sZC%d() {", class_name, class_code));
		tos.incr_tabs();
		tos.put_string_ln(String.format("%sR = GC_malloc(sizeof(%s));", class_name, class_name));
		tos.put_string_ln(String.format("R->_tag = %d;", class_code));
		if (decl.prim) {
			// TODO consider NULL, and floats and longs, etc
			if (!decl.prim_decl.equals("bool")) tos.put_string_ln("R->vsv = 0;");
			else tos.put_string_ln("R->vsv = false;");
		} else {
			for (EvaClass.VarTableEntry o : x.varTable) {
//					final String typeName = getTypeNameForVarTableEntry(o);
				// TODO this should be the result of getDefaultValue for each type
				tos.put_string_ln(String.format("R->vm%s = 0;", o.nameToken));
			}
		}
		tos.put_string_ln("return R;");
		tos.dec_tabs();
		tos.put_string_ln(String.format("} // class %s%s", decl.prim ? "box " : "", x.getName()));
		tos.put_string_ln("");

		tos.flush();

		tos.close();
		return tos;
	}

	public @NotNull BufferTabbedOutputStream getImplBuffer(final @NotNull GenerateC aGenerateC) {
		final EvaClass evaClass = (EvaClass) getLiving().evaNode();

		final CClassDecl decl = new CClassDecl(evaClass);
		decl.evaluatePrimitive();

		final String class_name = aGenerateC.getTypeName(evaClass);
		final int    class_code = getLiving().getCode();

		return getImplBuffer(evaClass, decl, class_name, class_code);
	}

	public @NotNull BufferTabbedOutputStream getHeaderBuffer(final @NotNull GenerateC aGenerateC) {
		final EvaClass evaClass = (EvaClass) getLiving().evaNode();

		final CClassDecl decl = new CClassDecl(evaClass);
		decl.evaluatePrimitive();

		final String class_name = aGenerateC.getTypeName(evaClass);
		final int    class_code = getLiving().getCode();

		return getHeaderBuffer(aGenerateC, evaClass, decl, class_name);
	}

	public @NotNull BufferTabbedOutputStream getHeaderBuffer(final @NotNull GenerateC aGenerateC, final @NotNull EvaClass x, final @NotNull CClassDecl decl, final String class_name) {
		final BufferTabbedOutputStream tosHdr = new BufferTabbedOutputStream();

		tosHdr.put_string_ln("typedef struct {");
		tosHdr.incr_tabs();
		tosHdr.put_string_ln("int _tag;");
		if (!decl.prim) {
			for (EvaClass.VarTableEntry o : x.varTable) {
				final String typeName = aGenerateC.getTypeNameGNCForVarTableEntry(o);
				tosHdr.put_string_ln(String.format("%s vm%s;", typeName, o.nameToken));
			}
		} else {
			tosHdr.put_string_ln(String.format("%s vsv;", decl.prim_decl));
		}

		tosHdr.dec_tabs();
		tosHdr.put_string_ln("");

		var xx = finalizedGenericPrintable(x);
		tosHdr.put_string_ln("} %s;  // class %s%s".formatted(class_name, decl.prim ? "box " : "", xx));
		tosHdr.put_string_ln("");

		tosHdr.flush();
		tosHdr.close();
		return tosHdr;
	}

	public LivingClass getLiving() {
		return _lc;
	}
}
