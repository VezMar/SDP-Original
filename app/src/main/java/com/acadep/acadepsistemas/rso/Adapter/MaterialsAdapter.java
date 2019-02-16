package com.acadep.acadepsistemas.rso.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.acadep.acadepsistemas.rso.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> Materials;
    private List<Integer> CantidadMaterials;

    public MaterialsAdapter(Context mContext, List<String> materials, List<Integer> cantidadMaterials) {
        this.mContext = mContext;
        Materials = materials;
        CantidadMaterials = cantidadMaterials;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_materials, viewGroup, false);
        MaterialsViewHolder materialsViewHolder =  new MaterialsViewHolder(view);

        return materialsViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MaterialsViewHolder) viewHolder).txtNumMaterial.setText(""+i);
        if(Materials.get(i).length()>25){
            ((MaterialsViewHolder) viewHolder).txtMaterial.setText(Materials.get(i).substring(0,23)+"...");
        }else{
            ((MaterialsViewHolder) viewHolder).txtMaterial.setText(Materials.get(i));
        }
    }

    @Override
    public int getItemCount() {
        if (Materials!=null) {
            return Materials.size();
        }else
            return 0;
    }


    public class MaterialsViewHolder extends RecyclerView.ViewHolder {

        TextView txtNumMaterial, txtMaterial;
        EditText edit_cantidad;

        public MaterialsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.txtNumMaterial = itemView.findViewById(R.id.txtNumMaterial);
            this.txtMaterial = itemView.findViewById(R.id.txtMaterial);
            this.edit_cantidad = itemView.findViewById(R.id.edit_cantidad);
        }
    }
}
