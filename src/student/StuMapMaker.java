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

public class StuMapMaker implements model.MapMaker {


    @Override
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException {
        Scanner mapReader = new Scanner(in);
        String fileLine;
        ArrayList<StuStation> stations = new ArrayList<>();
        ArrayList<Route> routes = new ArrayList<>();
        int counter = 0;

        while (mapReader.hasNextLine()){
            fileLine = mapReader.nextLine();
            String[] splitRegex = fileLine.split(" ");
            if(splitRegex.length==1){
                counter = counter +1;
                fileLine = mapReader.nextLine();
                splitRegex = fileLine.split(" ");
            } if(counter==0){
                stations.add(new StuStation(parseInt(splitRegex[0]), parseInt(splitRegex[1]), parseInt(splitRegex[2]), splitRegex[3]));
            }else{
                Baron tempVar = Baron.UNCLAIMED;
                if(splitRegex[2].equals("UNCLAIMED")) {
                    tempVar = Baron.UNCLAIMED;
                }else if (splitRegex[2].equals("GREEN")){
                    tempVar = Baron.GREEN;
                }else if (splitRegex[2].equals("RED")){
                    tempVar = Baron.RED;
                }else if(splitRegex[2].equals("BLUE")){
                    tempVar = Baron.BLUE;
                }else if (splitRegex[2].equals("YELLOW")){
                    tempVar = Baron.YELLOW;
                }

                routes.add(new StuRoute(stations.get(parseInt(splitRegex[0])), stations.get(parseInt(splitRegex[1])) , tempVar ));

                }
        }
        return null;
    }

    @Override
    public void writeMap(RailroadMap map, OutputStream out) throws RailroadBaronsException {

    }
}
