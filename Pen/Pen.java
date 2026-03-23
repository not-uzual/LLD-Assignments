package LLD_Assignment.Design_Assignment.Pen;

import java.util.Objects;

public class Pen {
    private final PenType type;
    private final OperatingMechanism mechanism;
    private Refill refill;

    public Pen(PenType type, OperatingMechanism mechanism, Refill refill) {
        this.type = Objects.requireNonNull(type, "Pen type is required.");
        this.mechanism = Objects.requireNonNull(mechanism, "Mechanism is required.");
        this.refill = Objects.requireNonNull(refill, "Refill is required.");
    }

    public PenType getType() {
        return type;
    }

    public String getCurrentColor() {
        return refill.getColor();
    }

    public void start() {
        mechanism.start();
    }

    public String write(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content to write cannot be blank.");
        }
        if (!mechanism.isReadyToWrite()) {
            throw new IllegalStateException("Pen is closed. Call start() before write().");
        }

        int inkNeeded = content.length();
        if (!refill.canWrite(inkNeeded)) {
            throw new IllegalStateException("Not enough ink. Please refill.");
        }

        refill.consume(inkNeeded);
        return "[" + refill.getColor() + "] " + content;
    }

    public void close() {
        mechanism.close();
    }

    public void refill(Refill newRefill) {
        this.refill = Objects.requireNonNull(newRefill, "New refill is required.");
    }

    @Override
    public String toString() {
        return "Pen{" +
                "type=" + type +
                ", mechanism='" + mechanism.getName() + '\'' +
                ", refill=" + refill +
                '}';
    }
}
