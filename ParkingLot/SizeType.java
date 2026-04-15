package ParkingLot;

public enum SizeType {
    SMALL,
    MEDIUM,
    LARGE;

    public int rank() {
        return switch (this) {
            case SMALL -> 1;
            case MEDIUM -> 2;
            case LARGE -> 3;
        };
    }
}
