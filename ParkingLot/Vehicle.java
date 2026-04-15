package ParkingLot;

public class Vehicle {
    private final String licenseNumber;
    private final SizeType sizeType;

    public Vehicle(String licenseNumber, SizeType sizeType) {
        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("License number is required.");
        }
        if (sizeType == null) {
            throw new IllegalArgumentException("Vehicle size type is required.");
        }
        this.licenseNumber = licenseNumber;
        this.sizeType = sizeType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public SizeType getSizeType() {
        return sizeType;
    }
}
