package com.example.agendasqlite.Adptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendasqlite.InfoContatoActivity;
import com.example.agendasqlite.Model.Contactos;
import com.example.agendasqlite.R;

import java.util.ArrayList;

public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.AdaptadorViewHolder>{

    ArrayList<Contactos> listaContactos;
    Context contexto;

    public AdapterContactos(ArrayList<Contactos> listaContactos, Context contexto) {
        this.listaContactos = listaContactos;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
        AdaptadorViewHolder avh = new AdaptadorViewHolder(itemView);

        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int position) {
        Contactos contactos = listaContactos.get(position);
        holder.tvName.setText(contactos.getName());


        holder.contraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, InfoContatoActivity.class);
                intent.putExtra("contacto", contactos);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contexto.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    //Para filtrar la lista del recycler por nombre de contacto
    public void setFiltro(ArrayList<Contactos> filtrarLista){
        this.listaContactos = new ArrayList<>();
        this.listaContactos.addAll(filtrarLista);
        notifyDataSetChanged();

    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout contraint;
        TextView tvName;



        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);

            //Aqui accedemos a cada uno de los elementos del layout
            contraint = itemView.findViewById(R.id.cl_Contactos);
            tvName = itemView.findViewById(R.id.tvNombreContacto);


        }
    }


}
