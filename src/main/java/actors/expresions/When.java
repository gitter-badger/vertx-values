package actors.expresions;
import io.vertx.core.Future;
import java.util.function.Supplier;
public class When<O> implements Supplier<Future<O>>
{

  private Supplier<Future<Boolean>> predicate;
  private Supplier<Future<O>> consequence;
  private Supplier<Future<O>> alternative;

  When(final Supplier<Future<Boolean>> predicate) {
    this.predicate = predicate;
  }

  public When<O> consequence(final Supplier<Future<O>> then){
    this.consequence = then;
    return this;
  }

  public When<O> alternative(final Supplier<Future<O>> alternative){
    this.alternative = alternative;
    return this;
  }

  @Override
  public Future<O> get()
  {
    final Future<Boolean> futureCon = predicate.get();
    if(futureCon.failed()) return Future.failedFuture(futureCon.cause());
    return futureCon.flatMap(c -> {
      if (c) return consequence.get();
      else return alternative.get();
    });
  }
}
