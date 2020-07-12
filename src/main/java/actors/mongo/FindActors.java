package actors.mongo;

import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import org.bson.conversions.Bson;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;


class FindActors {

    public final DeploymentOptions deploymentOptions;
    public final Actors actors;
    public final Supplier<MongoCollection<JsObj>> collection;

    public FindActors(final Supplier<MongoCollection<JsObj>> collection,
                      final DeploymentOptions deploymentOptions,
                      final Actors actors) {
        this.deploymentOptions = deploymentOptions;
        this.collection = requireNonNull(collection);
        this.actors = actors;
    }

    protected Future<ActorRef<JsObj, JsObj>> deployFindOneAndDelete(final FindOneAndDeleteInputs inputs) {
        return null;
    }

    protected Future<ActorRef<JsObj, JsObj>> deployFindOneAndUpdate(final FindOneAndUpdateInputs inputs) {
        return null;
    }

    protected Future<ActorRef<JsObj, JsObj>> deployFindOneAndReplace(final FindOneAndReplaceInputs inputs) {
        return null;
    }


    protected Future<ActorRef<JsObj, JsObj>> deployFindOneAndDelete() {
        return null;
    }

    protected Future<ActorRef<JsObj, JsObj>> deployFindOneAndUpdate() {
        return null;
    }

    protected Future<ActorRef<JsObj, JsObj>> deployFindOneAndReplace() {
        return null;
    }


    protected Supplier<Future<JsObj>> spawnFindOneAndDelete(final Bson query) {
        return null;
    }

    protected Supplier<Future<JsObj>> spawnFindOneAndUpdate(final Bson query) {
        return null;
    }

    protected Supplier<Future<JsObj>> spawnFindOneAndReplace(final Bson query) {
        return null;
    }

    protected Supplier<Function<JsObj, Future<JsObj>>> spawnFindOneAndDelete() {
        return null;
    }

    protected Supplier<Function<JsObj, Future<JsObj>>> spawnFindOneAndUpdate() {
        return null;
    }

    protected Supplier<Function<JsObj, Future<JsObj>>> spawnFindOneAndReplace() {
        return null;
    }


    protected Supplier<Future<JsObj>> spawnFindOneAndDelete(final FindOneAndDeleteInputs inputs,
                                                            final Bson query) {
        return null;
    }

    protected Supplier<Future<JsObj>> spawnFindOneAndUpdate(final FindOneAndUpdateInputs inputs,
                                                            final Bson query) {
        return null;
    }

    protected Supplier<Future<JsObj>> spawnFindOneAndReplace(final FindOneAndReplaceInputs inputs,
                                                             final Bson query) {
        return null;
    }

    protected Supplier<Function<JsObj, Future<JsObj>>> spawnFindOneAndDelete(final FindOneAndDeleteInputs inputs) {
        return null;
    }

    protected Supplier<Function<JsObj, Future<JsObj>>> spawnFindOneAndUpdate(final FindOneAndUpdateInputs inputs) {
        return null;
    }

    protected Supplier<Function<JsObj, Future<JsObj>>> spawnFindOneAndReplace(final FindOneAndReplaceInputs inputs) {
        return null;
    }


    protected <O> Future<ActorRef<JsObj, O>> deployFind(final FindInputs inputs,
                                                        final Function<FindIterable<JsObj>, O> resultConverter) {
        requireNonNull(inputs);
        requireNonNull(resultConverter);

        Function<JsObj, O> fn = queryMessage ->
        {
            Bson                   query      = Converters.objVal2Bson.apply(queryMessage);
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.find()) :
                   resultConverter.apply(collection.find(inputs.clientSession,
                                                         query
                                                        )
                                        );
        };
        return actors.deploy(fn,
                             inputs.deploymentOptions
                            );
    }


    protected <O> Future<ActorRef<JsObj, O>> deployFind(final Function<FindIterable<JsObj>, O> resultConverter) {
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = query -> {
            Bson                bson = Converters.objVal2Bson.apply(query);
            FindIterable<JsObj> t    = requireNonNull(collection.get()).find(bson);
            return resultConverter.apply(t);
        };
        return actors.deploy(fn,
                             deploymentOptions
                            );
    }

    protected <O> Supplier<Future<O>> spawnFind(final Bson query,
                                                final FindInputs inputs,
                                                final Function<FindIterable<JsObj>, O> resultConverter) {
        requireNonNull(inputs);
        requireNonNull(query);
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = m ->
        {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.find(query)) :
                   resultConverter.apply(collection.find(inputs.clientSession,
                                                         query
                                                        )
                                        );
        };
        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  inputs.deploymentOptions
                                                                 );
        return () -> spawn.get()
                          .apply(JsObj.empty());

    }

    protected <O> Supplier<Future<O>> spawnFind(final Bson query,
                                                final Function<FindIterable<JsObj>, O> resultConverter) {
        requireNonNull(query);
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = m -> resultConverter.apply(requireNonNull(collection.get()).find(query));
        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  deploymentOptions
                                                                 );
        return () -> spawn.get()
                          .apply(JsObj.empty());
    }

    protected <O> Supplier<Function<JsObj, Future<O>>> spawnFind(
            final FindInputs inputs,
            final Function<FindIterable<JsObj>, O> resultConverter) {
        requireNonNull(inputs);
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = m ->
        {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.find(Converters.objVal2Bson.apply(m))) :
                   resultConverter.apply(collection.find(inputs.clientSession,
                                                         Converters.objVal2Bson.apply(m)
                                                        )
                                        );
        };
        return actors.spawn(fn,
                            inputs.deploymentOptions
                           );

    }

    protected <O> Supplier<Function<JsObj, Future<O>>> spawnFind(final Function<FindIterable<JsObj>, O> resultConverter) {
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = m -> resultConverter.apply(requireNonNull(collection.get())
                                                                   .find(Converters.objVal2Bson.apply(m)));
        return actors.spawn(fn,
                            deploymentOptions
                           );

    }

}
