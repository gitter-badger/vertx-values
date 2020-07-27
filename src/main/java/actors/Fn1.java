package actors;

import actors.exp.Exp;
import io.vertx.core.eventbus.Message;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Fn1<I> extends Function<Consumer<Message<I>>, Exp<Void>> {


}
