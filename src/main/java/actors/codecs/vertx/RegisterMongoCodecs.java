package actors.codecs.vertx;

import actors.mongo.UpdateMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class RegisterMongoCodecs extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise)  {
        try {
            vertx.eventBus()
                 .registerDefaultCodec(UpdateMessage.class,
                                       UpdateMessageCodec.INSTANCE
                                      );
            startPromise.complete();
        } catch (Exception e) {
           startPromise.fail(e);
        }


    }
}
