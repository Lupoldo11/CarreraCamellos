package mensajes;

import java.io.Serializable;

public interface Mensaje extends Serializable {
    public String getData();
    public void setData(String mensaje);
}
