package actors;

import jsonvalues.JsValue;
import jsonvalues.gen.JsGen;
import org.junit.jupiter.api.Assertions;

import java.util.Random;
import java.util.function.Predicate;

public class TestProperty {
    /**
     @param gen       generator to produce randomized input data
     @param condition the property to be tested
     @param times     number of times an input is produced and tested on the property
     */
    public static void test(JsGen<?> gen,
                            Predicate<JsValue> condition,
                            int times
                           ) {
        for (int i = 0; i < times; i++) {

            final JsValue value = gen.apply(new Random())
                                     .get();
            boolean test = condition.test(value);
            if(!test) System.out.println("Error testing \n"+value);
            Assertions.assertTrue(test);
        }
    }
}
