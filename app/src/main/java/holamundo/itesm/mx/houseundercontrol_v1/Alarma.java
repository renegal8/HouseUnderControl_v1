package holamundo.itesm.mx.houseundercontrol_v1;

/**
 * Created by Toño on 21/04/2015.
 */
public class Alarma {
        private int id;
        private String fecha;
        private int idHouse;


        public Alarma(int id, String fecha, int idHouse) {
            this.id = id;
            this.fecha = fecha;
            this.idHouse=idHouse;
        }

        public Alarma(String fecha, int idHouse) {

            this.fecha = fecha;
            this.idHouse=idHouse;

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
}


