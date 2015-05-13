package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Toño on 20/04/2015.
 */
public class HouseListAdapter extends ArrayAdapter<House> {
    private Context context;
    int layoutResourceId;
    List<House> lista;

    public HouseListAdapter (Context context, int resource, List<House> house){
        super(context,resource,house);
        this.context=context;
        this.lista=house;
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
            House appliance = lista.get(position);

            rowTV.setText(String.valueOf(appliance.getName()));
            rowTV2.setText(String.valueOf(appliance.getAddress()));
        }
        return row;
    }

    public void update(List<House> newlist) {
        lista.clear();
        lista.addAll(newlist);
        this.notifyDataSetChanged();
    }
}


