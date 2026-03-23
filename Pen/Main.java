package LLD_Assignment.Design_Assignment.Pen;

public class Main {
    public static void main(String[] args) {
        Pen pen = new Pen(PenType.GEL, new ClickMechanism(), new Refill("Blue", 40));

        pen.start();
        System.out.println(pen.write("Hello from Ujjwal"));
        pen.close();

        pen.refill(new Refill("Black", 60));
        pen.start();
        System.out.println(pen.write("Color changed after refill"));
        pen.close();

        // System.out.println(pen);
    }
}
