package sharecrew.net.kithee.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Uti {

    public static String sendData(String maxfare, String departuredate, String returndate, String lengthofstay, String car){

        JSONObject obj = new JSONObject();
        try {
            obj.put("maxfare", maxfare);
            obj.put("departuredate", departuredate);
            obj.put("returndate", returndate);
            obj.put("lengthofstay", lengthofstay);
            obj.put("car", car);

            System.out.println(obj.toString());

            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
