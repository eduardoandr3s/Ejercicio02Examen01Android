package educodedev.ejercicio02examen01;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import educodedev.ejercicio02examen01.adapters.CochesAdapter;
import educodedev.ejercicio02examen01.databinding.ActivityMainBinding;
import educodedev.ejercicio02examen01.models.Coche;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Coche> cochesList;
    private CochesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cochesList = new ArrayList<>();

        setSupportActionBar(binding.toolbar);

        adapter = new CochesAdapter(cochesList,R.layout.coche_view_holder,this);
        layoutManager = new GridLayoutManager(this,1);

        binding.contentMain.contenedor.setLayoutManager(layoutManager);
        binding.contentMain.contenedor.setAdapter(adapter);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    crearCocheCarrera().show();
            }
        });



    }



    private AlertDialog crearCocheCarrera () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.agregar_carrera);
        builder.setCancelable(false);

        View cocheViewModel =
                LayoutInflater.from(this).inflate(R.layout.coche_view_model, null);

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
                        lblExcesoVueltas.setText(R.string.vueltas_imposibles_ning_n_circuito_permite_m_s_de_99_vueltas);
                    }
                }catch (Exception e){

                }

            }
        };

        txtVueltasContempladas.addTextChangedListener(textWatcher);




        builder.setNegativeButton(R.string.cancelar, null);
        builder.setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombrePiloto = txtNombrePiloto.getText().toString(), modeloCoche = txtModeloCoche.getText().toString(), vueltasContempladas = txtVueltasContempladas.getText().toString();


                if (!nombrePiloto.isEmpty() && !modeloCoche.isEmpty() && !vueltasContempladas.isEmpty()) {
                            Coche coche = new Coche(nombrePiloto,modeloCoche,Integer.parseInt(vueltasContempladas));
                            cochesList.add(0,coche);
                            adapter.notifyItemInserted(0);

                }else {
                    Toast.makeText(MainActivity.this, "FALTAN DATOS",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        return builder.create();
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LISTA", cochesList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cochesList.addAll((ArrayList<Coche>) savedInstanceState.getSerializable("LISTA"));
        adapter.notifyItemRangeInserted(0,cochesList.size());
    }

}