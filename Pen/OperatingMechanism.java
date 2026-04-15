package Pen;

public interface OperatingMechanism {
    void start();

    void close();

    boolean isReadyToWrite();

    String getName();
}
