package vertxval;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import vertxval.exp.Cons;
import vertxval.exp.λ;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 It represents a reference to an actor, the unit of computation. It allows to send messages
 to the actor with the method {@link #tell(DeliveryOptions)}, or establish conversations with the
 method {@link #ask(DeliveryOptions)}.

 @param <I> the type of the input message sent to this actor
 @param <O> the type of the output message returned by this actor */
public class VerticleRef<I, O> {

    private final Vertx vertx;
    /**
     the default delivery options that will be used if not specified
     */
    private static final DeliveryOptions DEFAULT = new DeliveryOptions();
    /**
     address where an actor is listening on
     */
    public final String address;
    /**
     the identifiers assigned to the different instances of this actor after being deployed.
     To undeploy an actor, its identifier is needed.
     */
    public final Set<String> ids;


    VerticleRef(final Vertx vertx,
                final Set<String> ids,
                final String address
               ) {
        this.vertx = requireNonNull(vertx);
        this.ids = requireNonNull(ids);
        this.address = requireNonNull(address);
        if (ids.isEmpty()) throw new IllegalArgumentException("ids is empty");
    }

    /**
     Method to establish a conversation with this actor: a message is sent and then a message is
     received.

     @param options the delivery options
     @return a function that takes an object of type I and returns an object of type O wrapped in a
     future
     */
    public λ<I, O> ask(final DeliveryOptions options) {
        requireNonNull(options);
        return body -> Cons.of(() -> vertx.eventBus().<O>request(address,
                                                                 body,
                                                                 options
                                                                ).map(Message::body));
    }

    /**
     Method to establish a conversation with this actor: a message is sent and then a message is
     received.

     @return a function that takes an object of type I and returns an object of type O wrapped in a
     future
     */
    public λ<I, O> ask() {
        return ask(DEFAULT);
    }


    /**
     Method to send a message to this actor.

     @param options the delivery options
     @return a consumer that takes an object of type I
     */
    public Consumer<I> tell(final DeliveryOptions options) {
        requireNonNull(options);
        return body -> vertx.eventBus()
                            .send(address,
                                  body,
                                  options
                                 );
    }

    /**
     Method to send a message to this actor.

     @return a consumer that takes an object of type I
     */
    public Consumer<I> tell() {
        return tell(DEFAULT);
    }

    /**
     Undeploy all the instances of this actor

     @return a future that will be completed when all the instances are undeployed
     */
    @SuppressWarnings({"rawtypes"})
    public Future<Void> undeploy() {

        List<Future> futures = new ArrayList<>();
        for (final String id : ids) {
            final Future<Void> future = vertx.undeploy(id);
            futures.add(future);
        }
        return CompositeFuture.all(futures)
                              .flatMap(it ->
                                       {
                                           if (it.succeeded()) return Future.succeededFuture();
                                           else return Future.failedFuture(it.cause());
                                       });
    }

}
