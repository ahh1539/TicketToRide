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


    /**
     *
     * @param in The function used create a railroad map
     * @return a new railroad barons map
     * @throws RailroadBaronsException
     */
    @Override
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException {
        Scanner mapReader = new Scanner(in);
        String fileLine;
        ArrayList<StuStation> stations = new ArrayList<>();
        ArrayList<Route> routes = new ArrayList<>();
        int counter = 0;

        while (mapReader.hasNextLine()) {
            fileLine = mapReader.nextLine();
            String[] splitRegex = fileLine.split(" ");
            if (splitRegex.length == 1) {
                counter = counter + 1;
                fileLine = mapReader.nextLine();
                splitRegex = fileLine.split(" ");
            }

            if (counter==0) {
                String sName = "";
                int size = splitRegex.length;
                for (int x=3; x<size;x++) {
                    sName += splitRegex[x];
                    if (x!=size-1) {
                        sName += " ";
                    }
                }
                sName = splitRegex[3]; //+splitRegex[4];

                stations.add(new StuStation(parseInt(splitRegex[0]), parseInt(splitRegex[1]), parseInt(splitRegex[2]), sName));
                //need to add station name somehow (it can be multiple words long)
            } else {
                Baron tempVar = Baron.UNCLAIMED;
                if (splitRegex[2].equals("UNCLAIMED")) {
                    tempVar = Baron.UNCLAIMED;
                } else if (splitRegex[2].equals("GREEN")) {
                    tempVar = Baron.GREEN;
                } else if (splitRegex[2].equals("RED")) {
                    tempVar = Baron.RED;
                } else if (splitRegex[2].equals("BLUE")) {
                    tempVar = Baron.BLUE;
                } else if (splitRegex[2].equals("YELLOW")) {
                    tempVar = Baron.YELLOW;
                }

                routes.add(new StuRoute(stations.get(parseInt(splitRegex[0])), stations.get(parseInt(splitRegex[1])), tempVar));

            }
        }

            int rows_checker = 0;
            int cols_checker = 0;


            for (StuStation s:stations) {
                if (s.getRow()>rows_checker) {

                    rows_checker = s.getRow();
                }
                if (s.getCol()>cols_checker) {

                    cols_checker = s.getCol();
                }
            }
        return new StuRailRoadBaronsMap(rows_checker, cols_checker, routes);
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
}
