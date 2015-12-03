package co.crazytech.crazytechutils.network.geoip;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

import co.crazytech.crazytechutils.R;
import co.crazytech.crazytechutils.aviation.airports.Airport;
import co.crazytech.crazytechutils.map.MapFragment;
import co.crazytech.crazytechutils.map.MapWithSliderFragment;

/**
 * Created by eric on 12/2/2015.
 */
public class GeoIpMapFragment extends MapWithSliderFragment{
    private View rootView,sliderView;
    private GeoIp geoip;
    private String hostname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        initSliderView();
        hostname="";
        new MoveToLocation().execute(hostname);
        return rootView;
    }

    private void initSliderView(){
        LayoutInflater mInflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        sliderView = mInflater.inflate(R.layout.slider_geoip, null, false);
        final EditText searchText = (EditText)sliderView.findViewById(R.id.editText);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    new MoveToLocation().execute(searchText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) searchText.selectAll();
            }
        });
        ImageButton btnSearch = (ImageButton)sliderView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MoveToLocation().execute(searchText.getText().toString());
            }
        });
        inflateSliderContent(sliderView);
    }

    private void changeValues(GeoIp geoip) {
        TextView textIp = (TextView)sliderView.findViewById(R.id.text_ip);
        textIp.setText(geoip.getIp());
        TextView textCountryCode = (TextView)sliderView.findViewById(R.id.text_country_code);
        textCountryCode.setText(geoip.getCountryCode());
        TextView textCountry = (TextView)sliderView.findViewById(R.id.text_country);
        textCountry.setText(geoip.getCountry());
        TextView textRegionCode= (TextView)sliderView.findViewById(R.id.text_region_code);
        textRegionCode.setText(geoip.getRegionCode() + "");
        TextView textRegion= (TextView)sliderView.findViewById(R.id.text_region);
        textRegion.setText(geoip.getRegion() + "");
        TextView textCity= (TextView)sliderView.findViewById(R.id.text_city);
        textCity.setText(geoip.getCity() + "");
        TextView textZipcode= (TextView)sliderView.findViewById(R.id.text_zip);
        textZipcode.setText(geoip.getZipcode() + "");
        TextView textMetroCode= (TextView)sliderView.findViewById(R.id.text_metro_code);
        textMetroCode.setText(geoip.getMetroCode() + "");
        TextView textTimezone= (TextView)sliderView.findViewById(R.id.text_timezone);
        textTimezone.setText(geoip.getTimezone() + "");
        TextView textLat = (TextView)sliderView.findViewById(R.id.text_lat);
        textLat.setText(geoip.getLatitude()+"");
        TextView textLng = (TextView)sliderView.findViewById(R.id.text_lng);
        textLng.setText(geoip.getLongitude()+"");
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
            if (jsonObj!=null){
                geoip = new GeoIp(jsonObj);
                changeValues(geoip);
                LatLng latlng = new LatLng(geoip.getLatitude(), geoip.getLongitude());
                getMap().clear();
                getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                getMap().addMarker(new MarkerOptions().position(latlng));
                super.onPostExecute(json);
            }
        }
    }

}
