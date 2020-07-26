package actors;

import actors.errors.InvalidUser;
import actors.exp.And;
import actors.exp.IfElse;
import actors.exp.MapExp;
import actors.exp.Val;
import io.vavr.collection.Map;
import jsonvalues.JsObj;

public class UserAccountModule extends ActorsModule {

    public static Actor<Integer, Boolean> isLegalAge;
    public static Actor<String, Boolean> isValidId;
    public static Actor<JsObj, Boolean> isValidAddress;
    public static Actor<String, Boolean> isValidEmail;
    public static Actor<JsObj, JsObj> register;

    public static Actor<JsObj, Boolean> isValid = obj ->
            And.of(isLegalAge.apply(obj.getInt("age")),
                   isValidId.apply(obj.getStr("id")),
                   isValidAddress.apply(obj.getObj("address")),
                   isValidEmail.apply(obj.getStr("email"))
                  );

    public static Actor<JsObj, JsObj> registerIfValid = obj ->
            IfElse.<JsObj>predicate(isValid.apply(obj))
                    .consequence(register.apply(obj))
                    .alternative(Val.of(new InvalidUser()));

    @Override
    protected void onComplete(final Map<String, ActorRef<?, ?>> futures) {
        isLegalAge = this.<Integer, Boolean>getActorRef("isLegalAge").ask();
        isValidId = this.<String, Boolean>getActorRef("isValidId").ask();
        isValidAddress = this.<JsObj, Boolean>getActorRef("isValidAddress").ask();
        isValidEmail = this.<String, Boolean>getActorRef("isValidEmail").ask();
        register = this.<JsObj, JsObj>getActorRef("register").ask();
    }

    @Override
    protected MapExp defineActors() {
        return MapExp.of("isLegalAge",
                         actors.register(UserAcountFunctions.isLegalAge),
                         "isValidId",
                         actors.register(UserAcountFunctions.isValidId),
                         "isValidAddress",
                         actors.register(UserAcountFunctions.isValidAddress),
                         "isValidEmail",
                         actors.register(UserAcountFunctions.isValidEmail),
                         "register",
                         actors.register(UserAcountFunctions.register)
                        );

    }
}
