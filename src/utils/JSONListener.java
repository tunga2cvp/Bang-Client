package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONListener {
    public static void main(String[] args) {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("D:\\workspace\\Bang-Client\\src\\JSON\\server_to_client.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // read token and type
            String token = (String) jsonObject.get("token");
            String jsontype = (String) jsonObject.get("jsontype");
            //System.out.println("token: " + token);
            //System.out.println("jsontype: " + jsontype);


//            // đọc address
//            Map address = ((Map) jsonObject.get("address"));
//            // đọc address Map
//            Iterator<Map.Entry> itr1 = address.entrySet().iterator();
//            while (itr1.hasNext()) {
//                Map.Entry pair = itr1.next();
//                System.out.println(pair.getKey() + " : " + pair.getValue());
//            }

            // read player list
            JSONArray playerList = (JSONArray) jsonObject.get("players");
//            System.out.println("");
//            System.out.println("Player details: ");
            //Iterating the contents of the array
            Iterator player = playerList.iterator();
            while(player.hasNext()) {
                //System.out.println(player.next());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
