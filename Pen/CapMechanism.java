package LLD_Assignment.Design_Assignment.Pen;

public class CapMechanism implements OperatingMechanism {
    private boolean capRemoved;

    @Override
    public void start() {
        capRemoved = true;
    }

    @Override
    public void close() {
        capRemoved = false;
    }

    @Override
    public boolean isReadyToWrite() {
        return capRemoved;
    }

    @Override
    public String getName() {
        return "Cap";
    }
}
