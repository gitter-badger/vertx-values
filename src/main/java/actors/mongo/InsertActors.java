package actors.mongo;

import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsArray;
import jsonvalues.JsObj;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

class InsertActors {

    private final DeploymentOptions deploymentOptions;
    private final Actors actors;
    private final Supplier<MongoCollection<JsObj>> collection;

    public InsertActors(final Supplier<MongoCollection<JsObj>> collection,
                        final DeploymentOptions deploymentOptions,
                        final Actors actors) {
        this.deploymentOptions = deploymentOptions;
        this.collection = Objects.requireNonNull(collection);
        this.actors = actors;
    }


    public <R> Future<ActorRef<JsObj, R>> deployInsertOne(final InsertOneInputs inputs,
                                                          final Function<InsertOneResult, R> resultConverter) {
        Objects.requireNonNull(inputs);
        Function<JsObj, R> c = m -> {
            if (inputs.clientSession == null)
                return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                    .insertOne(m,
                                                               inputs.options
                                                              ));
            else return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                     .insertOne(inputs.clientSession,
                                                                m,
                                                                inputs.options
                                                               ));
        };
        return actors.deploy(c,
                             inputs.deploymentOptions
                            );
    }

    public <R> Future<ActorRef<JsObj, R>> deployInsertOne(final Function<InsertOneResult, R> resultConverter) {
        Function<JsObj, R> c = m -> resultConverter.apply(Objects.requireNonNull(collection.get())
                                                                 .insertOne(m));
        return actors.deploy(c,
                             deploymentOptions
                            );
    }

    public <R> Supplier<Function<JsObj, Future<R>>> spawnInsertOne(final Function<InsertOneResult, R> resultConverter) {
        Function<JsObj, R> c = m -> resultConverter.apply(Objects.requireNonNull(collection.get())
                                                                 .insertOne(m));
        return actors.spawn(c,
                            deploymentOptions
                           );


    }

    public <R> Supplier<Function<JsObj, Future<R>>> spawnInsertOne(final InsertOneInputs inputs,
                                                                   final Function<InsertOneResult, R> resultConverter) {
        Objects.requireNonNull(inputs);
        Function<JsObj, R> c = m -> {
            if (inputs.clientSession == null)
                return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                    .insertOne(m,
                                                               inputs.options
                                                              ));
            else return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                     .insertOne(inputs.clientSession,
                                                                m,
                                                                inputs.options
                                                               ));
        };
        return actors.spawn(c,
                            inputs.deploymentOptions
                           );
    }

    public <R> Future<ActorRef<JsArray, R>> deployInsertMany(final InsertManyInputs inputs,
                                                             final Function<InsertManyResult, R> resultConverter) {
        Objects.requireNonNull(inputs);
        Function<JsArray, R> c = m -> {
            List<JsObj> docs = Converters.arrayVal2ListOfObjVal.apply(m);
            if (inputs.clientSession == null)
                return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                    .insertMany(docs,
                                                                inputs.options
                                                               ));
            else return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                     .insertMany(inputs.clientSession,
                                                                 docs,
                                                                 inputs.options
                                                                ));
        };
        return actors.deploy(c,
                             inputs.deploymentOptions
                            );
    }

    public <R> Future<ActorRef<JsArray, R>> deployInsertMany(final Function<InsertManyResult, R> resultConverter) {
        Function<JsArray, R> c = m -> {
            List<JsObj> docs = Converters.arrayVal2ListOfObjVal.apply(m);
            return resultConverter.apply(Objects.requireNonNull(collection.get())
                                                .insertMany(docs
                                                           ));

        };
        return actors.deploy(c,
                             deploymentOptions
                            );
    }

    public <R> Supplier<Function<JsArray, Future<R>>> spawnInsertMany(final Function<InsertManyResult, R> resultConverter) {
        return null;
    }

    public <R> Supplier<Future<R>> spawnInsertMany(final Function<InsertManyResult, R> resultConverter,
                                                   final List<JsObj> documents) {
        return null;
    }

    public <R> Supplier<Function<JsArray, Future<R>>> spawnInsertMany(final InsertManyInputs inputs,
                                                                      final Function<InsertManyResult, R> resultConverter) {
        return null;
    }

    public <R> Supplier<Future<R>> spawnInsertMany(final InsertManyInputs inputs,
                                                   final Function<InsertManyResult, R> resultConverter,
                                                   final List<JsObj> documents) {
        return null;
    }
}
