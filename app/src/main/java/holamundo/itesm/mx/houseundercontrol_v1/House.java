package holamundo.itesm.mx.houseundercontrol_v1;

/**
 * Created by renegalvez on 4/15/15.
 */
public class House {
    private int id;
    private String name;
    private int cantCuartos;
    private byte[] foto;
    private int idFoto;
    private String fecha;
    private String address;

    public House(int id, String name, int cantCuartos, byte[] foto, int idFoto, String fecha, String address) {
        this.id = id;
        this.name = name;
        this.cantCuartos = cantCuartos;
        this.foto = foto;
        this.idFoto = idFoto;
        this.fecha = fecha;
        this.address = address;
    }

    public House(String name, int cantCuartos, byte[] foto, int idFoto, String fecha, String address) {
        this.name = name;
        this.cantCuartos = cantCuartos;
        this.foto = foto;
        this.idFoto = idFoto;
        this.fecha = fecha;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCantCuartos() {
        return cantCuartos;
    }

    public void setCantCuartos(int cantCuartos) {
        this.cantCuartos = cantCuartos;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
