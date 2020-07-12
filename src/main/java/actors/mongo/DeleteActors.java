package actors.mongo;
import actors.ActorRef;
import actors.Actors;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import org.bson.conversions.Bson;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;


class DeleteActors {

  public final Supplier<MongoCollection<JsObj>> collection;
  public final DeploymentOptions deploymentOptions;
  public final Actors actors;

  public DeleteActors(final Supplier<MongoCollection<JsObj>> collection,
                      final DeploymentOptions deploymentOptions,
                      final Actors actors) {
    this.deploymentOptions = deploymentOptions;
    this.collection = collection;
    this.actors = actors;
  }



  public <O> Future<ActorRef<JsObj, O>> deployDeleteOne(final DeleteInputs inputs,
                                                        final Function<DeleteResult,O> resultConverter){
    requireNonNull(inputs);
    Function<JsObj,O> deleteOne = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return inputs.clientSession == null ?
             resultConverter.apply(collection
                       .deleteOne(Converters.objVal2Bson.apply(filter),
                                  inputs.options)) :
             resultConverter.apply(collection.deleteOne(inputs.clientSession,
                                  Converters.objVal2Bson.apply(filter),
                                  inputs.options));
    };
    return actors.deploy(deleteOne,inputs.deploymentOptions);
  }

  public <O> Future<ActorRef<JsObj, O>> deployDeleteOne(final Function<DeleteResult,O> resultConverter){
    Function<JsObj,O> delete = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return resultConverter.apply(collection.deleteOne(Converters.objVal2Bson.apply(filter)));
    };
    return actors.deploy(delete,deploymentOptions);
  }


  public <O> Supplier<Future<O>> spawnDeleteOne(final Bson filter,
                                                final Function<DeleteResult,O> resultConverter){
    requireNonNull(filter);
    //es irrelevante lo que se envia, el parametro es filter, ver si se puede enviar
    Function<JsObj,O> fn = m -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
      return resultConverter.apply(collection.deleteOne(filter));
    };
    Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,deploymentOptions);
    return () -> spawn.get().apply(JsObj.empty());
  }

  public Supplier<Function<JsObj,Future<DeleteResult>>> spawnDeleteOne(){
    Function<JsObj,DeleteResult> fn = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return collection.deleteOne(Converters.objVal2Bson.apply(filter));
    };
    return actors.spawn(fn,deploymentOptions);
  }

  public <O> Supplier<Function<JsObj,Future<O>>> spawnDeleteOne(final DeleteInputs inputs,
                                                                final Function<DeleteResult,O> resultConverter){
    requireNonNull(inputs);
    Function<JsObj,O> consumer = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return inputs.clientSession == null ?
             resultConverter.apply(collection.deleteOne(Converters.objVal2Bson.apply(filter),
                                  inputs.options)) :
             resultConverter.apply(collection.deleteOne(inputs.clientSession,
                                  Converters.objVal2Bson.apply(filter),
                                  inputs.options));
    };
    return actors.spawn(consumer,inputs.deploymentOptions);
  }

  public <O> Supplier<Future<O>> spawnDeleteOne(final Bson filter,
                                                       final DeleteInputs inputs,
                                                final Function<DeleteResult,O> resultConverter){
    requireNonNull(inputs);
    requireNonNull(filter);
    //es irrelevante lo que se envia, el parametro es filter, ver si se puede enviar
    Function<JsObj,O> consumer = m -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return inputs.clientSession == null ?
             resultConverter.apply(collection.deleteOne(filter,
                                  inputs.options)) :
             resultConverter.apply(collection.deleteOne(inputs.clientSession,
                                  filter,
                                  inputs.options));
    };
    Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(consumer,inputs.deploymentOptions);
    return () -> spawn.get().apply(JsObj.empty());
  }








  public <O> Future<ActorRef<JsObj, O>> deployDeleteMany(final DeleteInputs inputs,
                                                        final Function<DeleteResult,O> resultConverter){
    requireNonNull(inputs);
    Function<JsObj,O> deleteOne = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return inputs.clientSession == null ?
             resultConverter.apply(collection
                                           .deleteMany(Converters.objVal2Bson.apply(filter),
                                                      inputs.options)) :
             resultConverter.apply(collection.deleteMany(inputs.clientSession,
                                                        Converters.objVal2Bson.apply(filter),
                                                        inputs.options));
    };
    return actors.deploy(deleteOne,inputs.deploymentOptions);
  }

  public <O> Future<ActorRef<JsObj, O>> deployDeleteMany(final Function<DeleteResult,O> resultConverter){
    Function<JsObj,O> delete = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return resultConverter.apply(collection.deleteMany(Converters.objVal2Bson.apply(filter)));
    };
    return actors.deploy(delete,deploymentOptions);
  }


  public <O> Supplier<Future<O>> spawnDeleteMany(final Bson filter,
                                                final Function<DeleteResult,O> resultConverter){
    requireNonNull(filter);
    //es irrelevante lo que se envia, el parametro es filter, ver si se puede enviar
    Function<JsObj,O> fn = m -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());
      return resultConverter.apply(collection.deleteMany(filter));
    };
    Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(fn,deploymentOptions);
    return () -> spawn.get().apply(JsObj.empty());
  }

  public Supplier<Function<JsObj,Future<DeleteResult>>> spawnDeleteMany(){
    Function<JsObj,DeleteResult> fn = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return collection.deleteMany(Converters.objVal2Bson.apply(filter));
    };
    return actors.spawn(fn,deploymentOptions);
  }

  public <O> Supplier<Function<JsObj,Future<O>>> spawnDeleteMany(final DeleteInputs inputs,
                                                                final Function<DeleteResult,O> resultConverter){
    requireNonNull(inputs);
    Function<JsObj,O> consumer = filter -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return inputs.clientSession == null ?
             resultConverter.apply(collection.deleteMany(Converters.objVal2Bson.apply(filter),
                                                        inputs.options)) :
             resultConverter.apply(collection.deleteMany(inputs.clientSession,
                                                        Converters.objVal2Bson.apply(filter),
                                                        inputs.options));
    };
    return actors.spawn(consumer,inputs.deploymentOptions);
  }

  public <O> Supplier<Future<O>> spawnDeleteMany(final Bson filter,
                                                final DeleteInputs inputs,
                                                final Function<DeleteResult,O> resultConverter){
    requireNonNull(inputs);
    requireNonNull(filter);
    //es irrelevante lo que se envia, el parametro es filter, ver si se puede enviar
    Function<JsObj,O> consumer = m -> {
      MongoCollection<JsObj> collection = requireNonNull(this.collection.get());

      return inputs.clientSession == null ?
             resultConverter.apply(collection.deleteMany(filter,
                                                        inputs.options)) :
             resultConverter.apply(collection.deleteMany(inputs.clientSession,
                                                        filter,
                                                        inputs.options));
    };
    Supplier<Function<JsObj, Future<O>>> spawn = actors.spawn(consumer,inputs.deploymentOptions);
    return () -> spawn.get().apply(JsObj.empty());
  }
}
