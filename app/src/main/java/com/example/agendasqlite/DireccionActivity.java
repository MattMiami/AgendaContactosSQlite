package com.example.agendasqlite;

import androidx.fragment.app.FragmentActivity;

import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.example.agendasqlite.Model.Contactos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class DireccionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Contactos c;
    private List<Address> direccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Recogemos el contacto
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            c = (Contactos) extra.getSerializable("contacto");

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        /*Para posicionar la direccion de nuestro contacto en el mapa, creamos un objeto Geocoder
        al cual le pasaremos la direccion del contacto desde el cual hemos viajado hasta aquí,
         reducimos los resultados de busque a 1

         */

        mMap = googleMap;
        Geocoder geo = new Geocoder(this);
        try{
            direccion = geo.getFromLocationName(c.getAddress(),1);
            /*
        Nos cremos el objeto Address al cual le pasaremos el string que hay en la posicion 0 de la lista(la direccion del contacto)
        obtenemos la latitud y longitud de la direccion y por ultimo añadimos la marca, animamos y movemos la camara hasta ese punto
         */
            Address address = direccion.get(0);
            LatLng ll = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ll).title(c.getAddress()));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(ll));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        }catch (Exception ex){}



    }
}