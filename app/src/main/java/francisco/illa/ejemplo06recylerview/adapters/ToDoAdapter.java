package francisco.illa.ejemplo06recylerview.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import francisco.illa.ejemplo06recylerview.R;
import francisco.illa.ejemplo06recylerview.modelos.ToDo;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TodoVH> {


    private List<ToDo> objects; //la lista de tareas
    private int resource; // la fila, la vista para construir la fila
    private Context context; // la actividad que tiene el RecyclerView para mostar

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //instancia tantos elementos como quepan en pantalla
        View todoView = LayoutInflater.from(context).inflate(resource, null);


        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        todoView.setLayoutParams(lp);
        return new TodoVH(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoVH holder, int position) {
        ToDo toDo = objects.get(position);

        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()) {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }
        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              confirmUpdate("¿SEGURO QUE QUIERES CAMBIAR?", toDo).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        //Sirve para que el adapter averigue cuantos objetos va a mostrar
        return objects.size();
    }

    private AlertDialog confirmUpdate(String titulo, ToDo todo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todo.setCompletado(!todo.isCompletado());
                notifyDataSetChanged();
            }
        });
        return builder.create();
    }


    public class TodoVH extends RecyclerView.ViewHolder {

        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado;

        public TodoVH(@NonNull View itemView) {
            super(itemView);

            lbTitulo = itemView.findViewById(R.id.lbTituloToDoViewMode);
            lbContenido = itemView.findViewById(R.id.lbContenidoToDoViewModel);
            lbFecha = itemView.findViewById(R.id.lbFechaToDoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);

        }
    }
}
