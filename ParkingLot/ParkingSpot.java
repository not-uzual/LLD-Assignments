package LLD_Assignment.Design_Assignment.ParkingLot;

public class ParkingSpot {
    private final int slotNumber;
    private final int levelNumber;
    private final int position;
    private final SizeType sizeType;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSpot(int slotNumber, int levelNumber, int position, SizeType sizeType) {
        if (slotNumber <= 0) {
            throw new IllegalArgumentException("Slot number must be positive.");
        }
        if (levelNumber < 0) {
            throw new IllegalArgumentException("Level number cannot be negative.");
        }
        if (position < 0) {
            throw new IllegalArgumentException("Slot position cannot be negative.");
        }
        this.slotNumber = slotNumber;
        this.levelNumber = levelNumber;
        this.position = position;
        this.sizeType = sizeType;
        this.occupied = false;
        this.vehicle = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getPosition() {
        return position;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean canPark(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        return !occupied && this.sizeType.rank() >= vehicle.getSizeType().rank();
    }

    public void assignVehicle(Vehicle vehicle) {
        if (!canPark(vehicle)) {
            throw new IllegalStateException("Vehicle cannot be parked in this spot.");
        }
        occupied = true;
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        this.occupied = false;
        this.vehicle = null;
    }

    public int distanceFromGate(Gate gate) {
        int levelDistance = Math.abs(this.levelNumber - gate.getLevelNumber()) * 1000;
        int positionDistance = Math.abs(this.position - gate.getPosition());
        return levelDistance + positionDistance;
    }
}
