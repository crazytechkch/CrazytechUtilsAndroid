package co.crazytech.crazytechutils.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import co.crazytech.crazytechutils.R;

public class MapFragment extends Fragment {
	private View rootV,goneView;
	private GoogleMap map;
	private ImageButton btnMapView, btnMapType;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootV = inflater.inflate(R.layout.layout_map, container,false);
		initMap();
		declareViews(rootV);
		return rootV;
	}

	
	public void initMap() {
		map = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMap();

		checkPermission(getContext(),getActivity(),111,Manifest.permission.ACCESS_FINE_LOCATION);
		checkPermission(getContext(),getActivity(),112,Manifest.permission.ACCESS_COARSE_LOCATION);
		if(map!=null)map.setMyLocationEnabled(true);
		
	}

	private void checkPermission(Context context, Activity activity, int reqCode, String... permission){
		int hasPermission = ContextCompat.checkSelfPermission(context, permission[0]);
		if (hasPermission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity,permission,reqCode);
		}
	}
	
	public void declareViews(View rootView) {
		btnMapType = (ImageButton)rootView.findViewById(R.id.btn_maptype);
		btnMapType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(map.getMapType()== GoogleMap.MAP_TYPE_NORMAL)map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				else map.setMapType(GoogleMap.MAP_TYPE_NORMAL);;
			}
		});
		btnMapView = (ImageButton)rootView.findViewById(R.id.btn_mapview);
		btnMapView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (getGoneView()!=null) {
					switch (getGoneView().getVisibility()) {
					case View.VISIBLE:
						getGoneView().setVisibility(View.GONE);
						break;
						
					default:
						getGoneView().setVisibility(View.VISIBLE);
						break;
					}
					
				}
			}
		});
	}

	public FrameLayout getFrameLayout(){
		return (FrameLayout)rootV.findViewById(R.id.map_frame);
	}

	public View getGoneView() {
		return goneView;
	}

	public void setGoneView(View goneView) {
		this.goneView = goneView;
	}

	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}
}
