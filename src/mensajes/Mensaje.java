package mensajes;

import java.io.Serializable;

public interface Mensaje extends Serializable {
    public String getMSG();
    public void setMSG(String mensaje);
}
