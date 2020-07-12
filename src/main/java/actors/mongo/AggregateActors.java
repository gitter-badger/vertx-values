package actors.mongo;

import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsArray;
import jsonvalues.JsObj;
import org.bson.conversions.Bson;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;


class AggregateActors {

    public final DeploymentOptions deploymentOptions;
    public final Actors actors;
    public final Supplier<MongoCollection<JsObj>> collection;

    public AggregateActors(final Supplier<MongoCollection<JsObj>> collection,
                           final DeploymentOptions deploymentOptions,
                           final Actors actors) {
        this.deploymentOptions = deploymentOptions;
        this.collection = requireNonNull(collection);
        this.actors = actors;
    }

    protected <O> Future<ActorRef<JsArray, O>> deployAggregate(final AggregateInputs inputs,
                    final Function<AggregateIterable<JsObj>, O> resultConverter) {
        requireNonNull(resultConverter);
        requireNonNull(inputs);

        Function<JsArray, O> fn = m ->
        {
            List<Bson> pipeline = Converters.arrayVal2ListOfBsonVal.apply(m);
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.aggregate(pipeline)) :
                   resultConverter.apply(collection.aggregate(inputs.clientSession,
                                                         pipeline
                                                        )
                                        );
        };
        return actors.deploy(fn,
                             inputs.deploymentOptions
                            );
    }

    protected <O> Future<ActorRef<JsArray, O>> deployAggregate(final Function<AggregateIterable<JsObj>, O> resultConverter) {
        requireNonNull(resultConverter);

        Function<JsArray, O> fn = m ->
        {
            List<Bson> pipeline = Converters.arrayVal2ListOfBsonVal.apply(m);
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return
                   resultConverter.apply(collection.aggregate(pipeline));
        };
        return actors.deploy(fn,
                             deploymentOptions
                            );
    }




    protected <O> Supplier<Future<O>> spawnAggregate(final List<? extends Bson> pipeline,
                                                final Function<AggregateIterable<JsObj>, O> resultConverter) {
        requireNonNull(pipeline);
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = m ->
        {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return
                   resultConverter.apply(collection.aggregate(pipeline));
        };
        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  deploymentOptions
                                                                 );
        return () -> spawn.get()
                          .apply(JsObj.empty());

    }

    protected <O> Supplier<Future<O>> spawnAggregate(final AggregateInputs inputs,
                                                final List<? extends Bson> pipeline,
                                                final Function<AggregateIterable<JsObj>, O> resultConverter) {
        requireNonNull(pipeline);
        requireNonNull(inputs);
        requireNonNull(resultConverter);
        Function<JsObj, O> fn = m ->
        {
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.aggregate(pipeline)) :
                   resultConverter.apply(collection.aggregate(inputs.clientSession,
                                                         pipeline
                                                        )
                                        );
        };
        Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,
                                                                  inputs.deploymentOptions
                                                                 );
        return () -> spawn.get()
                          .apply(JsObj.empty());

    }



    protected <O> Supplier<Function<JsArray, Future<O>>> spawnAggregate(
            final Function<AggregateIterable<JsObj>, O> resultConverter) {
        requireNonNull(resultConverter);
        Function<JsArray, O> fn = m ->
        {
            List<Bson> pipeline = Converters.arrayVal2ListOfBsonVal.apply(m);
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return
                   resultConverter.apply(collection.aggregate(pipeline)) ;
        };
        return actors.spawn(fn,
                            deploymentOptions
                           );

    }


    protected <O> Supplier<Function<JsArray, Future<O>>> spawnAggregate(
            final AggregateInputs inputs,
            final Function<AggregateIterable<JsObj>, O> resultConverter) {
        requireNonNull(resultConverter);
        Function<JsArray, O> fn = m ->
        {
            List<Bson> pipeline = Converters.arrayVal2ListOfBsonVal.apply(m);
            MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
            return inputs.clientSession == null ?
                   resultConverter.apply(collection.aggregate(pipeline)) :
                   resultConverter.apply(collection.aggregate(inputs.clientSession,
                                                        pipeline
                                                        )
                                        );
        };
        return actors.spawn(fn,
                            inputs.deploymentOptions
                           );

    }


}
