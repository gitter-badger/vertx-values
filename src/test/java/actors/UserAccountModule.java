package actors;

import actors.errors.InvalidUser;
import actors.exp.And;
import actors.exp.IfElse;
import actors.exp.Val;
import jsonvalues.JsObj;

public class UserAccountModule extends ActorsModule {

    public static Fn<Integer, Boolean> isLegalAge;
    public static Fn<String, Boolean> isValidId;
    public static Fn<JsObj, Boolean> isValidAddress;
    public static Fn<String, Boolean> isValidEmail;
    public static Fn<JsObj, JsObj> register;

    public static Fn<JsObj, Boolean> isValid = obj ->
            And.of(isLegalAge.apply(obj.getInt("age")),
                   isValidId.apply(obj.getStr("id")),
                   isValidAddress.apply(obj.getObj("address")),
                   isValidEmail.apply(obj.getStr("email"))
                  );

    public static Fn<JsObj, JsObj> registerIfValid = obj ->
            IfElse.<JsObj>predicate(isValid.apply(obj))
                    .consequence(register.apply(obj))
                    .alternative(Val.of(new InvalidUser()));

    @Override
    protected void onComplete() {
        isLegalAge = this.<Integer, Boolean>getRegisteredActor("isLegalAge").ask();
        isValidId = this.<String, Boolean>getRegisteredActor("isValidId").ask();
        isValidAddress = this.<JsObj, Boolean>getRegisteredActor("isValidAddress").ask();
        isValidEmail = this.<String, Boolean>getRegisteredActor("isValidEmail").ask();
        register = this.<JsObj, JsObj>getRegisteredActor("register").ask();
    }

    @Override
    protected void registerActors() {
        registerActor("isLegalAge",
                      actors.register(UserAccountFunctions.isLegalAge)
                     );
        registerActor("isValidId",
                      actors.register(UserAccountFunctions.isValidId)
                     );
        registerActor("isValidAddress",
                      actors.register(UserAccountFunctions.isValidAddress)
                     );
        registerActor("isValidEmail",
                      actors.register(UserAccountFunctions.isValidEmail)
                     );
        registerActor("register",
                      actors.register(UserAccountFunctions.register)
                     );

    }
}
