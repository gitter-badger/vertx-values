package actors;

import actors.exp.Exp;
import io.vertx.core.eventbus.Message;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExampleModule extends ActorsModule {


    public Function<Integer, Exp<Integer>> triple;
    public Function<Integer, Exp<Integer>> quadruple;
    public Function<Integer, Exp<Integer>> addOne;
    public Consumer<Integer> printNumber;


    @Override
    protected void onComplete() {
        triple = this.<Integer, Integer>getRegisteredActor("triple").ask();
        addOne = this.<Integer, Integer>getRegisteredActor("addOne").ask();
        printNumber = this.<Integer, Void>getRegisteredActor("print").tell();
        quadruple = actors.spawn(i -> i * 4);
    }

    @Override
    protected void registerActors() {
        final Function<Integer, Integer> triple      = i -> i * 3;
        final Function<Integer, Integer> addOne      = i -> i + 1;
        final Consumer<Message<Integer>> printNumber = m -> System.out.println(m.body());

        registerActor("triple",
                      actors.register(triple)
                     );
        registerActor("addOne",
                      actors.register(addOne)
                     );
        registerActor("printNumber",
                      actors.register(printNumber)
                     );
    }
}
