package actors.bankaccount;

import jsonvalues.JsObj;
import jsonvalues.Lens;
import jsonvalues.spec.JsObjSpec;

import static jsonvalues.spec.JsSpecs.integer;
import static jsonvalues.spec.JsSpecs.str;

public class Transaction {

    public static final String FROM_FIELD = "from";
    public static final String TO_FIELD = "to";
    public static final String AMOUNT_FIELD = "amount";

    public static final Lens<JsObj,String> fromLens = JsObj.lens.str(FROM_FIELD);
    public static final Lens<JsObj,String> toLens = JsObj.lens.str(TO_FIELD);
    public static final Lens<JsObj,Integer> amountLens = JsObj.lens.intNum(AMOUNT_FIELD);

    public static final JsObjSpec spec = JsObjSpec.strict(FROM_FIELD,
                                                          str,
                                                          TO_FIELD,
                                                          str,
                                                          AMOUNT_FIELD,
                                                          integer);


}
