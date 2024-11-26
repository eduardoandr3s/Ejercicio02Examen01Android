package educodedev.ejercicio02examen01.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import educodedev.ejercicio02examen01.MainActivity;
import educodedev.ejercicio02examen01.R;
import educodedev.ejercicio02examen01.models.Coche;

public class CochesAdapter extends RecyclerView.Adapter<CochesAdapter.CocheVH> {
    private List<Coche> objects;
    private int fila;
    private Context context;

    public CochesAdapter(List<Coche> objects, int fila, Context context) {
        this.objects = objects;
        this.fila = fila;
        this.context = context;
    }

    @NonNull
    @Override
    public CochesAdapter.CocheVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View cocheView = LayoutInflater.from(context).inflate(fila, null);
        cocheView.setLayoutParams(
                new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        return new CocheVH(cocheView);
    }

    @Override
    public void onBindViewHolder(@NonNull CochesAdapter.CocheVH holder, int position) {
        Coche coche = objects.get(position);

        holder.txtNombre.setText(coche.getPiloto());
        holder.txtModelo.setText(coche.getModelo());
        holder.txtVueltas.setText(String.valueOf(coche.getVueltasCompletadas()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               actualizarCoche(coche).show();
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = confirmarBorrado(coche);
                alertDialog.show();


                objects.remove(coche);

            }
        });


    }

    @Override
    public int getItemCount() {
        return objects.size();

    }

    private AlertDialog actualizarCoche(Coche coche) {

       AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar coche");
        builder.setCancelable(false);

        View cocheViewModel =
                LayoutInflater.from(context).inflate(R.layout.coche_view_model, null);
        TextView lblCifras = cocheViewModel.findViewById(R.id.lblCifrasCocheViewModel);
        TextView lblExcesoVueltas = cocheViewModel.findViewById(R.id.lblExcesoVueltasCocheViewModel);

        EditText txtNombrePiloto = cocheViewModel.findViewById(R.id.txtNombrePilotoCocheViewModel);
        EditText txtModeloCoche = cocheViewModel.findViewById(R.id.txtModeloCocheViewModel);
        EditText txtVueltasContempladas = cocheViewModel.findViewById(R.id.txtVueltasCompletadasCocheViewModel);
        builder.setView(cocheViewModel);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    lblCifras.setText(String.valueOf(txtVueltasContempladas.getText().toString().length()));

                    if (txtVueltasContempladas.getText().toString().length() > 2){
                        lblExcesoVueltas.setText("   ¡Vueltas imposibles! Ningún circuito permite \n   más de 99 vueltas.");
                    }
                }catch (Exception e){

                }
            }
        };


        txtVueltasContempladas.addTextChangedListener(textWatcher);


        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("ACTUALIZAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nombrePiloto = txtNombrePiloto.getText().toString(), modeloCoche = txtModeloCoche.getText().toString(), vueltasContempladas = txtVueltasContempladas.getText().toString();



                        if (!nombrePiloto.isEmpty() && !modeloCoche.isEmpty() && !vueltasContempladas.isEmpty()) {
                           coche.setPiloto(nombrePiloto);
                           coche.setModelo(modeloCoche);
                           coche.setVueltasCompletadas(Integer.parseInt(vueltasContempladas));

                            notifyItemChanged(objects.indexOf(coche));
                        } else {
                            Toast.makeText(context, "FALTAN DATOS",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        return builder.create();
    }

    private AlertDialog confirmarBorrado(Coche coche) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.seguro);
        builder.setCancelable(false);
        TextView textView = new TextView(context);
        textView.setText(R.string.esta_accion_no_se_puede_deshacer);
        textView.setTextColor(Color.RED);
        textView.setTextSize(24);
        builder.setView(textView);
        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("ACEPTAR", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int posicion = objects.indexOf(coche);
                        objects.remove(coche);
                        notifyItemRemoved(posicion);
                    }
                });
        return builder.create();
    }

    public class CocheVH extends RecyclerView.ViewHolder {

        EditText txtNombre;
        EditText txtModelo;
        EditText txtVueltas;
        ImageButton btnEliminar;


        public CocheVH(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCocheViewHolder);
            txtModelo = itemView.findViewById(R.id.txtModeloCocheViewHolder);
            txtVueltas = itemView.findViewById(R.id.txtVueltasCocheViewHolder);

            btnEliminar = itemView.findViewById(R.id.btnEliminarCocheViewHolder);
        }
    }


}
