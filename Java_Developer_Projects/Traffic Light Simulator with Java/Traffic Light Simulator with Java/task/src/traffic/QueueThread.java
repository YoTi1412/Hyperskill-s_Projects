package traffic;

import java.util.Timer;
import java.util.TimerTask;

public class QueueThread {
    private final TrafficLights trafficLights;
    private final Timer timer;
    private final ConsolePrinter printer;
    private int secondsPassed;
    private boolean isInSystemState = false;

    public QueueThread(TrafficLights trafficLights, ConsolePrinter printer) {
        this.trafficLights = trafficLights;
        this.printer = printer;
        this.timer = new Timer("QueueThread", true);
        this.timer.scheduleAtFixedRate(new DisplayTask(), 0, 1000);
    }

    public void purge() {
        timer.cancel();
        timer.purge();
    }

    private String getSystemInfo() {
        return """
                ! %ds. have passed since system startup !
                ! Number of roads: %d !
                ! Interval: %d !

                %s

                ! Press "Enter" to open menu !"""
                .formatted(secondsPassed,
                        trafficLights.getRoadCapacity(),
                        trafficLights.getInterval(),
                        trafficLights.getRoadsState());
    }

    public void setInSystemState(boolean state) {
        this.isInSystemState = state;
    }

    private class DisplayTask extends TimerTask {
        @Override
        public void run() {
            secondsPassed++;

            trafficLights.notifySecondPassed();

            if (isInSystemState){
                printer.clearConsole();
                System.out.println(getSystemInfo());
            }
        }
    }
}