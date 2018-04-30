package student;

import model.Baron;
import model.RailroadBaronsException;
import model.RailroadMap;
import model.Route;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


/**
 * Reads and writes map files
 * @author Brett Farruggia & Alex Hurely
 */
public class StuMapMaker implements model.MapMaker {

    public ArrayList<Route> routtee = new ArrayList<>();


    /**
     *
     * @param in The function used create a railroad map
     * @return a new railroad barons map
     * @throws RailroadBaronsException
     */
    @Override
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException {
        Scanner fileReader = new Scanner(in);
        String line;
        ArrayList<StuStation> stations = new ArrayList<>();
        ArrayList<Route> routes = new ArrayList<>();
        int diff = 0;

        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            String[] split = line.split(" ");
            if (split.length==1) {
                diff+=1;
                line = fileReader.nextLine();
                split = line.split(" ");
            }

            if (diff==0) {
                String stationName = "";
                int size = split.length;
                for (int x=3; x<size;x++) {
                    stationName += split[x];
                    if (x!=size-1) {
                        stationName += " ";
                    }
                }
                stations.add(new StuStation(stationName,
                        parseInt(split[0]),parseInt(split[1]),parseInt(split[2])));
            } else {
                Baron tempBaron = Baron.UNCLAIMED;
                if (split[2].equals("UNCLAIMED")) {
                    tempBaron = Baron.UNCLAIMED;

                } else if (split[2].equals("GREEN")) {
                    tempBaron = Baron.GREEN;

                } else if (split[2].equals("BLUE")) {
                    tempBaron = Baron.BLUE;

                } else if (split[2].equals("YELLOW")) {
                    tempBaron = Baron.YELLOW;

                } else if (split[2].equals("RED")) {
                    tempBaron = Baron.RED;
                }
                routes.add(new StuRoute(stations.get(parseInt(split[0])),
                        stations.get(parseInt(split[1])), tempBaron));
            }
        }
        int rows = 0;
        int cols = 0;
        for (StuStation s:stations) {
            if (s.getRow()>rows) {
                rows = s.getRow();
            }
            if (s.getCol()>cols) {
                cols = s.getCol();
            }
        }
        routtee = routes;
        return new StuRailRoadBaronsMap(rows+1,cols+1,routes);
    }

    /**
     * Writes an in progress game to the map files folder, with claimed routes
     * @param map The Railroad to write out the data to
     * @param out The outputstream that writes the data
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void writeMap(RailroadMap map, OutputStream out) throws RailroadBaronsException {

    }

    public ArrayList getRoutes(){
        return routtee;
    }
}
