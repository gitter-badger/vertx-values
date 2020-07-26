package actors;

import actors.exp.MapExp;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

import java.util.Objects;


/**
 Actor that acts as a module deploying and exposing all the deployed actors.
 */
public abstract class ActorsModule extends AbstractVerticle {

    private static final DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
    protected final DeploymentOptions deploymentOptions;
    private MapExp refExp;
    private Map<String, ActorRef<?, ?>> refMap;


    public ActorsModule(final MapExp refExp,
                        final DeploymentOptions deploymentOptions) {
        this.refExp = Objects.requireNonNull(refExp);
        this.deploymentOptions = Objects.requireNonNull(deploymentOptions);
    }

    public ActorsModule(final MapExp refExp) {
        this.deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
        this.refExp = Objects.requireNonNull(refExp);
    }

    public ActorsModule() {
        this(MapExp.EMPTY);
    }

    /**
     The purpose of this method is to initialize the functions/consumers/suppliers defined in
     public fields of this class that will be exposed.

     @param futures the list of ActorRef wrapped in futures.
     */
    protected abstract void onComplete(final Map<String, ActorRef<?, ?>> futures);

    protected abstract MapExp defineActors();

    /**
     Factory to deploy or spawn actors
     */
    protected Actors actors;

    @Override
    public void start(final Promise<Void> start) {

        try {
            actors = new Actors(Objects.requireNonNull(vertx),
                                deploymentOptions
            );
            initModule(actors);

            refExp = defineActors();
            if (refExp != null && !refExp.isEmpty()) {
                refExp.onComplete(event -> {
                                      if (event.failed()) start.fail(event.cause());
                                      else {
                                          try {
                                              refMap = (Map<String, ActorRef<?, ?>>) event.result();
                                              onComplete(refMap);
                                              start.complete();
                                          } catch (Exception e) {
                                              start.fail(e);
                                          }
                                      }
                                  }
                                 ).get();
            }
            else {
                refMap = HashMap.empty();
                onComplete(refMap);
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
    //It's responsibility of the caller to make sure the object has the correct type
    protected <I, O> ActorRef<I, O> getActorRef(final String key) {
        return (ActorRef<I, O>) refMap.get(Objects.requireNonNull(key)).get();
    }

}
