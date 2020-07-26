package actors;

import actors.exp.Exp;
import actors.exp.MapExp;
import io.vavr.collection.Map;
import io.vertx.core.eventbus.Message;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExampleModule extends ActorsModule {


    public Function<Integer, Exp<Integer>> triple;
    public Function<Integer, Exp<Integer>> quadruple;
    public Function<Integer, Exp<Integer>> addOne;
    public Consumer<Integer> printNumber;


    @Override
    protected void onComplete(final Map<String, ActorRef<?, ?>> futures) {
        triple = this.<Integer, Integer>getActorRef("triple").ask();
        addOne = this.<Integer, Integer>getActorRef("addOne").ask();
        printNumber = this.<Integer, Void>getActorRef("print").tell();
        quadruple = actors.spawn(i -> i * 4);
    }

    @Override
    protected MapExp defineActors() {
        final Function<Integer, Integer> triple      = i -> i * 3;
        final Function<Integer, Integer> addOne      = i -> i + 1;
        final Consumer<Message<Integer>> printNumber = m -> System.out.println(m.body());

        return MapExp.of("triple",
                         actors.register(triple),
                         "addOne",
                         actors.register(addOne),
                         "printNumber",
                         actors.register(printNumber)
                        );
    }
}
