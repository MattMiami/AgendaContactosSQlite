package com.example.agendasqlite.DataBaseContactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendasqlite.Model.Contactos;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_Contactos extends DB_Contactos{

    public Ctrl_Contactos(Context context) {
        super(context);
    }

//-------------------------Insertamo un nuevo contacto----------------------------

    public long insertar(Contactos c){
        long resgitros_afectados = -1;
        //Para poder leer y escribir en  la base de datos
        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("name", c.getName());
            valores.put("surname", c.getSurname());
            valores.put("mail", c.getMail());
            valores.put("address", c.getAddress());
            valores.put("phone", c.getPhone());

            try{
                resgitros_afectados = db.insert("Contactos", null, valores);
            }catch (Exception ex){}

        }
        db.close();
        return resgitros_afectados;
    }

    //------------------------------Borramos por clave primaria(telefono)-----------------------------

    public long borrar(int idContacto){
        long registros_afectados = 0;

        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            ContentValues valores = new ContentValues();
            registros_afectados =    db.delete("Contactos", "IdContacto= " + idContacto , null);
        }
        db.close();
        return registros_afectados;

    }

    //----------------------------Modificamos el contacto que pasamos por parametro------------------------------

    public long modificar(Contactos c){
        long resgitros_afectados = -1;

        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("name", c.getName());
            valores.put("surname", c.getSurname());
            valores.put("mail", c.getMail());
            valores.put("address", c.getAddress());
            valores.put("phone", c.getPhone());
            resgitros_afectados = db.update("Contactos", valores, "IdContacto= " + c.getIdContacto(), null);
        }
        db.close();
        return resgitros_afectados;
    }

    //----------------------------Obtenemos la lista de contactos ordenados por nombre-------------------------------
    public ArrayList<Contactos> listarContactos(){
        SQLiteDatabase db=getReadableDatabase();
        Contactos c;
        List<Contactos> listaContacto=new ArrayList<>();
        if(db!=null){
            String[] campos = {"IdContacto","name","surname","mail","address","phone"};
            Cursor cursor = db.query("Contactos",campos,null,null,null,null,"name");
            if(cursor.moveToFirst()){
                do{
                    c=new Contactos(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5));

                    listaContacto.add(c);
                }while(cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return (ArrayList) listaContacto;
    }
}
