package vertxval;

import io.vavr.collection.Map;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import vertxval.exp.*;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.GET_ERROR_DEPLOYING_MODULE_CODE_EXCEPTION;


/**
 Actor that acts as a module deploying and exposing all the deployed actors.
 */
public abstract class VertxModule extends AbstractVerticle {

    private static final DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
    protected final DeploymentOptions deploymentOptions;
    private SeqVal<String> idExpList;
    private MapVal<VerticleRef<?, ?>> refExpMap;
    private Map<String, VerticleRef<?, ?>> refMap;


    @SuppressWarnings({"rawtypes","unchecked"})
    private VertxModule(final MapVal refExp,
                        final DeploymentOptions deploymentOptions) {
        this.refExpMap = requireNonNull(refExp);
        this.deploymentOptions = requireNonNull(deploymentOptions);
        idExpList = SeqVal.empty();
    }


    public VertxModule(final DeploymentOptions options) {
        this(MapVal.EMPTY,
             options
            );
        idExpList = SeqVal.empty();
    }

    public VertxModule() {
        this(MapVal.EMPTY,
             DEFAULT_DEPLOYMENT_OPTIONS
            );
        idExpList = SeqVal.empty();

    }

    /**
     The purpose of this method is to initialize the functions/consumers/suppliers defined in
     public fields of this class that will be exposed.
     */
    protected abstract void define();

    protected abstract void deploy();

    /**
     Factory to deploy or spawn actors
     */
    protected Deployer deployer;

    @Override
    public void start(final Promise<Void> start) {

        try {
            deployer = new Deployer(requireNonNull(vertx),
                                    deploymentOptions
            );
            deploy();
            Pair.of(idExpList,
                    refExpMap
                   )
                .onComplete(event -> {
                    if (event.failed())
                        start.fail(event.cause());
                    else {
                        try {
                            refMap = event.result()
                                          ._2();
                            define();
                            start.complete();
                        } catch (Exception e) {
                          start.fail(GET_ERROR_DEPLOYING_MODULE_CODE_EXCEPTION.apply(e));
                        }
                    }
                })
                .get();

        } catch (Exception e) {
            start.fail(GET_ERROR_DEPLOYING_MODULE_CODE_EXCEPTION.apply(e));
        }
    }


    /**
     @param address the name of the actor
     @param <I>     the type of the input message
     @param <O>     the type of the output message
     @return an ActorRef
     */
    @SuppressWarnings("unchecked")
    protected <I, O> VerticleRef<I, O> getDeployedVerticle(final String address) {
        return (VerticleRef<I, O>) refMap.get(requireNonNull(address))
                                         .get();
    }

    @SuppressWarnings("unchecked")
    public void deployTask(final Runnable runnable) {
        idExpList = idExpList.append(deployer.deployTask(requireNonNull(runnable)));
    }

    @SuppressWarnings("unchecked")
    public void deployTask(final Runnable runnable,final DeploymentOptions options) {
        idExpList = idExpList.append(deployer.deployTask(requireNonNull(runnable),options));
    }

    @SuppressWarnings("unchecked")
    public void deployVerticle(final AbstractVerticle verticle) {
        idExpList = idExpList.append(deployer.deployVerticle(requireNonNull(verticle)));
    }

    @SuppressWarnings("unchecked")
    protected <I, O> void deployFn(final String address,
                                   final Function<I, O> fn) {

        Val<VerticleRef<I, O>> exp = deployer.deployFn(requireNonNull(address),
                                                       requireNonNull(fn)
                                                      );
        refExpMap = refExpMap.set(requireNonNull(address),
                                  ((Val) requireNonNull(exp))
                                 );
    }

    @SuppressWarnings("unchecked")

    protected <I, O> void deployConsumer(final String address,
                                         final Consumer<Message<I>> consumer) {

        Val<VerticleRef<I, O>> exp = deployer.deployConsumer(requireNonNull(address),
                                                             requireNonNull(consumer),
                                                             DEFAULT_DEPLOYMENT_OPTIONS
                                                            );
        refExpMap = refExpMap.set(address,
                                  ((Val) requireNonNull(exp))
                                 );
    }

    @SuppressWarnings("unchecked")

    protected <I, O> void deployConsumer(final String address,
                                         final Consumer<Message<I>> consumer,
                                         final DeploymentOptions options) {

        Val<VerticleRef<I, O>> exp = deployer.deployConsumer(requireNonNull(address),
                                                             requireNonNull(consumer),
                                                             requireNonNull(options)
                                                            );
        refExpMap = refExpMap.set(address,
                                  ((Val) exp)
                                 );
    }

    @SuppressWarnings("unchecked")

    protected <I, O> void deployFn(final String address,
                                   final Function<I, O> fn,
                                   final DeploymentOptions options) {

        Val<VerticleRef<I, O>> exp = deployer.deployFn(requireNonNull(address),
                                                       requireNonNull(fn),
                                                       requireNonNull(options)
                                                      );
        refExpMap = refExpMap.set(address,
                                  ((Val) exp)
                                 );
    }

    @SuppressWarnings("unchecked")
    protected <I, O> void deployλ(final String address,
                                  final λ<I, O> lambda) {
        Val<VerticleRef<I, O>> exp = deployer.deployλ(requireNonNull(address),
                                                      lambda
                                                     );

                refExpMap = refExpMap.set(requireNonNull(address),
                                  ((Val) exp)
                                 );

    }
    @SuppressWarnings("unchecked")
    protected <I, O> void deployλ(final String address,
                                  final λ<I, O> lambda,
                                  final DeploymentOptions options) {
        Val<VerticleRef<I, O>> exp = deployer.deployλ(requireNonNull(address),
                                                      lambda,
                                                      options
                                                     );
        refExpMap = refExpMap.set(requireNonNull(address),
                                  ((Val) exp)
                                 );

    }
}
