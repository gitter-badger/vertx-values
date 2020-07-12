package actors.mongo;

import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.MongoCollection;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import org.bson.conversions.Bson;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;


class CountActors {

    public final DeploymentOptions deploymentOptions;
    public final Actors actors;
    public final Supplier<MongoCollection<JsObj>> collection;

    public CountActors(final Supplier<MongoCollection<JsObj>> collection,
                       final DeploymentOptions deploymentOptions,
                       final Actors actors) {
        this.deploymentOptions = deploymentOptions;
        this.collection = requireNonNull(collection);
        this.actors = actors;
    }



    protected  Future<ActorRef<JsObj, Long>> deployCount(final CountInputs inputs) {
        requireNonNull(inputs);

        Function<JsObj, Long> fn = queryMessage ->
        {
            Bson                   query      = Converters.objVal2Bson.apply(queryMessage);
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   collection.countDocuments() :
                   collection.countDocuments(inputs.clientSession,
                                                         query
                                        );
        };
        return actors.deploy(fn,
                             inputs.deploymentOptions
                            );
    }


    protected  Future<ActorRef<JsObj, Long>> deployCount() {
        Function<JsObj, Long> fn = query -> {
            Bson                bson = Converters.objVal2Bson.apply(query);
            return requireNonNull(collection.get()).countDocuments(bson);

        };
        return actors.deploy(fn,
                             deploymentOptions
                            );
    }

    protected  Supplier<Future<Long>> spawnCount(final Bson query,
                                                final CountInputs inputs) {
        requireNonNull(inputs);
        requireNonNull(query);
        Function<JsObj, Long> fn = m ->
        {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   collection.countDocuments(query) :
                   collection.countDocuments(inputs.clientSession,
                                   query
                                        );
        };
        Supplier<Function<JsObj, Future<Long>>> spawn = actors.spawn(fn,
                                                                  inputs.deploymentOptions
                                                                 );
        return () -> spawn.get()
                          .apply(JsObj.empty());

    }

    protected  Supplier<Future<Long>> spawnCount(final Bson query) {
        requireNonNull(query);
        Function<JsObj, Long> fn = m -> requireNonNull(collection.get()).countDocuments(query);
        Supplier<Function<JsObj, Future<Long>>> spawn = actors.spawn(fn,
                                                                  deploymentOptions
                                                                 );
        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    protected Supplier<Function<JsObj, Future<Long>>> spawnCount(final CountInputs inputs) {
        requireNonNull(inputs);
        Function<JsObj, Long> fn = m ->
        {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   collection.countDocuments(Converters.objVal2Bson.apply(m)) :
                   collection.countDocuments(inputs.clientSession,
                                             Converters.objVal2Bson.apply(m)
                                        );
        };
        return actors.spawn(fn,
                            inputs.deploymentOptions
                           );

    }

    protected  Supplier<Function<JsObj, Future<Long>>> spawnCount() {
        Function<JsObj, Long> fn = m -> requireNonNull(collection.get()).countDocuments(Converters.objVal2Bson.apply(m));
        return actors.spawn(fn,
                            deploymentOptions
                           );

    }

}
