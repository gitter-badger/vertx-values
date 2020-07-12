package actors.mongo;
import actors.Actors;
import actors.ActorsModule;
import com.mongodb.client.MongoCollection;
import io.vertx.core.DeploymentOptions;
import jsonvalues.JsObj;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class MongoModule extends ActorsModule {
    public static final DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions().setWorker(true);

    public final Supplier<MongoCollection<JsObj>> collection;
    protected InsertActors insertActors;
    protected FindActors findActors;
    protected UpdateActors updateActors;
    protected DeleteActors deleteActors;
    protected ReplaceActors replaceActors;
    protected WatcherActors watcherActors;
    protected CountActors countActors;
    protected AggregateActors aggregateActors;

    public MongoModule(final DeploymentOptions deploymentOptions,
                       final Supplier<MongoCollection<JsObj>> collection) {
        super(deploymentOptions);
        this.collection = Objects.requireNonNull(collection);
    }

    public MongoModule(final Supplier<MongoCollection<JsObj>> collection) {
        super(DEFAULT_DEPLOYMENT_OPTIONS);
        this.collection = Objects.requireNonNull(collection);
    }


    @Override
    protected void initModule(final Actors actors) {
        Objects.requireNonNull(actors);
        insertActors = new InsertActors(collection,deploymentOptions,actors);
        findActors = new FindActors(collection,deploymentOptions,actors);
        updateActors = new UpdateActors(collection,deploymentOptions,actors);
        deleteActors = new DeleteActors(collection,deploymentOptions,actors);
        replaceActors = new ReplaceActors(collection,deploymentOptions,actors);
        watcherActors = new WatcherActors(collection, deploymentOptions, actors);
        countActors = new CountActors(collection,deploymentOptions,actors);
        aggregateActors = new AggregateActors(collection,deploymentOptions,actors);
    }
}
