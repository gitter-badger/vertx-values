package actors;

import actors.errors.InvalidUser;
import actors.exp.And;
import actors.exp.IfElse;
import io.vertx.core.Future;
import jsonvalues.JsObj;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserAccountModule extends ActorsModule
{

  public static Function<Integer,Future<Boolean>> isLegalAge;
  public static Function<String,Future<Boolean>> isValidId;
  public static Function<JsObj,Future<Boolean>> isValidAddress;
  public static Function<String,Future<Boolean>> isValidEmail;
  public static Function<JsObj,Future<JsObj>> register;

  public static Function<JsObj, Supplier<Future<Boolean>>> isValid = obj -> And.of(() -> isLegalAge.apply(obj.getInt("age")),
                                                                                   () -> isValidId.apply(obj.getStr("id")),
                                                                                  () -> isValidAddress.apply(obj.getObj("address")),
                                                                                 () -> isValidEmail.apply(obj.getStr("email"))
                                                                                  );

  public static Function<JsObj,Future<JsObj>> registerIfValid = obj ->
    IfElse.<JsObj>predicate(isValid.apply(obj))
          .consequence(() -> register.apply(obj))
          .alternative(() -> Future.failedFuture(new InvalidUser()))
          .get();


  @Override
  protected void defineActors(final List<Object> futures)
  {
    isLegalAge = this.<Integer,Boolean>toVerticleRef(futures.get(0)).ask();
    isValidId = this.<String,Boolean>toVerticleRef(futures.get(1)).ask();
    isValidAddress = this.<JsObj,Boolean>toVerticleRef(futures.get(2)).ask();
    isValidEmail = this.<String,Boolean>toVerticleRef(futures.get(3)).ask();
    register = this.<JsObj,JsObj>toVerticleRef(futures.get(4)).ask();
  }

  @Override
  protected List<Future> registerActors()
  {
    return Arrays.asList(actors.register(UserAcountFunctions.isLegalAge),
                         actors.register(UserAcountFunctions.isValidId),
                         actors.register(UserAcountFunctions.isValidAddress),
                         actors.register(UserAcountFunctions.isValidEmail),
                         actors.register(UserAcountFunctions.register)
                        );
  }

}
