package actors.codecs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import jsonvalues.JsArray;
import jsonvalues.JsObj;

public class RegisterJsValuesCodecs extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise)  {
        try {
            vertx.eventBus()
                 .registerDefaultCodec(JsObj.class,
                                       JsObjMessageCodec.INSTANCE
                                      );

            vertx.eventBus()
                 .registerDefaultCodec(JsArray.class,
                                       JsArrayMessageCodec.INSTANCE
                                      );

            startPromise.complete();
        } catch (Exception e) {
           startPromise.fail(e);
        }


    }
}
