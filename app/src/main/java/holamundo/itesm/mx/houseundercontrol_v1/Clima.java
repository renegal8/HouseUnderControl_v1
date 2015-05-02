package holamundo.itesm.mx.houseundercontrol_v1;

/**
 * Created by Eduardo on 05/03/15.
 */
public class Clima  {

    double luz;
    double temperatura;
    double statLuz;

    public Clima(double luz, double temperatura, double statLuz) {
        this.luz = luz;
        this.temperatura = temperatura;
        this.statLuz = statLuz;
    }

    public double getStatLuz() {
        return statLuz;
    }

    public void setStatLuz(double statLuz) {
        this.statLuz = statLuz;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getLuz() {
        return luz;
    }

    public void setLuz(double luz) {
        this.luz = luz;
    }
}
