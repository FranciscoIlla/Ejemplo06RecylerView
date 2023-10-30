package francisco.illa.ejemplo06recylerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

import francisco.illa.ejemplo06recylerview.adapters.ToDoAdapter;
import francisco.illa.ejemplo06recylerview.databinding.ActivityMainBinding;
import francisco.illa.ejemplo06recylerview.modelos.ToDo;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private ArrayList<ToDo> todoList;

    private ToDoAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        todoList = new ArrayList<>();
        //crearTareas();

        adapter = new ToDoAdapter(todoList, R.layout.todo_view_model, MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
createToDo().show();
            }
        });
    }

    private void crearTareas() {
        for (int i = 0; i < 100000; i++) {
            todoList.add(new ToDo("Titulo"+i,"Contenido"+i));
        }
    }

    private AlertDialog createToDo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("CREAR TAREA");
        builder.setCancelable(false);

        View todoAlert = LayoutInflater.from(this).inflate(R.layout.todo_model_alert, null);
        EditText txtTitulo = todoAlert.findViewById(R.id.txtTituloToDoModelAlert);
        EditText txtContenido = todoAlert.findViewById(R.id.txtContentToDoModelAlert);
        builder.setView(todoAlert);

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtTitulo.getText().toString().isEmpty() || txtContenido.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "FALTAN DATPS", Toast.LENGTH_SHORT);
                }else{
                    todoList.add(new ToDo(txtTitulo.getText().toString(),
                            txtContenido.getText().toString()));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return builder.create();
    }

}

