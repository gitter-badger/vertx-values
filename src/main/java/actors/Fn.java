package actors;
import actors.exp.Exp;
import java.util.function.Function;

public interface Fn<I,O> extends Function<I, Exp<O>> {


}
