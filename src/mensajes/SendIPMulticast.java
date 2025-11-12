package mensajes;

public class SendIPMulticast implements Mensaje{
    //Atributos
    private String IPMulti;

    //Contructores
    public SendIPMulticast(String grupo){
        this.IPMulti= grupo;
    }

    //Metodos
    @Override
    public String getMSG() {
        return IPMulti;
    }

    @Override
    public void setMSG(String mensaje) {
        this.IPMulti = mensaje;
    }
}
