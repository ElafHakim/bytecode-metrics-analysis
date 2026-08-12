package fixtures;

public class MCCCAnalysisTest {
	
    public void method1() {
    	int a = 10;
    	if (a>5) {
    		  System.out.println("ich bin größer als 5");
        }
    	else {
    		 System.out.println("ich bin noch klein");
    	}
    }
    
    public void method2() {
        int i=2;
        switch(i){
        case 0:
            System.out.println("i ist null");
            break;
        case 1:
            System.out.println("i ist eins");
            break;
        case 2:
            System.out.println("i ist zwei");
            break;
        case 3:
            System.out.println("i ist drei");
            break;
        default:
            System.out.println("i liegt nicht zwischen null und drei");
            break;
        }
    }
	
    public void method3 (int a) {
        Boolean b=false;
        Boolean c=true;
    	while(a<10){
            if (a>5) {
    		  System.out.println("ich bin größer als 5");
        }
    	    else {
    		 System.out.println("ich bin noch klein");
             method1();
    	}
        a++;
        }

        if(b){
            if(c){
            System.out.println("");
        }
        else{
            System.out.println("");
        }
        } 
    }
}
