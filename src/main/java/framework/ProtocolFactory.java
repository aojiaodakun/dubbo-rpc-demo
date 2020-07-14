package framework;

import protocol.dubbo.DubboProtocol;
import protocol.http.HttpProtocol;

public class ProtocolFactory {

    public static Protocol getProtocol(){
        String name = System.getProperty("protocolName");
        if(name == null || name == ""){
            name = "http";
        }
        switch (name){
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new HttpProtocol();
    }

}
