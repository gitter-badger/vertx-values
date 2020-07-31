package vertxval.functions;

import jsonvalues.JsArray;
import jsonvalues.JsObj;
import jsonvalues.spec.JsArraySpec;
import jsonvalues.spec.JsErrorPair;
import jsonvalues.spec.JsObjSpec;
import vertxval.exp.Cons;
import vertxval.exp.λ;
import java.util.Set;
import java.util.function.Predicate;

import static vertxval.VertxValException.GET_BAD_MESSAGE_EXCEPTION;

public class Validators {

    public static λ<JsObj,JsObj> validateJsObj(final JsObjSpec spec){
        return obj -> {
            Set<JsErrorPair> errors = spec.test(obj);
            if (errors.isEmpty()) return Cons.success(obj);
            else return Cons.failure(GET_BAD_MESSAGE_EXCEPTION.apply(errors.toString()));
        };
    }

    public static λ<JsArray,JsArray> validateJsArray(final JsArraySpec spec){
        return arr -> {
            Set<JsErrorPair> errors = spec.test(arr);
            if (errors.isEmpty()) return Cons.success(arr);
            else return Cons.failure(GET_BAD_MESSAGE_EXCEPTION.apply(errors.toString()));
        };
    }

    public static <I> λ<I,I> validate(final Predicate<I> predicate,
                                      final String errorMessage){
        return message -> {
            if (predicate.test(message)) return Cons.success(message);
            else return Cons.failure(GET_BAD_MESSAGE_EXCEPTION.apply(errorMessage));
        };
    }


}
