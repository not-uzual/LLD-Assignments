package Elevator;

public class Main {
    public static void main(String[] args) {

        ElevatorController controller = new ElevatorController();

        // Request elevators from different floors
        controller.requestElevator(5, Direction.UP);
        controller.requestElevator(2, Direction.DOWN);
        controller.requestElevator(8, Direction.UP);

        // Simulate elevator movement
        for (int i = 0; i < 20; i++) {
            System.out.println("Step " + i);
            controller.step();
        }
    }
}
