package vertxval.exp;

import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsBool;
import jsonvalues.JsInt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestVal {
    @Test
    public void test() {

        Val<Boolean> a = IfElse.<Boolean>predicate(() -> Future.succeededFuture(true))
                .consequence(Cons.success(false))
                .alternative(Cons.success(true));

        IfElse<Integer> b = IfElse.<Integer>predicate(() -> Future.succeededFuture(true))
                .consequence(Cons.success(1))
                .alternative(Cons.success(2));

        IfElse<Integer> c = IfElse.<Integer>predicate(() -> Future.succeededFuture(true))
                .consequence(Cons.success(89))
                .alternative(Cons.success(99));

        IfElse<Integer> d = IfElse.<Integer>predicate(a).consequence(b)
                                                        .alternative(c);


        JsObjVal of = JsObjVal.of("a",
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
