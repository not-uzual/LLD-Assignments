package ParkingLot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotSystem {
    private final List<Level> levels;
    private final Map<Integer, Gate> gateIndex;
    private final Map<Integer, ParkingSpot> activeAllocations;
    private final Map<SizeType, Double> hourlyRateBySizeType;

    public ParkingLotSystem(Map<SizeType, Double> hourlyRateBySizeType) {
        if (hourlyRateBySizeType == null) {
            throw new IllegalArgumentException("Hourly rate map is required.");
        }
        for (SizeType type : SizeType.values()) {
            Double rate = hourlyRateBySizeType.get(type);
            if (rate == null || rate <= 0) {
                throw new IllegalArgumentException("Hourly rate must be set and positive for " + type);
            }
        }

        this.levels = new ArrayList<>();
        this.gateIndex = new HashMap<>();
        this.activeAllocations = new HashMap<>();
        this.hourlyRateBySizeType = new EnumMap<>(SizeType.class);
        this.hourlyRateBySizeType.putAll(hourlyRateBySizeType);
    }

    public void addLevel(Level level) {
        if (level == null) {
            throw new IllegalArgumentException("Level is required.");
        }
        this.levels.add(level);

        for (Gate gate : level.getGates()) {
            if (gateIndex.containsKey(gate.getId())) {
                throw new IllegalStateException("Duplicate gate id: " + gate.getId());
            }
            gateIndex.put(gate.getId(), gate);
        }
    }

    public Ticket park(Vehicle vehicleDetails, LocalDateTime entryTime, SizeType requestedSizeType, int entryGateID) {
        if (vehicleDetails == null) {
            throw new IllegalArgumentException("Vehicle details are required.");
        }
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time is required.");
        }
        if (requestedSizeType == null) {
            throw new IllegalArgumentException("Requested size type is required.");
        }

        Gate entryGate = gateIndex.get(entryGateID);
        if (entryGate == null) {
            throw new IllegalArgumentException("Invalid entry gate id: " + entryGateID);
        }

        if (requestedSizeType.rank() < vehicleDetails.getSizeType().rank()) {
            throw new IllegalArgumentException("Requested size type is smaller than vehicle size.");
        }

        ParkingSpot bestSpot = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Level level : levels) {
            for (ParkingSpot spot : level.getSpots()) {
                if (spot.isOccupied()) {
                    continue;
                }
                if (spot.getSizeType().rank() < vehicleDetails.getSizeType().rank()) {
                    continue;
                }
                if (spot.getSizeType().rank() < requestedSizeType.rank()) {
                    continue;
                }

                int distance = spot.distanceFromGate(entryGate);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestSpot = spot;
                }
            }
        }

        if (bestSpot == null) {
            throw new IllegalStateException("No compatible slot available.");
        }

        bestSpot.assignVehicle(vehicleDetails);
        Ticket ticket = new Ticket(
                vehicleDetails,
                bestSpot.getSlotNumber(),
                bestSpot.getSizeType(),
                entryTime,
                entryGateID
        );
        activeAllocations.put(ticket.getTicketId(), bestSpot);
        return ticket;
    }

    public Map<SizeType, Integer> status() {
        Map<SizeType, Integer> availability = new EnumMap<>(SizeType.class);
        availability.put(SizeType.SMALL, 0);
        availability.put(SizeType.MEDIUM, 0);
        availability.put(SizeType.LARGE, 0);

        for (Level level : levels) {
            for (ParkingSpot spot : level.getSpots()) {
                if (!spot.isOccupied()) {
                    SizeType type = spot.getSizeType();
                    availability.put(type, availability.get(type) + 1);
                }
            }
        }

        return availability;
    }

    public double exit(Ticket parkingTicket, LocalDateTime exitTime) {
        if (parkingTicket == null) {
            throw new IllegalArgumentException("Parking ticket is required.");
        }
        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time is required.");
        }
        if (exitTime.isBefore(parkingTicket.getEntryTime())) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }

        ParkingSpot occupiedSpot = activeAllocations.remove(parkingTicket.getTicketId());
        if (occupiedSpot == null) {
            throw new IllegalArgumentException("Ticket is invalid or already closed.");
        }

        long totalMinutes = Duration.between(parkingTicket.getEntryTime(), exitTime).toMinutes();
        long billedHours = (long) Math.ceil(totalMinutes / 60.0);
        if (billedHours == 0) {
            billedHours = 1;
        }

        double hourlyRate = hourlyRateBySizeType.get(parkingTicket.getAllocatedSizeType());
        double totalAmount = billedHours * hourlyRate;
        occupiedSpot.removeVehicle();
        return totalAmount;
    }
}
