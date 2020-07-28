package vertxval;

import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import vertxval.exp.Exp;
import vertxval.exp.Val;
import vertxval.exp.λ;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static vertxval.Exceptions.GET_ERROR_HANDLING_MESSAGE_IN_CONSUMER_EXCEPTION;

/**
 Wrapper around the vertx instance. It registers and spawns verticles. If an address is not provided, one is generated. You only
 need an ActorRef to interact with a verticle. When a verticle is deployed it's waiting for messages
 to be processed. When a verticle is spawn, it processes a message or does some stuff and after that
 it's undeployed automatically.
 */
public class Deployer {
    private static final DeploymentOptions DEFAULT_OPTIONS = new DeploymentOptions();
    private final static AtomicLong processSequence = new AtomicLong(0);
    private final Vertx vertx;
    private final DeploymentOptions deploymentOptions;

    /**
     Creates a factory to deploy and spawn verticles

     @param vertx the vertx instance
     */
    public Deployer(final Vertx vertx) {

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
    public Deployer(final Vertx vertx,
                    final DeploymentOptions deploymentOptions
                   ) {
        this.vertx = requireNonNull(vertx);
        this.deploymentOptions = requireNonNull(deploymentOptions);
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployConsumer(final Consumer<Message<I>> consumer) {

        return deployConsumer(generateProcessAddress(),
                              consumer,
                              DEFAULT_OPTIONS
                             );
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployConsumer(final Consumer<Message<I>> consumer,
                                                        final DeploymentOptions options) {

        return deployConsumer(generateProcessAddress(),
                              consumer,
                              options
                             );
    }

    /**
     @param address  the address of the verticle
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployConsumer(final String address,
                                                        final Consumer<Message<I>> consumer
                                                       ) {

        return deployConsumer(address,
                              consumer,
                              DEFAULT_OPTIONS
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
    public <I, O> Exp<VerticleRef<I, O>> deployConsumer(final String address,
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

        return Val.success(() -> CompositeFuture.all(futures)
                                                .flatMap(cf -> getVerticleRefFuture(address,
                                                                                    ids,
                                                                                    cf
                                                                                   )
                                                        )
                          );
    }

    /**
     @param fn  the function that takes the messages and produces an output
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployFn(final Function<I, O> fn,
                                                  final DeploymentOptions options) {
        return deployFn(generateProcessAddress(),
                        fn,
                        options
                       );
    }

    /**
     @param fn  the function that takes the messages and produces an output
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployFn(final Function<I, O> fn) {
        return deployFn(generateProcessAddress(),
                        fn,
                        deploymentOptions
                       );
    }

    /**
     @param address the address of the verticle
     @param fn      the function that takes the messages and produces an output
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployFn(final String address,
                                                  final Function<I, O> fn
                                                 ) {
        return deployFn(address,
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
    public <I, O> Exp<VerticleRef<I, O>> deployFn(final String address,
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

        return Val.success(() -> CompositeFuture.all(futures)
                                                .flatMap(cf -> getVerticleRefFuture(
                                                        address,
                                                        ids,
                                                        cf
                                                                                   )
                                                        )
                          );
    }

    /**
     @param address the address of the verticle
     @param fn      the function that takes a message of type I and produces an output of type O
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Exp<VerticleRef<I, O>> deployλ(final String address,
                                                 final λ<I, O> fn
                                                ) {
        return deployλ(address,
                       fn,
                       DEFAULT_OPTIONS
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
    public <I, O> Exp<VerticleRef<I, O>> deployλ(final String address,
                                                 final λ<I, O> fn,
                                                 final DeploymentOptions options
                                                ) {
        final int          instances = options.getInstances();
        final Set<String>  ids       = new HashSet<>();
        final List<Future> futures   = new ArrayList<>();
        final MyVerticle<I> verticle = new MyVerticle<>(message -> fn.apply(message.body())
                                                                     .onComplete(Handlers.pipeTo(message))
                                                                     .get(),
                                                        address
        );
        for (int i = 0; i < instances; i++) {
            final Future<String> future = vertx.deployVerticle(verticle,
                                                               options.setInstances(1)
                                                              );
            futures.add(future.onSuccess(ids::add));
        }

        return Val.success(() -> CompositeFuture.all(futures)
                                                .flatMap(cf -> getVerticleRefFuture(address,
                                                                                    ids,
                                                                                    cf
                                                                                   )
                                                        )
                          );
    }


    /**
     @param λ   the function that takes a message of type I and produces an output of type O
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> λ<I, O> spawnλ(final λ<I, O> λ) {
        return spawnλ(generateProcessAddress(),
                      λ,
                      DEFAULT_OPTIONS
                     );
    }

    /**
     @param λ   the function that takes a message of type I and produces an output of type O
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> λ<I, O> spawnλ(final λ<I, O> λ,
                                 final DeploymentOptions options) {
        return spawnλ(generateProcessAddress(),
                      λ,
                      options
                     );
    }

    /**
     @param λ   the function that takes a message of type I and produces an output of type O
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    private <I, O> λ<I, O> spawnλ(final String address,
                                  final λ<I, O> λ,
                                  final DeploymentOptions options) {


        return n ->
        {
            Exp<VerticleRef<I, O>> future = deployConsumer(address,
                                                           message -> λ.apply(message.body())
                                                                       .onComplete(Handlers.pipeTo(message))
                                                                       .get(),
                                                           options
                                                          );

            return future.flatMap(r -> r.ask()
                                        .apply(n)
                                        .onComplete(a -> r.undeploy()));
        };


    }


    /**
     @param fn  the function that takes a message of type I and produces an output of type O
     @param <I> the type of the input message
     @param <O> the type of the output
     @return an ActorRef wrapped in a future
     */
    public <I, O> λ<I, O> spawnFn(final Function<I, O> fn) {
        return spawnFn(fn,
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
    public <I, O> λ<I, O> spawnFn(final Function<I, O> fn,
                                  final DeploymentOptions options
                                 ) {
        return n ->
        {
            Consumer<Message<I>> consumer = m -> m.reply(fn.apply(m.body()));

            Exp<VerticleRef<I, O>> future = deployConsumer(generateProcessAddress(),
                                                           consumer,
                                                           options
                                                          );
            return future.flatMap(r -> r.ask()
                                        .apply(n)
                                        .onComplete(a -> r.undeploy()));
        };
    }


    public Exp<String> deployVerticle(final AbstractVerticle verticle) {
        requireNonNull(verticle);
        return Val.success(() -> vertx.deployVerticle(verticle));
    }


    protected <O> Consumer<O> registerPublisher(final String address) {
        requireNonNull(address);
        return message -> vertx.eventBus()
                               .publish(address,
                                        message
                                       );
    }

    protected <O> void registerConsumer(final String address,
                                        final Consumer<O> consumer) {
        requireNonNull(address);
        requireNonNull(consumer);
        vertx.eventBus().<O>consumer(address,
                                     message -> {
                                         try {
                                             O body = message.body();
                                             consumer.accept(body);
                                         } catch (Throwable e) {
                                             message.reply(GET_ERROR_HANDLING_MESSAGE_IN_CONSUMER_EXCEPTION.apply(e));
                                         }
                                     }
                                    );
    }

    private <I, O> Future<VerticleRef<I, O>> getVerticleRefFuture(final String address,
                                                                  final Set<String> ids,
                                                                  final CompositeFuture cf
                                                                 ) {
        if (cf.isComplete()) return Future.succeededFuture(new VerticleRef<>(vertx,
                                                                             ids,
                                                                             address
                                                           )
                                                          );
        else return Future.failedFuture(cf.cause());
    }


    private static String generateProcessAddress() {
        return "__vertx.generated." + processSequence.incrementAndGet();
    }


}
