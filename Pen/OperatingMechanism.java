package LLD_Assignment.Design_Assignment.Pen;

public interface OperatingMechanism {
    void start();

    void close();

    boolean isReadyToWrite();

    String getName();
}
