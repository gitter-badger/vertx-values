package actors.mongo;

import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import org.bson.conversions.Bson;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static actors.mongo.Converters.objVal2Bson;
import static java.util.Objects.requireNonNull;


class ReplaceActors {

    public final DeploymentOptions deploymentOptions;
    public final Actors actors;
    public final Supplier<MongoCollection<JsObj>> collection;

    public ReplaceActors(final Supplier<MongoCollection<JsObj>> collection,
                         final DeploymentOptions deploymentOptions,
                         final Actors actors) {
        this.deploymentOptions = deploymentOptions;
        this.collection = Objects.requireNonNull(collection);
        this.actors = actors;
    }


    public <O> Future<ActorRef<UpdateMessage, O>> deployUpdateOne(final ReplaceInputs inputs,
                                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(inputs);
        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.replaceOne(objVal2Bson.apply(message.filter),
                                                              message.update,
                                                              inputs.options
                                                             )) :
                   resultConverter.apply(collection.replaceOne(inputs.clientSession,
                                                              objVal2Bson.apply(message.filter),
                                                              message.update,
                                                              inputs.options
                                                             ));
        };

        return actors.deploy(updateFn,
                             inputs.deploymentOptions
                            );
    }

    public <O> Future<ActorRef<UpdateMessage, O>> deployReplaceOne(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return resultConverter.apply(collection.replaceOne(objVal2Bson.apply(message.filter),
                                                               message.update
                                                             ));
        };

        return actors.deploy(updateFn,
                             deploymentOptions
                            );
    }

    public <O> Supplier<Future<O>> spawnReplaceOne(final Bson filter,
                                                  final JsObj replacement,
                                                  final ReplaceInputs inputs,
                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(replacement);
        Objects.requireNonNull(inputs);

        Function<JsObj, O> fn = m -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return inputs.clientSession == null ?
                   resultConverter.apply(collection.replaceOne(filter,
                                                              replacement,
                                                              inputs.options
                                                             )) :
                   resultConverter.apply(collection.replaceOne(inputs.clientSession,
                                                              filter,
                                                              replacement,
                                                              inputs.options
                                                             ));
        };

        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  inputs.deploymentOptions
                                                                 );

        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    public <O> Supplier<Future<O>> spawnReplaceOne(final Bson filter,
                                                  final JsObj replacement,
                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(replacement);
        Function<JsObj, O> fn = m -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return resultConverter.apply(collection.replaceOne(filter,
                                                              replacement
                                                             ));
        };

        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  deploymentOptions
                                                                 );

        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnReplaceOne(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> fn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return resultConverter.apply(collection.replaceOne(
                    objVal2Bson.apply(message.filter),
                    message.update
                                                             ));
        };

        return actors.spawn(fn,
                            deploymentOptions
                           );

    }


    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnReplaceOne(final ReplaceInputs inputs,
                                                                           final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(inputs);

        Function<UpdateMessage, O> fn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return inputs.clientSession == null ?
                   resultConverter.apply(collection.replaceOne(objVal2Bson.apply(message.filter),
                                                              message.update,
                                                              inputs.options
                                                             )) :
                   resultConverter.apply(collection.replaceOne(inputs.clientSession,
                                                              objVal2Bson.apply(message.filter),
                                                              message.update,
                                                              inputs.options
                                                             ));
        };

        return actors.spawn(fn,
                            inputs.deploymentOptions
                           );

    }




}
