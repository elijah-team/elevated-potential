package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.*;

public interface ZoneITE extends ZoneTargettedMember {

	boolean isPropertyStatement();

	boolean isConstructorDef();

	boolean isVariableStatement();

	ZoneVariableStatement getVariableStatement();

	@NotNull String getRealTargetName2(Generate_Code_For_Method.AOG aog, String value);

}
