package vertxval;

import jsonvalues.JsObj;
import vertxval.errors.InvalidUser;
import vertxval.exp.And;
import vertxval.exp.IfElse;
import vertxval.exp.Val;
import vertxval.exp.λ;

public class UserAccountModule extends Module {

    public static λ<Integer, Boolean> isLegalAge;
    public static λ<String, Boolean> isValidId;
    public static λ<JsObj, Boolean> isValidAddress;
    public static λ<String, Boolean> isValidEmail;
    public static λ<JsObj, JsObj> register;

    public static λ<JsObj, Boolean> isValid = obj ->
            And.of(isLegalAge.apply(obj.getInt("age")),
                   isValidId.apply(obj.getStr("id")),
                   isValidAddress.apply(obj.getObj("address")),
                   isValidEmail.apply(obj.getStr("email"))
                  );

    public static λ<JsObj, JsObj> registerIfValid = obj ->
            IfElse.<JsObj>predicate(isValid.apply(obj))
                    .consequence(register.apply(obj))
                    .alternative(Val.failure(new InvalidUser()));

    @Override
    protected void onComplete() {
        isLegalAge = this.<Integer, Boolean>getDeployedVerticle("isLegalAge").ask();
        isValidId = this.<String, Boolean>getDeployedVerticle("isValidId").ask();
        isValidAddress = this.<JsObj, Boolean>getDeployedVerticle("isValidAddress").ask();
        isValidEmail = this.<String, Boolean>getDeployedVerticle("isValidEmail").ask();
        register = this.<JsObj, JsObj>getDeployedVerticle("register").ask();
    }

    @Override
    protected void deploy() {
        this.deployFn("isLegalAge",
                      UserAccountFunctions.isLegalAge
                     );
        this.deployFn("isValidId",
                      UserAccountFunctions.isValidId
                     );
        this.deployFn("isValidAddress",
                      UserAccountFunctions.isValidAddress
                     );
        this.deployFn("isValidEmail",
                      UserAccountFunctions.isValidEmail
                     );
        this.deployFn("register",
                      UserAccountFunctions.register
                     );
    }
}
