package co.crazytech.crazytechutils.aviation.airports;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import co.crazytech.crazytechutils.map.MapFragment;

/**
 * Created by eric on 12/1/2015.
 */
public class AirportMapFragment extends MapFragment {
   private View rootView;
    private Airport airport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        new GetJson().execute("kuching");
        return rootView;

    }
    private class GetJson extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection c = null;
            String keyword = strings[0];
            String url = Airport.getUrl(keyword);
            Log.d("AirportMapFragment",url);
            int timeout = 5000;
            String json = "{\"results\":[{\"name\":\"Kuching Intl\",\"city\":\"Kuching\",\"country\":\"Malaysia\",\"iata\":\"KCH\",\"icao\":\"WBGG\",\"latitude\":\"1.484697\",\"longitude\":\"110.346933\",\"altitude\":\"89\",\"timezone\":\"8\",\"dst\":\"N\",\"tzdatabase\":\"Asia\\/Kuala_Lumpur\"}],\"success\":1}";
            InputStream in;
            try {
                in = new URL(url).openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                 StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = br.read()) != -1) {
                    sb.append((char) cp);
                }
                json=sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {

            JsonObject jsonObj = new Gson().fromJson(json, JsonObject.class);
            if (jsonObj.get("success").getAsInt()==1) {
                JsonArray jsonArr = jsonObj.get("results").getAsJsonArray();
                for (JsonElement jsonElement : jsonArr) {
                    airport = new Airport(jsonElement.getAsJsonObject());
                    break;
                }
            }
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(airport.getLat(), airport.getLng()), 15));
            super.onPostExecute(json);
        }
    }

}
