package co.crazytech.crazytechutils.aviation.airports;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import co.crazytech.crazytechutils.map.MapWithSliderFragment;

/**
 * Created by eric on 12/1/2015.
 */
public class AirportMapFragment extends MapWithSliderFragment {
   private View rootView,sliderView;
    private Airport airport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        initSliderView();
        new MoveToAirport().execute("kuching");
        return rootView;

    }

    private void initSliderView(){
        LayoutInflater mInflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        sliderView = mInflater.inflate(R.layout.slider_airports, null, false);
        final EditText searchText = (EditText)sliderView.findViewById(R.id.editText);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    new MoveToAirport().execute(searchText.getText().toString());
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
                new MoveToAirport().execute(searchText.getText().toString());
            }
        });
        inflateSliderContent(sliderView);
    }

     private void changeValues(Airport airport) {
        TextView textName = (TextView)sliderView.findViewById(R.id.text_name);
        textName.setText(airport.getName());
        TextView textCity = (TextView)sliderView.findViewById(R.id.text_city);
         textCity.setText(airport.getCity());
         TextView textCountry = (TextView)sliderView.findViewById(R.id.text_country);
        textCountry.setText(airport.getCountry());
        TextView textLat = (TextView)sliderView.findViewById(R.id.text_lat);
        textLat.setText(airport.getLat()+"");
        TextView textLng = (TextView)sliderView.findViewById(R.id.text_long);
        textLng.setText(airport.getLng()+ "");
        TextView textAlt= (TextView)sliderView.findViewById(R.id.text_alt);
        textAlt.setText(airport.getAlt()+ "");
        TextView textTimezone= (TextView)sliderView.findViewById(R.id.text_timezone);
        textTimezone.setText(airport.getTimezone() + "");
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
            if (jsonObj!=null){
                if (jsonObj.get("success").getAsInt()==1) {
                    final JsonArray jsonArr = jsonObj.get("results").getAsJsonArray();
                    if (jsonArr.size()>1) {
                        String[] airports = new String[jsonArr.size()];
                        for (int i = 0; i <jsonArr.size() ; i++) {
                            airports[i] = jsonArr.get(i).getAsJsonObject().get("name").getAsString();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Pick Airport");
                        builder.setItems(airports,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                airport = new Airport (jsonArr.get(which).getAsJsonObject());
                                changeValuesAndLoadMap(airport);
                            }
                        });
                        builder.create().show();
                    } else if (jsonArr.size()==1){
                        airport = new Airport (jsonArr.get(0).getAsJsonObject());
                        changeValuesAndLoadMap(airport);
                    }
                }

                super.onPostExecute(json);
            }
        }

        private void changeValuesAndLoadMap(Airport airport){
            changeValues(airport);
            LatLng latlng = new LatLng(airport.getLat(), airport.getLng());
            getMap().clear();
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
            getMap().addMarker(new MarkerOptions().position(latlng));
        }
    }

}
