package com.example.sandeepemekar.googlemapcaranimation;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    List<LatLng> polyLineList = new ArrayList<>();
    private GoogleMap mMap;
    private int index;
    private int next;
    private Handler handler = new Handler();
    private PolylineOptions polylineOptions;
    private Marker carMarker;
    private float v;
    private double lat, lng;
    private LatLng startPosition;
    private LatLng endPosition;
    Runnable staticCarRunnable = new Runnable() {
        @Override
        public void run() {
            if (index < (polyLineList.size() - 1)) {
                index++;
                next = index + 1;
            } else {
                index = -1;
                next = 1;
                stopRepeatingTask();
                return;
            }

            if (index < (polyLineList.size() - 1)) {
                startPosition = polyLineList.get(index);
                endPosition = polyLineList.get(next);

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(1500);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        v = valueAnimator.getAnimatedFraction();
                        lng = v * endPosition.longitude + (1 - v)
                                * startPosition.longitude;
                        lat = v * endPosition.latitude + (1 - v)
                                * startPosition.latitude;
                        LatLng newPos = new LatLng(lat, lng);
                        carMarker.setPosition(newPos);
                        carMarker.setAnchor(0.5f, 0.5f);
                        carMarker.setRotation(getBearing(startPosition, newPos));
                        mMap.moveCamera(CameraUpdateFactory
                                .newCameraPosition
                                        (new CameraPosition.Builder()
                                                .target(newPos)
                                                .zoom(20)
                                                .build()));


                    }
                });
                valueAnimator.start();
                handler.postDelayed(this, 1500);
            }
        }
    };

    /**
     * Bearing between two LatLng pair
     *
     * @param begin First LatLng Pair
     * @param end   Second LatLng Pair
     * @return The bearing or the angle at which the marker should rotate for going to {@code end} LAtLng.
     */
    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadLatLongTravelList();

    }

    private void loadLatLongTravelList() {
        polyLineList.add(new LatLng(18.586306901893188, 73.81864640861748));
        polyLineList.add(new LatLng(18.586264635861824, 73.81864707916975));
        polyLineList.add(new LatLng(18.586215060653796, 73.81864842027426));
        polyLineList.add(new LatLng(18.586123854944933, 73.81864707916975));
        polyLineList.add(new LatLng(18.58603964057109, 73.81864942610265));
        polyLineList.add(new LatLng(18.585972269042056, 73.81864942610265));
        polyLineList.add(new LatLng(18.58588328773647, 73.81863299757242));
        polyLineList.add(new LatLng(18.585803204521696, 73.81863702088594));
        polyLineList.add(new LatLng(18.585742824295206, 73.81864003837107));
        polyLineList.add(new LatLng(18.585692295562907, 73.81863500922918));
        polyLineList.add(new LatLng(18.585617296913746, 73.81863802671432));
        polyLineList.add(new LatLng(18.58556041232309, 73.81862092763186));
        polyLineList.add(new LatLng(18.58556613256212, 73.81856057792902));
        polyLineList.add(new LatLng(18.585582339904974, 73.81846871227026));
        polyLineList.add(new LatLng(18.58558647118821, 73.81841238588095));
        polyLineList.add(new LatLng(18.58559441596337, 73.81837584078312));
        polyLineList.add(new LatLng(18.585603949693063, 73.8183057680726));
        polyLineList.add(new LatLng(18.585616661331827, 73.81823100149632));
        polyLineList.add(new LatLng(18.58564081344288, 73.81820417940617));
        polyLineList.add(new LatLng(18.585723121269293, 73.8182182610035));
        polyLineList.add(new LatLng(18.585795577546904, 73.81824407726526));
        polyLineList.add(new LatLng(18.585916020150705, 73.81823636591434));
        polyLineList.add(new LatLng(18.586027246754078, 73.8182544708252));
        polyLineList.add(new LatLng(18.58612925737504, 73.81826788187026));
        polyLineList.add(new LatLng(18.586224594348774, 73.81826218217611));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(polyLineList.get(0)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                drawPolyLine();
            }
        }, 1500);
    }

    void drawPolyLine() {

        if (mMap != null)
            mMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polyLineList) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 20);
        mMap.animateCamera(mCameraUpdate);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(polyLineList);
        mMap.addPolyline(polylineOptions);

        animateCar();

    }

    private void animateCar() {
        LatLng latLng = new LatLng(polyLineList.get(0).latitude, polyLineList.get(0).longitude);

        carMarker = mMap.addMarker(new MarkerOptions().position(latLng).
                flat(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.my_car)));


        index = -1;
        next = 1;
        handler.postDelayed(staticCarRunnable, 1500);
    }

    void stopRepeatingTask() {

        if (staticCarRunnable != null) {
            handler.removeCallbacks(staticCarRunnable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRepeatingTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
