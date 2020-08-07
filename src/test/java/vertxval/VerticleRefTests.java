package vertxval;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.exp.λ;

import java.util.Set;
import java.util.function.Function;

@ExtendWith(VertxExtension.class)
public class VerticleRefTests {

    public static Deployer deployer;

    @BeforeAll
    public static void prepare(final Vertx vertx,
                               final VertxTestContext testContext
                              ) {

        deployer = new Deployer(vertx);
        testContext.completeNow();


    }

    @Test
    public void _1(final VertxTestContext context) throws InterruptedException {
        Function<Integer, Integer> doubleFn = i -> i * 2;


        //nothing is executing, a val is a lazy data structure

        deployer.deployFn(doubleFn,
                          new DeploymentOptions().setInstances(4)
                         )
                .map(verRef -> {
                    // a VerticleRef is a wrapper around a Verticle

                    // an address is generated if it was not provided
                    System.out.println(verRef.address);
                    // a set of ids, once per instance
                    Set<String> ids = verRef.ids;
                    System.out.println(ids);
                    // ask method returns  a lambda, which is an alias for Function<Integer,Val<Integer>>
                    // a Val<O> is an alias for Supplier<Future<O>>
                    λ<Integer, Integer> doubleλ = verRef.ask();


                    return doubleλ;


                })
                .flatMap(it -> it.apply(1))
                .onSuccess(result -> {
                    context.verify(() -> Assertions.assertEquals(2,
                                                                 result
                                                                ));
                    context.completeNow();
                })
                .get();

    }


}
