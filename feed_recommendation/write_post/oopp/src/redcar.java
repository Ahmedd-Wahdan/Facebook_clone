public class redcar extends BlueCar{

redcar(){
    System.out.println("redcar constructor");
}
    @Override
    public void add(int a) {
        System.out.println("integer.add");
    }
    public void add(double a) {
        System.out.println("double.add");
    }
}
