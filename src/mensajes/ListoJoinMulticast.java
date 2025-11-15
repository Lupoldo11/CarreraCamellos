package mensajes;

import java.io.Serializable;

public class ListoJoinMulticast implements Mensaje, Serializable {
    private String nombre;

    @Override
    public String getData() {
        return nombre;
    }
    @Override
    public void setData(String mensaje) {
        this.nombre=mensaje;
    }
}
