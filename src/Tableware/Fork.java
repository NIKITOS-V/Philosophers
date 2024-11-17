package Tableware;

import Formating.Logger;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fork {
    static long lastID = 0;

    private final long ID;
    private volatile boolean busy = false;
    private Long philosopherID;

    public Fork(){
        this.ID = lastID++;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean busy, Long philosopherID) {
        this.busy = busy;
        this.philosopherID = philosopherID;
    }

    @Override
    public String toString() {
        String info = "";

        if (isBusy()){
            info = String.format(
                "ID: %d, busy by philosopher: %s",
                this.ID,
                this.philosopherID
            );

        } else {
            info = String.format(
                "ID: %d, is busy: %s",
                this.ID,
                this.busy
            );
        }

        return info;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ID);
    }
}
