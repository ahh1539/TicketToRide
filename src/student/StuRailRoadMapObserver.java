package student;

import model.*;

public class StuRailRoadMapObserver implements RailroadMapObserver {

    @Override
    public void routeClaimed(RailroadMap map, Route route) {
        Baron owner = route.getBaron();
        if (owner == Baron.UNCLAIMED){

        }
        else {
            route.claim(owner);
        }

    }
}
