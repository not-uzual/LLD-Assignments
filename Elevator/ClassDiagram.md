# Elevator System - Class Diagram

```mermaid
classDiagram
    class ElevatorController {
        - elevators: List~Elevator~
        - scheduler: Scheduler
        - MIN_FLOOR: int
        - MAX_FLOOR: int
        - NUM_ELEVATORS: int
        + ElevatorController()
        + requestElevator(floor: int, direction: Direction)
        - validateFloor(floor: int)
        - findBestElevator(floor: int, direction: Direction) Elevator
        + step()
    }

    class Elevator {
        - id: int
        - currentFloor: int
        - currentDirection: Direction
        - requests: List~Request~
        - scheduler: Scheduler
        - minFloor: int
        - maxFloor: int
        + Elevator(id: int, scheduler: Scheduler, minFloor: int, maxFloor: int)
        + addRequest(request: Request)
        + addInternalRequest(floor: int)
        + step()
        + getFloor() int
        + getDirection() Direction
        + getId() int
        + getRequestCount() int
    }

    class Scheduler {
        - algo: Algorithm
        + Scheduler(algo: Algorithm)
        + schedule(requests: List~Request~) List~Request~
    }

    class Request {
        - floor: int
        - type: RequestType
        + Request(floor: int, type: RequestType)
        + getFloor() int
        + getType() RequestType
    }

    class Algorithm {
        <<interface>>
        + sort(requests: List~Request~) List~Request~
    }

    class FIFOAlgorithm {
        + sort(requests: List~Request~) List~Request~
    }

    class Direction {
        <<enumeration>>
        UP
        DOWN
        IDLE
    }

    class RequestType {
        <<enumeration>>
        INTERNAL
        EXTERNAL
    }

    ElevatorController --> "1" Scheduler
    ElevatorController --> "3..*" Elevator
    Elevator --> "0..*" Request
    Elevator --> "1" Scheduler
    Elevator --> Direction
    Scheduler --> Algorithm
    FIFOAlgorithm ..|> Algorithm
    Request --> RequestType
    Request --> Direction
```

## Design Overview

**ElevatorController**: Manages the fleet of elevators and dispatches requests to the best available elevator based on distance, direction, and current state.

**Elevator**: Represents a single elevator that processes requests, moves between floors, and updates its direction based on scheduled requests.

**Scheduler**: Uses an algorithm to sort and optimize the order of floor requests for efficient elevator movement.

**Algorithm Interface**: Allows for different scheduling strategies (currently FIFO, but extensible to SCAN, SSTF, etc.).

**Request**: Represents a request to visit a floor, categorized as either INTERNAL (inside elevator) or EXTERNAL (outside elevator).

**Enums**: 
- **Direction**: UP, DOWN, IDLE states
- **RequestType**: INTERNAL or EXTERNAL request types
