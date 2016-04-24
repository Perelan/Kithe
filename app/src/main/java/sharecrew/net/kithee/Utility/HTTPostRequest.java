package sharecrew.net.kithee.Utility;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Deep on 11.03.16.
 */
public class HTTPostRequest {
    public String fetchData(String input){
        String newLink = "http://sharecrew.net/nerdherd/php/core.php?data=" + input;
        URL url;
        HttpURLConnection urlConnection = null;

        System.out.println(newLink);
        try{
            url = new URL(newLink);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);

            int data = isr.read();
            StringBuilder sb = new StringBuilder();

            while (data != -1) {
                char current = (char) data;
                data = isr.read();
                sb.append(current);
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert urlConnection != null;
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}

