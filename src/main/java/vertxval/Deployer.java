package vertxval;

import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import vertxval.exp.Cons;
import vertxval.exp.Val;
import vertxval.exp.λ;
import vertxval.functions.Handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.GET_EXECUTING_VERTIClE_EXCEPTION;

/**
 Wrapper around the vertx instance. It registers and spawns verticles. If an address is not provided, one is generated. You only
 need an ActorRef to interact with a verticle. When a verticle is deployed it's waiting for messages
 to be processed. When a verticle is spawn, it processes a message or does some stuff and after that
 it's undeployed automatically.
 */
public class Deployer {
    private static final DeploymentOptions DEFAULT_OPTIONS = new DeploymentOptions();
    private final static AtomicLong processSeq = new AtomicLong(0);
    private final static AtomicLong verticleSeq = new AtomicLong(0);
    private final Vertx vertx;
    private final DeploymentOptions deploymentOptions;

    /**
     Creates a factory to deploy and spawn verticles

     @param vertx the vertx instance
     */
    public Deployer(final Vertx vertx) {

        this(requireNonNull(vertx),
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
    public <I, O> Val<VerticleRef<I, O>> deployConsumer(final Consumer<Message<I>> consumer) {
        requireNonNull(consumer);
        return deployConsumer(generateVerticleAddress(),
                              consumer,
                              DEFAULT_OPTIONS
                             );
    }

    /**
     @param consumer the consumer that will process the messages sent to the verticle
     @param <I>      the type of the message sent to the verticle
     @param options  options for configuring the verticle deployment
     @param <O>      the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Val<VerticleRef<I, O>> deployConsumer(final Consumer<Message<I>> consumer,
                                                        final DeploymentOptions options) {
        requireNonNull(consumer);
        requireNonNull(options);

        return deployConsumer(generateVerticleAddress(),
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
    public <I, O> Val<VerticleRef<I, O>> deployConsumer(final String address,
                                                        final Consumer<Message<I>> consumer
                                                       ) {
        requireNonNull(address);
        requireNonNull(consumer);
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
    public <I, O> Val<VerticleRef<I, O>> deployConsumer(final String address,
                                                        final Consumer<Message<I>> consumer,
                                                        final DeploymentOptions options
                                                       ) {
        requireNonNull(address);
        requireNonNull(consumer);
        requireNonNull(options);
        final int                                        instances = options.getInstances();
        final Set<String>                                ids       = new HashSet<>();
        @SuppressWarnings("rawtypes") final List<Future> futures   = new ArrayList<>();
        final MyVerticle<I> verticle = new MyVerticle<>(consumer,
                                                        address
        );
        for (int i = 0; i < instances; i++) {
            final Future<String> future = vertx.deployVerticle(verticle,
                                                               options.setInstances(1)
                                                              );
            futures.add(future.onSuccess(ids::add));
        }

        return Cons.of(() -> CompositeFuture.all(futures)
                                            .flatMap(cf -> getVerticleRefFuture(address,
                                                                                ids,
                                                                                cf
                                                                               )
                                                    )
                      );
    }

    /**
     @param fn      the function that takes the messages and produces an output
     @param options options for configuring the verticle deployment
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Val<VerticleRef<I, O>> deployFn(final Function<I, O> fn,
                                                  final DeploymentOptions options) {
        requireNonNull(options);
        requireNonNull(fn);
        return deployFn(generateVerticleAddress(),
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
    public <I, O> Val<VerticleRef<I, O>> deployFn(final Function<I, O> fn) {
        requireNonNull(fn);
        return deployFn(generateVerticleAddress(),
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
    public <I, O> Val<VerticleRef<I, O>> deployFn(final String address,
                                                  final Function<I, O> fn
                                                 ) {
        requireNonNull(address);
        requireNonNull(fn);
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
    public <I, O> Val<VerticleRef<I, O>> deployFn(final String address,
                                                  final Function<I, O> fn,
                                                  final DeploymentOptions options
                                                 ) {
        requireNonNull(address);
        requireNonNull(fn);
        requireNonNull(options);
        final int                                        instances = options.getInstances();
        final Set<String>                                ids       = new HashSet<>();
        @SuppressWarnings("rawtypes") final List<Future> futures   = new ArrayList<>();
        final MyVerticle<I> verticle = new MyVerticle<>(m -> m.reply(fn.apply(m.body())),
                                                        address
        );
        for (int i = 0; i < instances; i++) {
            final Future<String> future = vertx.deployVerticle(verticle,
                                                               options.setInstances(1)
                                                              );
            futures.add(future.onSuccess(ids::add));
        }

        return Cons.of(() -> CompositeFuture.all(futures)
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
    public <I, O> Val<VerticleRef<I, O>> deployλ(final String address,
                                                 final λ<I, O> fn
                                                ) {
        requireNonNull(address);
        requireNonNull(fn);
        return deployλ(address,
                       fn,
                       DEFAULT_OPTIONS
                      );
    }

    /**
     @param fn  the function that takes a message of type I and produces an output of type O
     @param <I> the type of the message sent to the verticle
     @param <O> the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Val<VerticleRef<I, O>> deployλ(final λ<I, O> fn
                                                ) {

        requireNonNull(fn);
        return deployλ(generateVerticleAddress(),
                       fn,
                       DEFAULT_OPTIONS
                      );
    }

    /**
     @param fn      the function that takes a message of type I and produces an output of type O
     @param <I>     the type of the message sent to the verticle
     @param options options for configuring the verticle deployment
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> Val<VerticleRef<I, O>> deployλ(final λ<I, O> fn,
                                                 final DeploymentOptions options
                                                ) {
        requireNonNull(options);
        requireNonNull(fn);
        return deployλ(generateVerticleAddress(),
                       fn,
                       options
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
    public <I, O> Val<VerticleRef<I, O>> deployλ(final String address,
                                                 final λ<I, O> fn,
                                                 final DeploymentOptions options
                                                ) {
        requireNonNull(address);
        requireNonNull(options);
        requireNonNull(fn);
        final int                                        instances = options.getInstances();
        final Set<String>                                ids       = new HashSet<>();
        @SuppressWarnings("rawtypes") final List<Future> futures   = new ArrayList<>();
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

        return Cons.of(() -> CompositeFuture.all(futures)
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
        requireNonNull(λ);
        return spawnλ(generateProcessAddress(),
                      λ,
                      DEFAULT_OPTIONS
                     );
    }

    /**
     @param λ       the function that takes a message of type I and produces an output of type O
     @param options options for configuring the verticle deployment
     @param <I>     the type of the message sent to the verticle
     @param <O>     the type of the reply
     @return an ActorRef wrapped in a future
     */
    public <I, O> λ<I, O> spawnλ(final λ<I, O> λ,
                                 final DeploymentOptions options) {
        requireNonNull(options);
        requireNonNull(λ);
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

        requireNonNull(address);
        requireNonNull(options);
        requireNonNull(λ);

        return n ->
        {
            Val<VerticleRef<I, O>> future = deployConsumer(address,
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
        requireNonNull(fn);
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
        requireNonNull(fn);
        requireNonNull(options);
        return n ->
        {
            Consumer<Message<I>> consumer = m -> m.reply(fn.apply(m.body()));

            Val<VerticleRef<I, O>> future = deployConsumer(generateProcessAddress(),
                                                           consumer,
                                                           options
                                                          );
            return future.flatMap(r -> r.ask()
                                        .apply(n)
                                        .onComplete(a -> r.undeploy()));
        };
    }

    public Supplier<Val<String>> spawnTask(final Runnable task) {
        requireNonNull(task);
        return () -> deployTask(task);
    }

    public Supplier<Val<String>> spawnTask(final Runnable task,
                                           final DeploymentOptions options) {
        requireNonNull(task);
        requireNonNull(options);
        return () -> deployTask(task,
                                options
                               );
    }

    public Supplier<Val<String>> spawnVerticle(final AbstractVerticle verticle) {
        requireNonNull(verticle);
        return () -> deployVerticle(verticle,
                                    DEFAULT_OPTIONS
                                   );
    }

    public Supplier<Val<String>> spawnVerticle(final AbstractVerticle verticle,
                                               final DeploymentOptions options) {
        requireNonNull(verticle);
        requireNonNull(options);
        return () -> deployVerticle(verticle,
                                    options
                                   );
    }

    public Val<String> deployVerticle(final AbstractVerticle verticle,
                                      final DeploymentOptions options) {
        requireNonNull(verticle);
        requireNonNull(options);
        return Cons.of(() -> vertx.deployVerticle(verticle,
                                                  options
                                                 ));
    }

    public Val<String> deployVerticle(final AbstractVerticle verticle) {
        requireNonNull(verticle);
        return deployVerticle(verticle,
                              DEFAULT_OPTIONS
                             );
    }

    public Val<String> deployTask(final Runnable task,
                                  final DeploymentOptions options) {
        requireNonNull(options);
        requireNonNull(task);
        return Cons.of(() -> vertx.deployVerticle(new AbstractVerticle() {
                                                      @Override
                                                      public void start(final Promise<Void> promise) {
                                                          try {
                                                              task.run();
                                                              promise.complete();
                                                          } catch (Exception e) {
                                                              promise.fail(e);
                                                          }
                                                      }
                                                  },
                                                  options
                                                 ));
    }


    public Val<String> deployTask(final Runnable task) {
        return deployTask(requireNonNull(task),
                          DEFAULT_OPTIONS
                         );
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
                                             message.reply(GET_EXECUTING_VERTIClE_EXCEPTION.apply(e));
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
        return "__vertx.process." + processSeq.incrementAndGet();
    }

    private static String generateVerticleAddress() {
        return "__vertx.verticle." + verticleSeq.incrementAndGet();
    }


}
