package actors.mongo;

import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsObj;

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


    public <O> Future<ActorRef<UpdateMessage, O>> deployUpdateOne(final ReplaceOptions options,
                                                                  final DeploymentOptions deploymentOptions,
                                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(options);
        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return
                   resultConverter.apply(collection.replaceOne(objVal2Bson.apply(message.filter),
                                                              message.update,
                                                              options
                                                             )) ;
        };

        return actors.deploy(updateFn,
                             deploymentOptions
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


    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnUpdateOne(final ReplaceOptions options,
                                                                           final DeploymentOptions deploymentOptions,
                                                                           final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(options);
        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return
                    resultConverter.apply(collection.replaceOne(objVal2Bson.apply(message.filter),
                                                                message.update,
                                                                options
                                                               )) ;
        };

        return actors.spawn(updateFn,
                             deploymentOptions
                            );
    }

    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnReplaceOne(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return resultConverter.apply(collection.replaceOne(objVal2Bson.apply(message.filter),
                                                               message.update
                                                              ));
        };

        return actors.spawn(updateFn,
                             deploymentOptions
                            );
    }



}
