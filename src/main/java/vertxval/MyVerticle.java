package vertxval;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.*;

/**
 Represents an actor, which is a Verticle in Vertx jargon. It's the unit of computation.

 @param <I> type of the message sent to the actor */
class MyVerticle<I> extends AbstractVerticle {
    private final Consumer<Message<I>> consumer;
    private final String address;
    private MessageConsumer<Object> messageConsumer;

    /**
     Creates an actor instance that when deployed will process the messages sent to the given
     address

     @param consumer consumer that takes and processes the messages sent to this actor
     @param address  address where the actor is listening on
     */
    public MyVerticle(final Consumer<Message<I>> consumer,
                      final String address
                     ) {
        this.consumer = requireNonNull(consumer);
        this.address = requireNonNull(address);
    }

    /**
     Register the given consumer listening on the given address. When it's done, this
     actor is ready to receive messages on that address.

     @param promise promise to be completed when the consumer is registered
     */
    @Override
    @SuppressWarnings("unchecked")
    //if you interact with this actor via its ActorRef object, there's no
    //way you can send it messages of type different than I
    public void start(final Promise<Void> promise) {
        try {
            messageConsumer = vertx.eventBus()
                                   .consumer(address,
                                             m ->
                                             {
                                                 try {
                                                     this.consumer.accept((Message<I>) m);
                                                 } catch (Throwable e) {
                                                     m.reply(GET_EXECUTING_VERTIClE_EXCEPTION.apply(e));
                                                 }
                                             }
                                            );
            messageConsumer.completionHandler(promise);
        } catch (Exception e) {
            promise.fail(GET_DEPLOYING_VERTIClE_EXCEPTION.apply(e));
        }
    }

    /**
     Unregister the consumer listening on the give address that was created during the
     {@link #start(Promise)} method.

     @param promise promise to be completed when the consumer is unregistered
     */
    @Override
    public void stop(final Promise<Void> promise) {
        try {
            if (messageConsumer.isRegistered())
                messageConsumer.unregister(promise);
            else promise.complete();
        } catch (Exception e) {
            promise.fail(GET_STOPPING_VERTICLE_EXCEPTION.apply(e));

        }
    }
}
