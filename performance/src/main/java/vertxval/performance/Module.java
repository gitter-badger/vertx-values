package vertxval.performance;

import vertxval.ActorsModule;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import jsonvalues.JsObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


public class Module extends ActorsModule
{

  public static Function<Integer, Future<Integer>> countStringsOneVerticle;
  public static Function<Integer, Future<Integer>> countStringsMultiProcesses;
  public static Function<Integer, Future<Integer>> countStringsMultiVerticles;
  public static Function<JsObj, Future<JsObj>> filter;
  public static Supplier<Function<JsObj, Future<JsObj>>> filterProcess;
  public static Function<Integer,Future<JsObj>> generator;
  public static Supplier<Function<Integer, Future<JsObj>>> generatorProcess;
  public static Function<JsObj, Future<JsObj>> map;
  public static Supplier<Function<JsObj, Future<JsObj>>> mapProcess;
  public static Function<JsObj, Future<Integer>> reduce;
  public static Supplier<Function<JsObj, Future<Integer>>> reduceProcess;
  public static Function<JsObj,Future<JsObj>> id;
  public static Function<String,Future<JsObj>> parser;
  public static Function<JsonObject,Future<JsonObject>> jacksonId;
  public static Function<String,Future<JsonObject>> jacksonParser;

  @Override
  protected void defineActors(final List<Object> list)
  {
    filter = this.<JsObj, JsObj>toActorRef(list.get(0)).ask();

    map = this.<JsObj, JsObj>toActorRef(list.get(1)).ask();

    reduce = this.<JsObj, Integer>toActorRef(list.get(2)).ask();

    generator = this.<Integer, JsObj>toActorRef(list.get(3)).ask();

    countStringsOneVerticle = this.<Integer, Integer>toActorRef(list.get(4)).ask();

    countStringsMultiVerticles = this.<Integer, Integer>toActorRef(list.get(5)).ask();

    countStringsMultiProcesses = this.<Integer, Integer>toActorRef(list.get(6)).ask();

    id = this.<JsObj,JsObj>toActorRef(list.get(7)).ask();

    jacksonId = this.<JsonObject,JsonObject>toActorRef(list.get(8)).ask();

    parser = this.<String,JsObj>toActorRef(list.get(9)).ask();

    jacksonParser = this.<String,JsonObject>toActorRef(list.get(10)).ask();

    filterProcess = vertxval.spawn(Functions.filter);

    mapProcess = vertxval.spawn(Functions.map);

    reduceProcess = vertxval.spawn(Functions.reduce);

    generatorProcess = vertxval.spawn(new JsGenVerticle());

  }


  protected List<Future> deployActors()
  {
    final DeploymentOptions WORKER = new DeploymentOptions().setWorker(true);

    return Arrays.asList(vertxval.deploy(Functions.filter),
                         vertxval.deploy(Functions.map),
                         vertxval.deploy(Functions.reduce),
                         vertxval.deploy(new JsGenVerticle(),
                                         WORKER.setInstances(8)
                                        ),
                         vertxval.deploy(new CountStringOneVerticle(),
                                         WORKER
                                        ),
                         vertxval.deploy(new CountStringMultiVerticle(),
                                         WORKER
                                        ),
                         vertxval.deploy(new CountStringProcesses(),
                                         WORKER
                                        ),
                         vertxval.deploy(Functions.id),

                         vertxval.deploy(Functions.jacksonId),

                         vertxval.deploy(Functions.parser),

                         vertxval.deploy(Functions.jacksonParser)
                        );
  }

  public static void main(String[] args) throws InterruptedException
  {
    Logger LOGGER = LoggerFactory.getLogger(Module.class);

    final Vertx vertx = Vertx.vertx();


    vertx.deployVerticle(new Module(),
                         h ->
                           Module.countStringsMultiVerticles.apply(10)
                                                            .onComplete(r ->
                                                                        {
                                                                          LOGGER.info("Result: {}",
                                                                                      r.result()
                                                                                     );
                                                                        })
                        );

    Thread.sleep(10000);

    LOGGER.info("The end.");


  }


}
