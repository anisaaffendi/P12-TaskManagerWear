package sg.edu.rp.id19030019.p12_taskmanagerwear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter <Task>{
    private ArrayList<Task> tasks;
    private Context context;
    private TextView tv1, tv2;

    public TaskAdapter(Context context, int resource, ArrayList<Task> objects){
        super(context, resource, objects);
        tasks = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        tv1 = rowView.findViewById(R.id.tv1);
        tv2 = rowView.findViewById(R.id.tv2);

        Task currentTask = tasks.get(position);

        tv1.setText(currentTask.getId() + "" + currentTask.getName());
        tv2.setText(currentTask.getDesc());

        return rowView;
    }
}
