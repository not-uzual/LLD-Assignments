package Pen;

public class ClickMechanism implements OperatingMechanism {
    private boolean tipExposed;

    @Override
    public void start() {
        tipExposed = true;
    }

    @Override
    public void close() {
        tipExposed = false;
    }

    @Override
    public boolean isReadyToWrite() {
        return tipExposed;
    }

    @Override
    public String getName() {
        return "Click";
    }
}
