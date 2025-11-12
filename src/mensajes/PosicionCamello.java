package mensajes;

import java.io.Serializable;

public class PosicionCamello implements Mensaje {
    private String mensaje;

    public PosicionCamello(){}

    @Override
    public String getMSG() {
        return mensaje;
    }

    @Override
    public void setMSG(String mensaje) {
        this.mensaje=mensaje;
    }

    //Atributos

    //Metodos

    //Constructores

}
