package traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrafficLights {
    private final int roadsCapacity;
    private final int interval;
    private int remainingInterval;

    private final List<Road> roads = new ArrayList<>();
    TrafficLights(int roadsCapacity, int interval){
        this.roadsCapacity = roadsCapacity;
        this.interval = interval;
    }

    public int getRoadCapacity() {
        return this.roadsCapacity;
    }

    public int getInterval() {
        return interval;
    }

    public String addRoad(String road) {
        if (roads.size() == roadsCapacity){
            return "The queue is full";
        }

        if (roads.isEmpty()){
            remainingInterval = interval + 1;
        }

        roads.add(new Road(road, remainingInterval + roads.size() * interval));
        return road + " Added!";
    }

    public String getRoadsState() {
        return roads.stream().map(Road::getState).collect(Collectors.joining("\n"));
    }

    public String deleteRoad() {
        if (roads.isEmpty()){
            return "The queue is empty";
        }

        String msg = "%s deleted".formatted(roads.get(0).getName());
        adjustCountersIfClosedRoadDeleted();
        roads.remove(0);

        return msg;
    }

    private void adjustCountersIfClosedRoadDeleted() {
        if (roads.stream().noneMatch(road -> road.secondsCounter<= interval)) {
            return;
        }
        var secondsOnRoadToDelete = roads.get(0).getSecondsCounter();
        if (secondsOnRoadToDelete > interval) { // road is closed
            roads.stream()
                    .filter(road -> road.secondsCounter > secondsOnRoadToDelete)
                    .forEach(Road::subtractInterval);
        }
    }

    public void notifySecondPassed() {
        roads.forEach(Road::countDown);
        remainingInterval = remainingInterval > 1 ? remainingInterval - 1 : interval;
    }

    private class Road {
        private final String name;
        private int secondsCounter;

        private static final String ANSI_RED = "\u001B[31m";
        private static final String ANSI_GREEN = "\u001B[32m";
        private static final String ANSI_YELLOW = "\u001B[33m";
        private static final String ANSI_RESET = "\u001B[0m";

        Road(String name, int secondsCounter){
            this.name = name;
            this.secondsCounter = secondsCounter;
        }

        public String getName() {
            return name;
        }

        public int getSecondsCounter() {
            return secondsCounter;
        }

        public String getState(){
            return secondsCounter <= interval ?
                    "Road \"%s\" is %sopen for %ds.%s".formatted(name, ANSI_GREEN, secondsCounter, ANSI_RESET) :
                    "Road \"%s\" is %sclosed for %ds.%s".formatted(name, ANSI_RED, secondsCounter - interval, ANSI_RESET);
        }

        public void countDown() {
            secondsCounter =
                    secondsCounter == 1 ?
                            roads.size() * interval :
                            secondsCounter - 1;

        }

        private void subtractInterval() {
            secondsCounter -= interval;
        }
    }
}