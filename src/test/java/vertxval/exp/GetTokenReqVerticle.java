package vertxval.exp;

import jsonvalues.JsObj;

public class GetTokenReqVerticle implements λ<JsObj, String> {

    int count = 0;

    @Override
    public Val<String> apply(final JsObj input) {
        count += 1;

        return Cons.success(count +"");

    }
}
