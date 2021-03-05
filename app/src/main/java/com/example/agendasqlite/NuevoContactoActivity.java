package com.example.agendasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendasqlite.DataBaseContactos.Ctrl_Contactos;
import com.example.agendasqlite.Model.Contactos;

import java.util.ArrayList;

public class NuevoContactoActivity extends AppCompatActivity {

    private TextView etNombre, etApellidos, etTelefono, etDireccion, etEmail;
    private Button btGuardar;
    private Ctrl_Contactos db;
    private ArrayList<Contactos> listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        db = new Ctrl_Contactos(this);
        listaContactos = db.listarContactos();

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNombre.getText().toString().isEmpty()
                        && !etApellidos.getText().toString().isEmpty()
                        && !etTelefono.getText().toString().isEmpty()
                        && !etDireccion.getText().toString().isEmpty()
                        && !etEmail.getText().toString().isEmpty()) {

                    Contactos c = new Contactos();
                    c.setName(etNombre.getText().toString());
                    c.setSurname(etApellidos.getText().toString());
                    c.setPhone(etTelefono.getText().toString());
                    c.setAddress(etDireccion.getText().toString());
                    c.setMail(etEmail.getText().toString());

                    if (existeContacto()) {
                        Toast.makeText(getApplicationContext(), "El telefono o el email del contacto ya existen.", Toast.LENGTH_SHORT).show();

                    } else {
                        if (db.insertar(c) != -1) {
                            Toast.makeText(getApplicationContext(), "Nuevo contacto añadido correctamente", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(NuevoContactoActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Completa toda la informacion para añadir el contacto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Iniciamos los elementos del view
    private void initViews() {
        etNombre = findViewById(R.id.etNuevoNombre);
        etApellidos = findViewById(R.id.etNuevoApellido);
        etTelefono = findViewById(R.id.etNuevoTelefono);
        etDireccion = findViewById(R.id.etNevaDireccion);
        etEmail = findViewById(R.id.etNuevoEmail);
        btGuardar = findViewById(R.id.btGuardar);
    }

    //Si el telefono o el email existen no se prodrá insertar el contacto
    private boolean existeContacto() {
        boolean existeCont = false;
        for (Contactos contacto : listaContactos) {
            if (contacto.getPhone().equals(etTelefono.getText().toString()) || contacto.getMail().equals(etEmail.getText().toString())) {
                existeCont = true;
            }
        }
        return existeCont;
    }
}