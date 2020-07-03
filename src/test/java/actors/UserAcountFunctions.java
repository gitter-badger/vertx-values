package actors;

import jsonvalues.JsInt;
import jsonvalues.JsObj;

import java.util.Random;
import java.util.function.Function;

public class UserAcountFunctions
{
  private static Random random = new Random();

  public static Function<Integer, Boolean> isLegalAge = age -> age > 16;
  public static Function<String, Boolean> isValidId = id -> !id.isEmpty();
  public static Function<JsObj, Boolean> isValidAddress = address -> !address.getStr("city").isEmpty();
  public static Function<String, Boolean> isValidEmail = email -> !email.isEmpty();
  public static Function<JsObj, JsObj> register = user -> user.set("id",
                                                                   JsInt.of(random.nextInt())
                                                                  );



}
