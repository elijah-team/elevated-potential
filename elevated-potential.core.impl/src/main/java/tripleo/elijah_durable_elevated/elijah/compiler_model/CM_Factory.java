package tripleo.elijah_durable_elevated.elijah.compiler_model;

import tripleo.elijah.compiler_model.CM_Filename;

import java.io.File;

public class CM_Factory {
	private CM_Factory() {}

	 public static CM_Filename Filename__fromFileToString(File file) {
		return new CM_Filename() {
			@Override
			public String getString() {
				return file.toString();
			}
		};
	}

	 public static CM_Filename Filename__fromParams(String aF) {
		return new CM_Filename() {
			@Override
			public String getString() {
				return aF;
			}
		};
	}

	 public static CM_Filename Filename__of(String aFn) {
		return new CM_Filename() {
			@Override
			public String getString() {
				return aFn;
			}
		};
	}

	//static CM_Filename Filename__fromInputRequestCanonicalFileToString(CompFactory.InputRequest aInputRequest) {
	//	var r = fromInputRequestCanonicalFileToStringOperation(aInputRequest);
	//	if (r.mode() == Mode.SUCCESS) {
	//		return r.success();
	//	}
	//	throw new RuntimeException(r.failure());
	//}

	//static Operation<CM_Filename> Filename__fromInputRequestCanonicalFileToStringOperation(CompFactory.InputRequest aInputRequest) {
	//	var file = aInputRequest.file();
	//	var f = aInputRequest.file().toString();
	//	var do_out = aInputRequest.do_out();
	//
	//	final String absolutePath1;
	//	try {
	//		absolutePath1 = file.getCanonicalFile().toString();
	//	} catch (IOException aE) {
	//		return Operation.failure(aE);
	//	}
	//
	//	var r = new CM_Filename() {
	//		@Override
	//		public String getString() {
	//			return absolutePath1;
	//		}
	//	};
	//
	//	return Operation.success(r);
	//}
}
