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


class UpdateActors {

    public final DeploymentOptions deploymentOptions;
    public final Actors actors;
    public final Supplier<MongoCollection<JsObj>> collection;

    public UpdateActors(final Supplier<MongoCollection<JsObj>> collection,
                        final DeploymentOptions deploymentOptions,
                        final Actors actors) {
        this.deploymentOptions = deploymentOptions;
        this.collection = Objects.requireNonNull(collection);
        this.actors = actors;
    }


    public <O> Future<ActorRef<UpdateMessage, O>> deployUpdateOne(final UpdateInputs inputs,
                                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(inputs);
        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.updateOne(objVal2Bson.apply(message.filter),
                                                              objVal2Bson.apply(message.update),
                                                              inputs.options
                                                             )) :
                   resultConverter.apply(collection.updateOne(inputs.clientSession,
                                                              objVal2Bson.apply(message.filter),
                                                              objVal2Bson.apply(message.update),
                                                              inputs.options
                                                             ));
        };

        return actors.deploy(updateFn,
                             inputs.deploymentOptions
                            );
    }

    public <O> Future<ActorRef<UpdateMessage, O>> deployUpdateOne(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return resultConverter.apply(collection.updateOne(objVal2Bson.apply(message.filter),
                                                              objVal2Bson.apply(message.update)
                                                             ));
        };

        return actors.deploy(updateFn,
                             deploymentOptions
                            );
    }

    public <O> Supplier<Future<O>> spawnUpdateOne(final Bson filter,
                                                  final Bson update,
                                                  final UpdateInputs inputs,
                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(update);
        Objects.requireNonNull(inputs);

        Function<JsObj, O> fn = m -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return inputs.clientSession == null ?
                   resultConverter.apply(collection.updateOne(filter,
                                                              update,
                                                              inputs.options
                                                             )) :
                   resultConverter.apply(collection.updateOne(inputs.clientSession,
                                                              filter,
                                                              update,
                                                              inputs.options
                                                             ));
        };

        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  inputs.deploymentOptions
                                                                 );

        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    public <O> Supplier<Future<O>> spawnUpdateOne(final Bson filter,
                                                  final Bson update,
                                                  final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(update);
        Function<JsObj, O> fn = m -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return resultConverter.apply(collection.updateOne(filter,
                                                              update
                                                             ));
        };

        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  deploymentOptions
                                                                 );

        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnUpdateOne(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> fn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return resultConverter.apply(collection.updateOne(
                    objVal2Bson.apply(message.filter),
                    objVal2Bson.apply(message.update)
                                                             ));
        };

        return actors.spawn(fn,
                            deploymentOptions
                           );

    }


    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnUpdateOne(final UpdateInputs inputs,
                                                                           final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(inputs);

        Function<UpdateMessage, O> fn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return inputs.clientSession == null ?
                   resultConverter.apply(collection.updateOne(objVal2Bson.apply(message.filter),
                                                              objVal2Bson.apply(message.update),
                                                              inputs.options
                                                             )) :
                   resultConverter.apply(collection.updateOne(inputs.clientSession,
                                                              objVal2Bson.apply(message.filter),
                                                              objVal2Bson.apply(message.update),
                                                              inputs.options
                                                             ));
        };

        return actors.spawn(fn,
                            inputs.deploymentOptions
                           );

    }


    public <O> Future<ActorRef<UpdateMessage, O>> deployUpdateMany(final UpdateInputs inputs,
                                                                   final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(inputs);
        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.updateMany(objVal2Bson.apply(message.filter),
                                                               objVal2Bson.apply(message.update),
                                                               inputs.options
                                                              )) :
                   resultConverter.apply(collection.updateMany(inputs.clientSession,
                                                               objVal2Bson.apply(message.filter),
                                                               objVal2Bson.apply(message.update),
                                                               inputs.options
                                                              ));
        };

        return actors.deploy(updateFn,
                             inputs.deploymentOptions
                            );
    }

    public <O> Future<ActorRef<UpdateMessage, O>> deployUpdateMany(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> updateFn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return resultConverter.apply(collection.updateMany(objVal2Bson.apply(message.filter),
                                                               objVal2Bson.apply(message.update)
                                                              ));
        };

        return actors.deploy(updateFn,
                             deploymentOptions
                            );
    }

    public <O> Supplier<Future<O>> spawnUpdateMany(final Bson filter,
                                                   final Bson update,
                                                   final UpdateInputs inputs,
                                                   final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(update);
        Objects.requireNonNull(inputs);

        Function<JsObj, O> fn = m -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return inputs.clientSession == null ?
                   resultConverter.apply(collection.updateMany(filter,
                                                               update,
                                                               inputs.options
                                                              )) :
                   resultConverter.apply(collection.updateMany(inputs.clientSession,
                                                               filter,
                                                               update,
                                                               inputs.options
                                                              ));
        };

        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  inputs.deploymentOptions
                                                                 );

        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    public <O> Supplier<Future<O>> spawnUpdateMany(final Bson filter,
                                                   final Bson update,
                                                   final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(update);
        Function<JsObj, O> fn = m -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return resultConverter.apply(collection.updateMany(filter,
                                                               update
                                                              )
                                        );
        };

        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  deploymentOptions
                                                                 );

        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnUpdateMany(final Function<UpdateResult, O> resultConverter) {

        Function<UpdateMessage, O> fn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return resultConverter.apply(collection.updateMany(objVal2Bson.apply(message.filter),
                                                               objVal2Bson.apply(message.update)
                                                              )
                                        );
        };

        return actors.spawn(fn,
                            deploymentOptions
                           );

    }


    public <O> Supplier<Function<UpdateMessage, Future<O>>> spawnUpdateMany(final UpdateInputs inputs,
                                                                            final Function<UpdateResult, O> resultConverter) {
        Objects.requireNonNull(inputs);

        Function<UpdateMessage, O> fn = message -> {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

            return inputs.clientSession == null ?
                   resultConverter.apply(collection.updateMany(objVal2Bson.apply(message.filter),
                                                               objVal2Bson.apply(message.update),
                                                               inputs.options
                                                              )) :
                   resultConverter.apply(collection.updateMany(inputs.clientSession,
                                                               objVal2Bson.apply(message.filter),
                                                               objVal2Bson.apply(message.update),
                                                               inputs.options
                                                              ));
        };

        return actors.spawn(fn,
                            inputs.deploymentOptions
                           );

    }

}
