package fixtures;

public class RFCAnalysisTest2 {
	private int n;

	public static void main(String[] args) {
		System.out.println(publicStaticInt());
	}

	public static int publicStaticInt() {
		return privateStaticInt();
	}

	private static int privateStaticInt() {
		return 5;
	}

	public String publicString() {
		return privateString();
	}

	private String privateString() {
		return "";
	}

	private void privateVoid() {
		n = 0;
	}

}
