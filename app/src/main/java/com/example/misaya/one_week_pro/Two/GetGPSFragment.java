package com.example.misaya.one_week_pro.Two;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.misaya.one_week_pro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetGPSFragment extends Fragment {
    private TextView tv_gps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_g, container, false);
        initView(view);

        getLocation();

        return view;
    }

    private void getLocation() {
        LocationManager locationManager;
        String service = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getActivity().getSystemService(service);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.ACCURACY_LOW);

        String provider = locationManager.getBestProvider(criteria, true);
        @SuppressLint("MissingPermission") Location lastKnownLocation = locationManager.getLastKnownLocation(provider);

        updateToNewLocation(lastKnownLocation);
//        locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
//                locationListener);

}

    private void updateToNewLocation(Location lastKnownLocation) {
        if (lastKnownLocation!=null){
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            tv_gps.setText("纬度："+latitude + "\n经度:"+longitude);
        }else {
            tv_gps.setText("无法获取地理位置");
        }

    }

    private void initView(View view) {
        tv_gps = (TextView) view.findViewById(R.id.tv_gps);
    }
}
