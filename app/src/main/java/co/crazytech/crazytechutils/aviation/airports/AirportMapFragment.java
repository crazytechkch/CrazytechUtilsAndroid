package co.crazytech.crazytechutils.aviation.airports;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

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
        airport = new Airport("name","kuching");
        getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(airport.getLat(),airport.getLng()),10));
        return rootView;

    }
}
