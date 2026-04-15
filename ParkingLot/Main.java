package ParkingLot;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<SizeType, Double> rates = new EnumMap<>(SizeType.class);
        rates.put(SizeType.SMALL, 10.0);
        rates.put(SizeType.MEDIUM, 20.0);
        rates.put(SizeType.LARGE, 50.0);

        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(rates);

        Level level0 = new Level(0);
        level0.addGate(new Gate(1, 0, 0));
        level0.addGate(new Gate(2, 0, 30));
        level0.addSpot(new ParkingSpot(101, 0, 2, SizeType.SMALL));
        level0.addSpot(new ParkingSpot(102, 0, 8, SizeType.MEDIUM));
        level0.addSpot(new ParkingSpot(103, 0, 20, SizeType.LARGE));

        Level level1 = new Level(1);
        level1.addGate(new Gate(3, 1, 0));
        level1.addGate(new Gate(4, 1, 40));
        level1.addSpot(new ParkingSpot(201, 1, 3, SizeType.MEDIUM));
        level1.addSpot(new ParkingSpot(202, 1, 10, SizeType.LARGE));

        parkingLotSystem.addLevel(level0);
        parkingLotSystem.addLevel(level1);

        Vehicle bike = new Bike("KA01-BIKE-1");
        LocalDateTime bikeEntryTime = LocalDateTime.now().minusMinutes(130);
        Ticket bikeTicket = parkingLotSystem.park(bike, bikeEntryTime, SizeType.SMALL, 1);

        Vehicle car = new Car("KA01-CAR-1");
        LocalDateTime carEntryTime = LocalDateTime.now().minusMinutes(50);
        Ticket carTicket = parkingLotSystem.park(car, carEntryTime, SizeType.MEDIUM, 2);

        Vehicle bus = new Bus("KA01-BUS-1");
        LocalDateTime busEntryTime = LocalDateTime.now().minusMinutes(200);
        Ticket busTicket = parkingLotSystem.park(bus, busEntryTime, SizeType.LARGE, 3);

        System.out.println(parkingLotSystem.status());
        System.out.println(parkingLotSystem.exit(bikeTicket, LocalDateTime.now()));
        System.out.println(parkingLotSystem.exit(carTicket, LocalDateTime.now()));
        System.out.println(parkingLotSystem.exit(busTicket, LocalDateTime.now()));
    }
}
