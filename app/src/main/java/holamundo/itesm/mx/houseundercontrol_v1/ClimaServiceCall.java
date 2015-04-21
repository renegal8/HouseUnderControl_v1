package holamundo.itesm.mx.houseundercontrol_v1;

import android.util.Log;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eduardo on 05/03/15.
 */
public class ClimaServiceCall {

    public ClimaServiceCall(){
        super();
    }

    //Metodo para establecer conexion
    public JSONObject getJSONFromUrl (String Url) throws Exception {
        JSONObject jsonObject = null;
        HttpURLConnection httpURLConnection = null;

        try{
            URL myURL = new URL(Url);
            httpURLConnection = (HttpURLConnection) myURL.openConnection();
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpStatus.SC_OK){
                String responseString = readStream(httpURLConnection.getInputStream());
                jsonObject = new JSONObject(responseString);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }finally {
            if (null!= httpURLConnection)
                httpURLConnection.disconnect();
        }
        return jsonObject;
    }

    //metodo que lee el buffer
    private String readStream (InputStream in){
        BufferedReader reader= null;
        StringBuffer response = null;

        try {
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line = "";
            response = new StringBuffer();
            while ((line = reader.readLine())!= null){
                response.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (reader != null)
           try {
                reader.close();
            }catch (IOException e){
               e.printStackTrace();
           }
        }
        return response.toString();
    }
}
