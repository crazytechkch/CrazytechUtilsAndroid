package co.crazytech.crazytechutils.aviation.airports;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        new MoveToAirport().execute("kuching");
        Snackbar snackbar = Snackbar.make(rootView,"",Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackLayout = (Snackbar.SnackbarLayout)snackbar.getView();

        return rootView;

    }
    private class MoveToAirport extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String keyword = strings[0];
            String json = "{\"results\":[{\"name\":\"Kuching Intl\",\"city\":\"Kuching\",\"country\":\"Malaysia\",\"iata\":\"KCH\",\"icao\":\"WBGG\",\"latitude\":\"1.484697\",\"longitude\":\"110.346933\",\"altitude\":\"89\",\"timezone\":\"8\",\"dst\":\"N\",\"tzdatabase\":\"Asia\\/Kuala_Lumpur\"}],\"success\":1}";
            try {
                String url = Airport.getUrl()+"?"+Airport.getWhere(keyword);
                int timeout = 5000;
                InputStream in;
                URL javaUrl = new URL(url);
                HttpURLConnection con = (HttpURLConnection)javaUrl.openConnection();
                con.setRequestMethod("GET");
               BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = br.read()) != -1) {
                    sb.append((char) cp);
                }
                br.close();
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
            LatLng latlng = new LatLng(airport.getLat(), airport.getLng());
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
            getMap().addMarker(new MarkerOptions().position(latlng));
            super.onPostExecute(json);
        }
    }

}
