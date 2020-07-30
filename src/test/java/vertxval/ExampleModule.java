package vertxval;

import io.vertx.core.eventbus.Message;
import vertxval.exp.Val;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExampleModule extends VertxModule {


    public static Function<Integer, Val<Integer>> triple;
    public static  Function<Integer, Val<Integer>> quadruple;
    public static Function<Integer, Val<Integer>> addOne;
    public static Consumer<Integer> printNumber;
    {
        Function<Integer, Integer> integerIntegerFn = i -> i * 4;
        quadruple = deployer.spawnFn(integerIntegerFn);
    }



    @Override
    protected void define() {
        triple = this.<Integer, Integer>getDeployedVerticle("triple").ask();
        addOne = this.<Integer, Integer>getDeployedVerticle("addOne").ask();
        printNumber = this.<Integer, Void>getDeployedVerticle("print").tell();
    }

    @Override
    protected void deploy() {
        final Function<Integer, Integer> triple      = i -> i * 3;
        final Function<Integer, Integer> addOne      = i -> i + 1;
        final Consumer<Message<Integer>> printNumber = m -> System.out.println(m.body());

        this.deployFn("triple",
                      triple
                     );
        this.deployFn("addOne",
                      addOne
                     );

        this.deployConsumer("printNumber",
                            printNumber
                           );



    }
}
