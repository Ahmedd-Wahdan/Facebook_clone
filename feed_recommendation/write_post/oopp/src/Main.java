import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface A{
   static int getdata(){
       return 4;
   }

}
class B implements A{


    int z;


    //public int getdata(){
    //    return 0;
    //}

}




public class Main {

    public static void main(String[] argv){


        B b = new B();
        System.out.println(A.getdata());
    }
}