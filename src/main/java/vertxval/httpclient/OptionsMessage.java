package vertxval.httpclient;



public class OptionsMessage extends ReqMessage<OptionsMessage> {
    public OptionsMessage() {
        this.type = TYPE.OPTIONS;
    }

}