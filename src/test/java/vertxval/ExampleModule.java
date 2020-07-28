package vertxval;

import io.vertx.core.eventbus.Message;
import vertxval.exp.Exp;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExampleModule extends Module {


    public Function<Integer, Exp<Integer>> triple;
    public Function<Integer, Exp<Integer>> quadruple;
    public Function<Integer, Exp<Integer>> addOne;
    public Consumer<Integer> printNumber;


    @Override
    protected void onComplete() {
        triple = this.<Integer, Integer>getDeployedVerticle("triple").ask();
        addOne = this.<Integer, Integer>getDeployedVerticle("addOne").ask();
        printNumber = this.<Integer, Void>getDeployedVerticle("print").tell();
        Function<Integer, Integer> integerIntegerFn = i -> i * 4;
        quadruple = deployer.spawnFn(integerIntegerFn);
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
