package com.example.fragmentsjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fragmentsjava.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap maps;

    ActivityMainBinding mBinding;

    Marker marker;

    CameraPosition cameraPosition;

    ArrayList<PolyLinesData> polyLinesData_list = new ArrayList<>();

    ArrayList<Polyline> deleted_polyline_list = new ArrayList<>();

    int size;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {



        maps = googleMap;

        //POLYLINE LIST----------------------------------->

        polyLinesData_list.add(new PolyLinesData(new LatLng(31.467156942384996, 43.69397661374925),Color.YELLOW,"yellow",BitmapDescriptorFactory.HUE_YELLOW));
        polyLinesData_list.add(new PolyLinesData(new LatLng(64.22600361086207, 79.2017875666138), Color.GREEN,"green",BitmapDescriptorFactory.HUE_GREEN));
        polyLinesData_list.add(new PolyLinesData(new LatLng(33.395741177843604, 93.96741192325055),Color.BLUE,"blue",BitmapDescriptorFactory.HUE_BLUE));
        polyLinesData_list.add(new PolyLinesData(new LatLng(50.820561010215044, 48.08850767227208),Color.RED,"red",BitmapDescriptorFactory.HUE_RED));
        polyLinesData_list.add(new PolyLinesData(new LatLng(50.70937891814292, 94.14319316559147),Color.GREEN,"green",BitmapDescriptorFactory.HUE_GREEN));
        polyLinesData_list.add(new PolyLinesData(new LatLng(31.467156942384996, 43.69397661374925), Color.BLUE,"blue",BitmapDescriptorFactory.HUE_BLUE));


        //DISPLAYING POLYlINES ON MAP--------------------->

        LatLng point1 = null;

        for (int i=0; i<polyLinesData_list.size() ; i++){

            if(point1 != null){

                addPolyLine(point1,polyLinesData_list.get(i).getLatLng(),polyLinesData_list.get(i).getColor(),polyLinesData_list.get(i).getTag());
                addTag(polyLinesData_list.get(i).getLatLng(),polyLinesData_list.get(i).getTag(),polyLinesData_list.get(i).getTagColor());

            }
            point1 = polyLinesData_list.get(i).getLatLng();
        }


        //DELETING POLYLINE FROM MAP---------------------->


        maps.setOnPolylineClickListener(

                polyline ->
                        deletePolyLine(polyline)
                );



        //RESTORING POLYLINE------------------------------->

        mBinding.restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restorePolyline();
            }
        });




    }
    private void addMarker(LatLng latLng, String title) {
        if (marker != null)
            marker.remove();
        marker = maps.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    private void animateCamera(LatLng latLng) {
        if (marker == null) {
            cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(maps.getCameraPosition().zoom)
                    .build();
            maps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(7)
                    .build();
            maps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }



     }
     private Polyline addPolyLine(LatLng startPoint,LatLng endingPoint,Integer color,String tag){

         Polyline polyline = maps.addPolyline(new PolylineOptions()
                 .clickable(true)
                 .add(startPoint,endingPoint)
                 .width(5f)
                 .color(color)
         );

       polyline.setTag(tag);

       return polyline;

    }

    public void addTag(LatLng latLng,String tag, Float color){
        marker = maps.addMarker(new MarkerOptions()
                .position(latLng)
                .title(tag)
                .icon(BitmapDescriptorFactory.defaultMarker(color)));

    }

    private Drawable changeColor(int colorRes){

        Drawable mDrawable = ContextCompat.getDrawable(this, R.drawable.ic_star);
        Drawable mWrappedDrawable = mDrawable.mutate();
        mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable);
        DrawableCompat.setTint(mWrappedDrawable, colorRes);
        DrawableCompat.setTintMode(mWrappedDrawable, PorterDuff.Mode.SRC_IN);
        return mWrappedDrawable;
    }

    public void deletePolyLine(Polyline polyline){


        polyline.remove();

        deleted_polyline_list.add(polyline);

        size = deleted_polyline_list.size();
        Toast.makeText(MainActivity.this, size, Toast.LENGTH_SHORT).show();

    }

    private Polyline restorePolyline(){

        Polyline polyline = deleted_polyline_list.get(size);

        deleted_polyline_list.remove(size);

        size--;

        return polyline;
    }


}
