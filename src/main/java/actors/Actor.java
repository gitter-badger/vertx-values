package actors;
import actors.exp.Exp;
import java.util.function.Function;

public interface Actor<I,O> extends Function<I, Exp<O>> {


}
