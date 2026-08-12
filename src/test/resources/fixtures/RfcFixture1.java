package fixtures;

public class RFCAnalysisTest1 {
	private int a = 0;

	private void privateVoid(int x) {
		this.a = privateInt(x + a);
	}

	private int privateInt(int n) {
		return n;
	}

	private String privateString() {
		return "";
	}
}
