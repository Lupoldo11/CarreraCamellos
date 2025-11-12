package mensajes;

public class ServerReady implements Mensaje{
    private String mensaje;

    public ServerReady(){}

    @Override
    public String getMSG() {
        return mensaje;
    }

    @Override
    public void setMSG(String mensaje) {
        this.mensaje=mensaje;
    }
}
