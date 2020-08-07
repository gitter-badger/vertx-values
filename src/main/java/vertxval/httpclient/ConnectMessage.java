package vertxval.httpclient;



public class ConnectMessage extends ReqMessage<ConnectMessage> {
    public ConnectMessage() {
        this.type = TYPE.CONNECT;
    }

}