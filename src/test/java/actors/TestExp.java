package actors;

import actors.expresions.Exp;
import actors.expresions.IfElse;
import actors.expresions.JsObjExp;
import io.vertx.core.Future;
import jsonvalues.JsBool;
import jsonvalues.JsInt;
import jsonvalues.JsObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestExp {
    @Test
    public void test() {

        Exp<Boolean> a = IfElse.<Boolean>predicate(() -> Future.succeededFuture(true))
                .consequence(() -> Future.succeededFuture(false))
                .alternative(() -> Future.succeededFuture(true));

        IfElse<Integer> b = IfElse.<Integer>predicate(() -> Future.succeededFuture(true))
                .consequence(() -> Future.succeededFuture(1))
                .alternative(() -> Future.succeededFuture(2));

        IfElse<Integer> c = IfElse.<Integer>predicate(() -> Future.succeededFuture(true))
                .consequence(() -> Future.succeededFuture(89))
                .alternative(() -> Future.succeededFuture(99));

        IfElse<Integer> d = IfElse.<Integer>predicate(a).consequence(b)
                                                        .alternative(c);


        JsObj result = JsObjExp.of("a",
                                   a.map(JsBool::of),
                                   "b",
                                   b.map(JsInt::of),
                                   "c",
                                   c.map(JsInt::of),
                                   "d",
                                   d.map(JsInt::of)
                                  )
                               .get()
                               .result();
        System.out.println(result);
        Assertions.assertEquals(JsObj.of("a",
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
