package co.crazytech.crazytechutils.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import co.crazytech.crazytechutils.R;

/**
 * Created by eric on 12/3/2015.
 */
public class MapWithSliderFragment extends MapFragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_map_slider,container,false);
        initMap();
        declareViews(rootView);
        return rootView;
    }

    public void inflateSliderContent(View contentView){
        LinearLayout content = (LinearLayout)rootView.findViewById(R.id.sliding_content);
        content.addView(contentView);
    }
}
