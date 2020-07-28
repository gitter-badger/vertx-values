package vertxval;

import vertxval.exp.Exp;
import vertxval.exp.IfElse;
import vertxval.exp.JsObjExp;
import vertxval.exp.Val;
import io.vertx.core.Future;
import jsonvalues.JsBool;
import jsonvalues.JsInt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestExp {
    @Test
    public void test() {

        Exp<Boolean> a = IfElse.<Boolean>predicate(() -> Future.succeededFuture(true))
                .consequence(Val.success(false))
                .alternative(Val.success(true));

        IfElse<Integer> b = IfElse.<Integer>predicate(() -> Future.succeededFuture(true))
                .consequence(Val.success(1))
                .alternative(Val.success(2));

        IfElse<Integer> c = IfElse.<Integer>predicate(() -> Future.succeededFuture(true))
                .consequence(Val.success(89))
                .alternative(Val.success(99));

        IfElse<Integer> d = IfElse.<Integer>predicate(a).consequence(b)
                                                        .alternative(c);


        JsObjExp of = JsObjExp.of("a",
                                  a.map(JsBool::of),
                                  "b",
                                  b.map(JsInt::of),
                                  "c",
                                  c.map(JsInt::of),
                                  "d",
                                  d.map(JsInt::of)
                                 );


        jsonvalues.JsObj result = of
                .get()
                .result();

        Assertions.assertEquals(jsonvalues.JsObj.of("a",
                                                    JsBool.FALSE,
                                                    "b",
                                                    JsInt.of(1),
                                                    "c",
                                                    JsInt.of(89),
                                                    "d",
                                                    JsInt.of(89)
                                                   ),
                                result
                               );
    }

}
