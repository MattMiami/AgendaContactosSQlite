package com.example.agendasqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.agendasqlite.Adptador.AdapterContactos;
import com.example.agendasqlite.DataBaseContactos.Ctrl_Contactos;
import com.example.agendasqlite.Model.Contactos;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ImageButton btCrear, btBuscar;
    private TextView etBuscar;

    private Ctrl_Contactos db;
    private RecyclerView rvContactos;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterContactos adapter;
    private Contactos c;
    private ArrayList<Contactos> listaContactos;
    private SwipeRefreshLayout swiperefresh;
    private boolean refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cargarDatosContactos();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Agenda Contactos");
        setSupportActionBar(toolbar);

        //Navegamos al activity para crear un nuevo contacto
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,NuevoContactoActivity.class);
                startActivity(intent);
            }
        });

        //Refrescar la lista de contactos
        swiperefresh.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listaContactos.clear();
                    cargarDatosContactos();
                    swiperefresh.setRefreshing(false);
                }
            }
        );

    }

    //Iniciamos los componentes de la vista
    private void initViews(){
        btCrear = findViewById(R.id.btCrear);
        rvContactos = findViewById(R.id.rvContactos);
        swiperefresh = findViewById(R.id.swiperefresh);
        //Asignamos el itemView a nuestro recycler view
        layoutManager = new LinearLayoutManager(this);
        rvContactos.setLayoutManager(layoutManager);

    }

    //Listamos todos los contactos que tenemos en la base de datos
    private void cargarDatosContactos() {
        db = new Ctrl_Contactos(this);
        listaContactos = db.listarContactos();
        //Inicializamos el adaptador, lo asociamos al recycler y le añado el divider
        adapter = new AdapterContactos(listaContactos, this);
        rvContactos.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvContactos.setAdapter(adapter);
    }

    //Menu de busqueda por nombre
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar, menu);
        MenuItem buscador = menu.findItem(R.id.buscador);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(buscador);

        sv.setOnQueryTextListener(this);
        //Funcionalidad boton busqueda del menu, cuando haya texto filtra y cuando no haya no lo hara y nos muestra la lista completa
        MenuItemCompat.setOnActionExpandListener(buscador, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {return true;}

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFiltro(listaContactos);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Métodos para filtrar por  el nombre del contacto
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //Creamos un array de contactos y utilizamos el método filtrarBusqueda
    @Override
    public boolean onQueryTextChange(String newText) {

        try {
            ArrayList<Contactos> listaFiltrada =  filtroBusqueda(listaContactos, newText);
            adapter.setFiltro(listaFiltrada);

        }catch (Exception ex){}
        return false;
    }

    /*Con este método comparamos lo introducido en la barra de búsqueda con los nombre de la lista de contactos
        si el nombre de un contacto contiene la cadena de texto la añadimos a la lista filtrada
     */
    private ArrayList<Contactos> filtroBusqueda(ArrayList<Contactos> nombres, String cadena){
        ArrayList<Contactos> listaFiltrada =  new ArrayList<>();
        try {
            cadena = cadena.toLowerCase();
            for (Contactos c : nombres){
                String contacto = c.getName().toLowerCase();
                if (contacto.contains(cadena)){
                    listaFiltrada.add(c);
                }
            }
        }catch (Exception ex){}
        return listaFiltrada;
    }
}