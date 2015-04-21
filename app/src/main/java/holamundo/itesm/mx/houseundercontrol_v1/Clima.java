package holamundo.itesm.mx.houseundercontrol_v1;

/**
 * Created by Eduardo on 05/03/15.
 */
public class Clima  {

    double tempActual;
    double tempMinima;
    double tempMaxima;

    public Clima(double tempActual, double tempMinima, double tempMaxima) {
        this.tempActual = tempActual;
        this.tempMinima = tempMinima;
        this.tempMaxima = tempMaxima;
    }
    public double getTempActual() {
        return tempActual;
    }

    public void setTempActual(double tempActual) {
        this.tempActual = tempActual;
    }

    public double getTempMinima() {
        return tempMinima;
    }

    public void setTempMinima(double tempMinima) {
        this.tempMinima = tempMinima;
    }

    public double getTempMaxima() {
        return tempMaxima;
    }

    public void setTempMaxima(double tempMaxima) {
        this.tempMaxima = tempMaxima;
    }


}
