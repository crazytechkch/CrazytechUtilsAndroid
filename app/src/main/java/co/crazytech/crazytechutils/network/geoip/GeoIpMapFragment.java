package co.crazytech.crazytechutils.network.geoip;

import android.os.AsyncTask;
import android.os.Bundle;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import co.crazytech.crazytechutils.aviation.airports.Airport;
import co.crazytech.crazytechutils.map.MapFragment;

/**
 * Created by eric on 12/2/2015.
 */
public class GeoIpMapFragment extends MapFragment{
    private View rootView;
    private GeoIp geoip;
    private String hostname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        hostname="";
        new MoveToLocation().execute(hostname);
        return rootView;
    }

    private class MoveToLocation extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String keyword = strings[0];
            String json = "";
            try {
                String url = GeoIp.URL+keyword;
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
            geoip = new GeoIp(jsonObj);
            LatLng latlng = new LatLng(geoip.getLatitude(), geoip.getLongitude());
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
            getMap().addMarker(new MarkerOptions().position(latlng));
            super.onPostExecute(json);
        }
    }

}
