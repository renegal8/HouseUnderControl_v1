package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Eduardo Bertaud on 05/03/15.
 */
public class ClimaAdapter extends ArrayAdapter<Clima> {


    Context context;
    int resourceId;
    List<Clima> listaClimas;

    public ClimaAdapter(Context context, int resourceId, List<Clima> climaList) {
        super(context, resourceId, climaList);
        this.context = context;
        this.resourceId = resourceId;
        this.listaClimas = climaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        //convertView ---> visita a reusar, si es nulo se crea

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(resourceId,parent,false);
        }

        TextView tempActual = (TextView)row.findViewById(R.id.actualValorTV);
        TextView tempMin = (TextView)row.findViewById(R.id.minimaValueTV);
        TextView tempMax = (TextView)row.findViewById(R.id.maximaValueTV);

        //obtiene el elemento de la lista de temperaturas
        Clima clima = listaClimas.get(position);

        DecimalFormat df= new DecimalFormat("#.##");

        tempActual.setText(String.valueOf(df.format(clima.getTempActual())));
        tempMin.setText(String.valueOf(df.format(clima.getTempMinima())));
        tempMax.setText(String.valueOf(df.format(clima.getTempMaxima())));

        return row;
    }
}

