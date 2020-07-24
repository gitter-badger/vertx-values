package actors.bankaccount;

import actors.VerticleRef;
import actors.Actors;
import actors.TestFns;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@ExtendWith(VertxExtension.class)
public class TestBankAccount {

    private static Actors actors;
    private static Function<Integer, Future<VerticleRef<String, String>>> personSupplier;

    public static final int OK = 0;
    public static final int BROKE = -1;
    public static final int BAD_MESSAGE = -2;

    public static final Predicate<Integer> IS_BROKE = i -> i == BROKE;


    public static final Function<Integer, String> DEPOSIT = i -> "+" + i;
    public static final Function<Integer, String> WITHDRAW = i -> "-" + i;




    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              ) {

        actors = new Actors(vertx);
        personSupplier =
                amount -> actors.register(new AccountActor(amount));


        testContext.completeNow();

    }


    @Test
    public void testCreateAndStopPersons(VertxTestContext context) {
        Future<VerticleRef<String, String>> rafaRefFuture = personSupplier.apply(100);

        Future<VerticleRef<String, String>> carmenRefFuture = personSupplier.apply(200);


        CompositeFuture.all(rafaRefFuture,
                            carmenRefFuture
                           )
                       .onComplete(TestFns.pipeTo(afterDeployment(),
                                                  context
                                                 ));
    }

    private Consumer<CompositeFuture> afterDeployment() {
        return result -> {
            List<Object>                 persons   = result.list();
            VerticleRef<String, Integer> rafaRef   = (VerticleRef<String, Integer>) persons.get(0);
            VerticleRef<String, Integer> carmenRef = (VerticleRef<String, Integer>) persons.get(1);
            System.out.println("Rafa address=" + rafaRef.address + " and id=" + rafaRef.ids);
            System.out.println("Carmen address=" + carmenRef.address + " and id=" + carmenRef.ids);
            rafaRef.unregister();
            carmenRef.unregister();
        };
    }

    @Test
    public void testTransfer(VertxTestContext context){

        Future<VerticleRef<String, String>> rafaRefFuture = personSupplier.apply(100);

        Future<VerticleRef<String, String>> carmenRefFuture = personSupplier.apply(200);


        CompositeFuture.all(rafaRefFuture,
                            carmenRefFuture
                           )
                       .onComplete(TestFns.pipeTo(afterDeployment(),
                                                  context
                                                 ));

    }
}
