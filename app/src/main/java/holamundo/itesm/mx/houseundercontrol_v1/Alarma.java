package holamundo.itesm.mx.houseundercontrol_v1;

/**
 * Created by Toño on 21/04/2015.
 */
public class Alarma {
        private int id;
        private String fecha;
        private int idHouse;
        private String statuzFoco;
        private String intensidadLuz;
        private String temperatura;


        public Alarma(int id, String fecha, int idHouse, String statuzFoco, String intensidadLuz,String temperatura) {
            this.id = id;
            this.fecha = fecha;
            this.idHouse=idHouse;
            this.statuzFoco=statuzFoco;
            this.intensidadLuz=intensidadLuz;
            this.temperatura=temperatura;
        }

        public Alarma(String fecha, int idHouse, String statuzFoco, String intensidadLuz,String temperatura) {

            this.fecha = fecha;
            this.idHouse=idHouse;
            this.statuzFoco=statuzFoco;
            this.intensidadLuz=intensidadLuz;
            this.temperatura=temperatura;

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

       public int getIdHouse() {
            return idHouse;
        }

        public void setIdHouse(int idHouse) {
            this.idHouse = idHouse;
        }

    public String getIntensidadLuz() {
        return intensidadLuz;
    }

    public String getStatuzFoco() {
        return statuzFoco;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setIntensidadLuz(String intensidadLuz) {
        this.intensidadLuz = intensidadLuz;
    }

    public void setStatuzFoco(String statuzFoco) {
        this.statuzFoco = statuzFoco;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}


