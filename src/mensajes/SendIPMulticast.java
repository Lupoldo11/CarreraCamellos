package mensajes;

import java.io.Serializable;

public class SendIPMulticast implements Serializable {
    //Atributos
    private String IPMulti;

    //Contructores
    public SendIPMulticast(String ip){
        this.IPMulti=ip;
    }

    //Metodos
    public String getIPMulti(){
        return IPMulti;
    }
}
