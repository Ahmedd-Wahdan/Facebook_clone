public abstract class BlueCar implements Car {
    public void speak() {
        System.out.println("hi");
    }
    BlueCar(){
        System.out.println("hey i am super constructor BlueCar");
    }
    int info (){
        System.out.println("int");
        return 0;
    }
}
