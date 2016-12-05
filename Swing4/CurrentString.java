package Swing4;
import java.util.*;
import java.util.concurrent.*;


class CurrentString {

    private String string;
    Random r = new Random();
    ZrobmySobieStringa str;

    CurrentString(String string) {
        str = new ZrobmySobieStringa();
        this.string = string;
    }

    public String getCurrentString() {
        return string;
    }

    public void setCurrentString() {
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                string = str.pomieszaj(string.length());
            }
        }, 0, r.nextInt(2000 - 200 + 1) + 200, TimeUnit.MILLISECONDS);
    }
}