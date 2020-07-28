package vertxval;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import vertxval.exp.Exp;
import vertxval.exp.MapExp;
import vertxval.exp.λ;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;


/**
 Actor that acts as a module deploying and exposing all the deployed actors.
 */
public abstract class Module extends AbstractVerticle {

    private static final DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
    protected final DeploymentOptions deploymentOptions;
    private MapExp<VerticleRef<?, ?>> refExp;
    private Map<String, VerticleRef<?, ?>> refMap;


    public Module(final MapExp<VerticleRef<?, ?>> refExp,
                  final DeploymentOptions deploymentOptions) {
        this.refExp = requireNonNull(refExp);
        this.deploymentOptions = requireNonNull(deploymentOptions);
    }

    public Module(final MapExp refExp) {
        this.deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
        this.refExp = requireNonNull(refExp);
    }

    public Module() {
        this(MapExp.EMPTY);
    }

    /**
     The purpose of this method is to initialize the functions/consumers/suppliers defined in
     public fields of this class that will be exposed.

     @param actorRefs the list of ActorRef wrapped in actorRefs.
     */
    protected abstract void onComplete();

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
            initModule(deployer);

            deploy();
            if (refExp != null && !refExp.isEmpty()) {
                refExp.onComplete(event -> {
                                      if (event.failed())
                                          start.fail(event.cause());
                                      else {
                                          try {
                                              refMap = event.result();
                                              onComplete();
                                              start.complete();
                                          } catch (Exception e) {
                                              start.fail(e);
                                          }
                                      }
                                  }
                                 )
                      .get();
            }
            else {
                refMap = HashMap.empty();
                onComplete();
                start.complete();
            }


        } catch (Exception e) {
            start.fail(e);
        }
    }

    protected void initModule(final Deployer deployer) {
    }


    /**
     @param address the name of the actor //TODO reutilizar para que sea su address!!
     @param <I>     the type of the input message
     @param <O>     the type of the output message
     @return an ActorRef
     */
    @SuppressWarnings("unchecked")
    protected <I, O> VerticleRef<I, O> getDeployedVerticle(final String address) {
        return (VerticleRef<I, O>) refMap.get(requireNonNull(address))
                                         .get();
    }


    protected <I, O> void deployFn(final String address,
                                   final Function<I, O> fn) {

        Exp<VerticleRef<I, O>> exp = deployer.deployFn(requireNonNull(address),
                                                       fn
                                                      );
        refExp = refExp.set(requireNonNull(address),
                            ((Exp) requireNonNull(exp))
                           );
    }

    protected <I, O> void deployConsumer(final String address,
                                         final Consumer<Message<I>> consumer) {

        Exp<VerticleRef<I, O>> exp = deployer.deployConsumer(address,
                                                             consumer,
                                                             DEFAULT_DEPLOYMENT_OPTIONS);
        refExp = refExp.set(requireNonNull(address),
                            ((Exp) requireNonNull(exp))
                           );
    }

    protected <I, O> void deployConsumer(final String address,
                                         final Consumer<Message<I>> consumer,
                                         final DeploymentOptions options) {

        Exp<VerticleRef<I, O>> exp = deployer.deployConsumer(address,
                                                             consumer,
                                                             options);
        refExp = refExp.set(requireNonNull(address),
                            ((Exp) requireNonNull(exp))
                           );
    }

    protected <I, O> void deployFn(final String address,
                                   final Function<I, O> fn,
                                   final DeploymentOptions options) {

        Exp<VerticleRef<I, O>> exp = deployer.deployFn(requireNonNull(address),
                                                       fn,
                                                       options
                                                      );
        refExp = refExp.set(requireNonNull(address),
                            ((Exp) requireNonNull(exp))
                           );
    }


    protected <I, O> void deployλ(final String address,
                                  final λ<I, O> lambda) {
        Exp<VerticleRef<I, O>> exp = deployer.deployλ(requireNonNull(address),
                                                      lambda
                                                     );
        refExp = refExp.set(requireNonNull(address),
                            ((Exp) requireNonNull(exp))
                           );

    }

    protected <I, O> void deployλ(final String address,
                                  final λ<I, O> lambda,
                                  final DeploymentOptions options) {
        Exp<VerticleRef<I, O>> exp = deployer.deployλ(requireNonNull(address),
                                                      lambda,
                                                      options
                                                     );
        refExp = refExp.set(requireNonNull(address),
                            ((Exp) requireNonNull(exp))
                           );

    }
}
