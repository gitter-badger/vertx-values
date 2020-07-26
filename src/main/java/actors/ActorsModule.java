package actors;

import actors.exp.Exp;
import actors.exp.MapExp;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

import java.util.Objects;

import static java.util.Objects.requireNonNull;


/**
 Actor that acts as a module deploying and exposing all the deployed actors.
 */
public abstract class ActorsModule extends AbstractVerticle {

    private static final DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
    protected final DeploymentOptions deploymentOptions;
    private MapExp<ActorRef<?,?>> refExp;
    private Map<String, ActorRef<?, ?>> refMap;


    public ActorsModule(final MapExp<ActorRef<?,?>> refExp,
                        final DeploymentOptions deploymentOptions) {
        this.refExp = requireNonNull(refExp);
        this.deploymentOptions = requireNonNull(deploymentOptions);
    }

    public ActorsModule(final MapExp refExp) {
        this.deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
        this.refExp = requireNonNull(refExp);
    }

    public ActorsModule() {
        this(MapExp.EMPTY);
    }

    /**
     The purpose of this method is to initialize the functions/consumers/suppliers defined in
     public fields of this class that will be exposed.

     @param actorRefs the list of ActorRef wrapped in actorRefs.
     */
    protected abstract void onComplete();

    protected abstract void registerActors();

    /**
     Factory to deploy or spawn actors
     */
    protected Actors actors;

    @Override
    public void start(final Promise<Void> start) {

        try {
            actors = new Actors(requireNonNull(vertx),
                                deploymentOptions
            );
            initModule(actors);

            registerActors();
            if (refExp != null && !refExp.isEmpty()) {
                refExp.onComplete(event -> {
                                      if (event.failed()) start.fail(event.cause());
                                      else {
                                          try {
                                              refMap =  event.result();
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

    protected void initModule(final Actors actors) {
    }


    /**
     @param key the name of the actor //TODO reutilizar para que sea su address!!
     @param <I> the type of the input message
     @param <O> the type of the output message
     @return an ActorRef
     */
    @SuppressWarnings("unchecked")
    protected <I, O> ActorRef<I, O> getRegisteredActor(final String key) {
        return (ActorRef<I, O>) refMap.get(requireNonNull(key))
                                      .get();
    }

    protected <I, O> void registerActor(String key,
                                        Exp<ActorRef<I, O>> exp) {
        refExp = refExp.set(requireNonNull(key),
                            ((Exp) Objects.requireNonNull(exp))
                           );
    }
}
