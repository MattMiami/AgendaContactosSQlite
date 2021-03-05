package com.example.agendasqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendasqlite.DataBaseContactos.Ctrl_Contactos;
import com.example.agendasqlite.Model.Contactos;

public class InfoContatoActivity extends AppCompatActivity {

    private Contactos c;
    private TextView etEditarNombre, etEditarApellidos, etEditarTelefono, etEditarDireccion, etEditarEmail;
    private ImageButton btBorrar, btEditar, btMapa, btLlamar, btEmail;
    private Ctrl_Contactos db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contato);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        db = new Ctrl_Contactos(this);

        //Recogemos el contacto
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            c = (Contactos) extra.getSerializable("contacto");
            initContacto();
        }

        //funcionalidad botones
        eliminarContacto();
        llamarContacto();
        editarContacto();
        enviarEmail();
        direccionMapa();

    }


    //Iniciamos los componentes de la vista
    private void initViews(){
        //textos
        etEditarNombre = findViewById(R.id.etEditarNombre);
        etEditarApellidos = findViewById(R.id.etEditarApellido);
        etEditarTelefono = findViewById(R.id.etEditarTelefono);
        etEditarDireccion = findViewById(R.id.etEditarDireccion);
        etEditarEmail = findViewById(R.id.etEditarEmail);
        //botones
        btBorrar = findViewById(R.id.btEliminar);
        btEditar = findViewById(R.id.btEditar);
        btLlamar =  findViewById(R.id.btLlamar);
        btEmail = findViewById(R.id.btEnviarEmail);
        btMapa = findViewById(R.id.btVerMapa);
    }

    //Mostramos los datos del contacto
    private void initContacto(){
        etEditarNombre.setText(c.getName());
        etEditarApellidos.setText(c.getSurname());
        etEditarTelefono.setText(c.getPhone());
        etEditarDireccion.setText(c.getAddress());
        etEditarEmail.setText(c.getMail());
    }

    //Eliminar el contacto
    private  void eliminarContacto(){

        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Eliminar Contacto");
                    alert.setMessage("¿Seguro que quiers eliminar este contacto de tu agenda?");
                    alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if(db.borrar(c.getIdContacto()) > 0) {
                                Toast.makeText(getApplicationContext(), "Contacto eliminado correctamente", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(InfoContatoActivity.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    alert.show();
            }
        });
    }

    //Modificar la informacion de usuario
    private void editarContacto(){
        boolean refresh;
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Si ninguno de los campos estan vacios
                if (!etEditarNombre.getText().toString().isEmpty()
                        && !etEditarApellidos.getText().toString().isEmpty()
                        && !etEditarTelefono.getText().toString().isEmpty()
                        && !etEditarDireccion.getText().toString().isEmpty()
                        && !etEditarEmail.getText().toString().isEmpty()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Modificar Contacto");
                    alert.setMessage("¿Seguro que quiers modificar los datos de este contacto?");
                    alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            c.setName(etEditarNombre.getText().toString());
                            c.setSurname(etEditarApellidos.getText().toString());
                            c.setPhone(etEditarTelefono.getText().toString());
                            c.setAddress(etEditarDireccion.getText().toString());
                            c.setMail(etEditarEmail.getText().toString());

                            if (db.modificar(c) != -1) {
                                Toast.makeText(getApplicationContext(), "Contacto modificado correctamente", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(InfoContatoActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    alert.show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No dejes campos vacios para modificar un contacto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //Para llamar al contacto
    private void llamarContacto(){
        btLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = c.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL,  Uri.parse("tel:"+phone));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    // Abrimos el correo electronico y le pasamos el email del contacto al que queremos enviarle un email
    private void enviarEmail(){
        btEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + c.getMail())); // only email apps should handle this
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    //Viajamos a la actividad de mapas con el objeto Contacto metido en el bundle
    private void direccionMapa(){
        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(InfoContatoActivity.this, DireccionActivity.class);
                intent.putExtra("contacto", c);
                startActivity(intent);
            }
        });
    }


}