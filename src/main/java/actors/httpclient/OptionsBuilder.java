package actors.httpclient;



public class OptionsBuilder extends ReqBuilder<OptionsBuilder> {
    public OptionsBuilder() {
        this.type = TYPE.OPTIONS;
    }

}