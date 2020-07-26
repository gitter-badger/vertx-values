package actors.exp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Exp<O> extends Supplier<Future<O>> {

    <P> Exp<P> map(Function<O, P> fn);

    <Q> Exp<Q> flatMap(Function<O,Exp<Q>> fn);

    O result();

    Exp<O> retry(int attempts);

    Exp<O> retryIf(Predicate<Throwable> predicate, int attempts);

    Exp<O> recover(Function<Throwable, O> fn);

    Exp<O> recoverWith(Function<Throwable, Exp<O>> fn);

    Exp<O> fallbackTo(Function<Throwable, Exp<O>> fn);

    Exp<O> onSuccess(Consumer<O> success);

    Exp<O> onComplete(Consumer<O> success, Consumer<Throwable> thowable);

    //todo poner en el javadoc que la exception del caso de fallo tiene que ser ReplyException
    Exp<Void> pipeTo(Message<?> message);

    //todo poner en el javadoc que la exception del caso de fallo tiene que ser ReplyException
    Exp<Void> pipeTo(Message<?> message,
                     Function<O, ?> successMapper,
                     Function<Throwable, ?> failureMapper);

    <U> Exp<U> flatMap(Function<O, Exp<U>> successMapper,
                       Function<Throwable, Exp<U>> failureMapper);

    Exp<O> onComplete(Handler<AsyncResult<O>> handler);



}
