package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Toño on 21/04/2015.
 */
public class AlarmaListAdapter extends ArrayAdapter<Alarma> {
    private Context context;
    int layoutResourceId;
    List<Alarma> lista;

    public AlarmaListAdapter (Context context, int resource, List<Alarma> alarma){
        super(context,resource,alarma);
        this.context=context;
        this.lista=alarma;
        this.layoutResourceId= resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId,parent,false);
        }
        TextView rowTV = (TextView) row.findViewById(R.id.rowTV);
        TextView rowTV2 = (TextView) row.findViewById(R.id.rowTV2);
        if(!lista.isEmpty()){
            Alarma alarma = lista.get(position);

            rowTV.setText(String.valueOf(alarma.getFecha()));
            rowTV2.setText(String.valueOf(alarma.getIdHouse()));
        }
        return row;
    }
}