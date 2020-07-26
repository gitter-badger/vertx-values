package actors;

import actors.exp.Exp;
import actors.exp.Val;
import io.vertx.core.*;
import io.vertx.core.eventbus.Message;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 Wrapper around the vertx instance. It registers and spawns verticles. If an address is not provided, one is generated. You only
 need an ActorRef to interact with a verticle. When a verticle is deployed it's waiting for messages
 to be processed. When a verticle is spawn, it processes a message or does some stuff and after that
 it's undeployed automatically.
 */
public class Actors {
    private static final DeploymentOptions DEFAULT_OPTIONS = new DeploymentOptions();
    private final static AtomicLong processSequence = new AtomicLong(0);
    private final Vertx vertx;
    private final DeploymentOptions deploymentOptions;

    /**
     Creates a factory to deploy and spawn verticles

     @param vertx the vertx instance
     */
    public Actors(final Vertx vertx) {

        this(vertx,
             DEFAULT_OPTIONS
            );

    }

    /**
     Creates a factory to deploy and spawn verticles

     @param vertx             the vertx instance
     @param deploymentOptions the default deployment options that will be used for deploying and spawing
     verticles if one is not provided
     */
    public Actors(final Vertx vertx,
                  final DeploymentOptions deploymentOptions
                 ) {
        this.vertx = requireNonNull(vertx);
        this.deploymentOptions = requireNonNull(deploymentOptions);
    }

    /**
     It deploys a verticle that listen on the given address.

     @param address  the address of the verticle
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final String address,
                                               final Consumer<Message<I>> consumer
                                              ) {
        return register(address,
                        consumer,
                        deploymentOptions
                       );
    }

    /**
     @param address  the address of the verticle
     @param consumer the consumer that will process the messages sent to the verticle
     @param options  options for configuring the verticle deployment
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final String address,
                                               final Consumer<Message<I>> consumer,
                                               final DeploymentOptions options
                                              ) {
        final int          instances = options.getInstances();
        final Set<String>  ids       = new HashSet<>();
        final List<Future> futures   = new ArrayList<>();
        final MyVerticle<I> verticle = new MyVerticle<>(consumer,
                                                        address
        );
        for (int i = 0; i < instances; i++) {
            final Future<String> future = vertx.deployVerticle(verticle,
                                                               options.setInstances(1)
                                                              );
            futures.add(future.onSuccess(ids::add));
        }

        return Val.of(() -> CompositeFuture.all(futures)
                                           .flatMap(cf -> getActorRefFuture(address,
                                                                            ids,
                                                                            cf
                                                                           )
                                                   )
                     );
    }

    private <I, O> Future<ActorRef<I, O>> getActorRefFuture(final String address,
                                                            final Set<String> ids,
                                                            final CompositeFuture cf
                                                           ) {
        if (cf.isComplete()) return Future.succeededFuture(new ActorRef<>(vertx,
                                                                          ids,
                                                                          address
                                                           )
                                                          );
        else return Future.failedFuture(cf.cause());
    }

    /**
     It deploys a verticle

     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final Consumer<Message<I>> consumer) {
        return register(generateProcessAddress(),
                        consumer,
                        deploymentOptions
                       );
    }

    private static String generateProcessAddress() {
        return "__vertx.generated." + processSequence.incrementAndGet();
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param options  options for configuring the verticle deployment
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final Consumer<Message<I>> consumer,
                                               final DeploymentOptions options
                                              ) {
        return register(generateProcessAddress(),
                        consumer,
                        options
                       );
    }

    /**
     @param address the address of the verticle
     @param fn      the function that takes the messages and produces an output
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final String address,
                                               final Function<I, O> fn
                                              ) {
        return register(address,
                        fn,
                        deploymentOptions
                       );
    }

    /**
     @param address the address of the verticle
     @param fn      the function that takes a message of type I and produces an output of type O
     @param options options for configuring the verticle deployment
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final String address,
                                               final Function<I, O> fn,
                                               final DeploymentOptions options
                                              ) {
        final int          instances = options.getInstances();
        final Set<String>  ids       = new HashSet<>();
        final List<Future> futures   = new ArrayList<>();
        final MyVerticle<I> verticle = new MyVerticle<>(m -> m.reply(fn.apply(m.body())),
                                                        address
        );
        for (int i = 0; i < instances; i++) {
            final Future<String> future = vertx.deployVerticle(verticle,
                                                               options.setInstances(1)
                                                              );
            futures.add(future.onSuccess(ids::add));
        }

        return Val.of(() -> CompositeFuture.all(futures)
                                           .flatMap(cf -> getActorRefFuture(
                                                   address,
                                                   ids,
                                                   cf
                                                                           )
                                                   )
                     );
    }

    /**
     @param fn  the function that takes a message of type I and produces an output of type O
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final Function<I, O> fn) {
        return register(generateProcessAddress(),
                        fn,
                        deploymentOptions
                       );
    }

    /**
     @param fn      the function that takes a message of type I and produces an output of type O
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @param options the deployment options
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<ActorRef<I, O>> register(final Function<I, O> fn,
                                               final DeploymentOptions options) {
        return register(generateProcessAddress(),
                        fn,
                        options
                       );
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Actor<I, O> spawn(final Consumer<Message<I>> consumer) {
        return spawn(generateProcessAddress(),
                     consumer,
                     deploymentOptions
                    );
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param options  options for configuring the verticle deployment
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Actor<I, O> spawn(final String address,
                                    final Consumer<Message<I>> consumer,
                                    final DeploymentOptions options
                                   ) {
        return n ->
        {
            Exp<ActorRef<I, O>> future = register(address,
                                                  consumer,
                                                  options
                                                 );

            return future.flatMap(r -> r.ask()
                                        .apply(n)
                                        .onComplete(a -> r.unregister())
                                 );
        };
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Actor<I, O> spawn(final String address,
                                    final Consumer<Message<I>> consumer) {
        return spawn(address,
                     consumer,
                     deploymentOptions
                    );
    }

    /**
     @param fn  the function that takes a message of type I and produces an output of type O
     @param <I> the type of the input message
     @param <O> the type of the output
     @return an ActorRef wrapped in a future
     */
    public <I, O> Actor<I, O> spawn(final Function<I, O> fn) {
        return spawn(generateProcessAddress(),
                     fn,
                     deploymentOptions
                    );
    }

    /**
     @param fn      the function that takes a message of type I and produces an output of type O
     @param options options for configuring the verticle deployment
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Actor<I, O> spawn(final String address,
                                    final Function<I, O> fn,
                                    final DeploymentOptions options
                                   ) {
        return n ->
        {
            Consumer<Message<I>> consumer = m -> m.reply(fn.apply(m.body()));

            Exp<ActorRef<I, O>> future = register(address,
                                                  consumer,
                                                  options
                                                 );
            return future.flatMap(r -> r.ask()
                                        .apply(n)
                                        .onComplete(a -> r.unregister())
                                 );
        };
    }

    /**
     @param fn  the function that takes a message of type I and produces an output of type O
     @param <I> the type of the input message
     @param <O> the type of the output
     @return an ActorRef wrapped in a future
     */
    public <I, O> Actor<I, O> spawn(final String address,
                                    final Function<I, O> fn) {
        return spawn(address,
                     fn,
                     deploymentOptions
                    );
    }

    public Exp<String> register(final AbstractVerticle verticle) {
        return Val.of(() -> vertx.deployVerticle(requireNonNull(verticle)));
    }

    public Exp<String> register(final AbstractVerticle verticle,
                                final DeploymentOptions options) {
        return Val.of(() -> vertx.deployVerticle(requireNonNull(verticle),
                                                 requireNonNull(options)
                                                ));
    }

    //TODO cambiar esto
    protected <O> Future<O> send(final String address,
                                 Object message) {
        return vertx.eventBus().<O>request(address,
                                           message
                                          ).map(Message::body);
    }

    //TODO cambiar esto
    protected void publish(final String address,
                           Object message) {
        vertx.eventBus()
             .publish(address,
                      message
                     );
    }


}
