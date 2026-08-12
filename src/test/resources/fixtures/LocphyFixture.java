package fixtures;

public class LocphyFixture {

    public static void main(String[] args) {
        method1();
        method2();
        System.out.println(method3());
    }

    public static void method1() {
        // comment
        for (int i = 0; i < 5; i++) {
            int a = 1;
            int b = 1;
            int sum = 0;
            sum = a + b;
            System.out.println(sum);
        }
        //comment
    }

    public static void method2() {
        for (int i = 0; i < 5; i++) {

            // comment
            System.out.println(i);
        }
    }

    public static String method3() {
        // comment
        return "Hello";
    }

}
