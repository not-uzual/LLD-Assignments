package ParkingLot;

import java.time.LocalDateTime;

public class Ticket {
    private final int ticketId;
    private final Vehicle vehicle;
    private final int allocatedSlotNumber;
    private final SizeType allocatedSizeType;
    private final LocalDateTime entryTime;
    private final int entryGateId;
    private static int ticketCounter = 0;

    public Ticket(Vehicle vehicle, int allocatedSlotNumber, SizeType allocatedSizeType,
                  LocalDateTime entryTime, int entryGateId) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle details are required.");
        }
        if (allocatedSlotNumber <= 0) {
            throw new IllegalArgumentException("Allocated slot number must be positive.");
        }
        if (allocatedSizeType == null) {
            throw new IllegalArgumentException("Allocated size type is required.");
        }
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time is required.");
        }
        if (entryGateId <= 0) {
            throw new IllegalArgumentException("Entry gate id must be positive.");
        }

        this.ticketId = nextTicketId();
        this.vehicle = vehicle;
        this.allocatedSlotNumber = allocatedSlotNumber;
        this.allocatedSizeType = allocatedSizeType;
        this.entryTime = entryTime;
        this.entryGateId = entryGateId;
    }

    private static synchronized int nextTicketId() {
        return ++ticketCounter;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getAllocatedSlotNumber() {
        return allocatedSlotNumber;
    }

    public SizeType getAllocatedSizeType() {
        return allocatedSizeType;
    }

    public int getEntryGateId() {
        return entryGateId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}

