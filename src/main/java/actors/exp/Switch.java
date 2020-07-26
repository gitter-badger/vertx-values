package actors.exp;


import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Switch<O> extends AbstractExp<O> {

    final List<Exp<Boolean>> predicates;
    final List<Exp<O>> branches;


    public static <O> Switch<O> of(final Exp<Boolean> predicate1,
                                   final Exp<O> branch1,
                                   final Exp<Boolean> predicate2,
                                   final Exp<O> branch2) {

        List<Exp<Boolean>> predicates = new ArrayList<>();
        List<Exp<O>>       branches   = new ArrayList<>();
        predicates.add(predicate1);
        predicates.add(predicate2);
        branches.add(branch1);
        branches.add(branch2);
        return new Switch<>(predicates,
                            branches
        );

    }


    public static <O> Switch<O> of(final Exp<Boolean> predicate1,
                                   final Exp<O> branch1,
                                   final Exp<Boolean> predicate2,
                                   final Exp<O> branch2,
                                   final Exp<Boolean> predicate3,
                                   final Exp<O> branch3) {

        List<Exp<Boolean>> predicates = new ArrayList<>();
        List<Exp<O>>       branches   = new ArrayList<>();
        predicates.add(predicate1);
        predicates.add(predicate2);
        predicates.add(predicate3);
        branches.add(branch1);
        branches.add(branch2);
        branches.add(branch3);

        return new Switch<>(predicates,
                            branches
        );

    }


    public static <O> Switch<O> of(final Exp<Boolean> predicate1,
                                   final Exp<O> branch1,
                                   final Exp<Boolean> predicate2,
                                   final Exp<O> branch2,
                                   final Exp<Boolean> predicate3,
                                   final Exp<O> branch3,
                                   final Exp<Boolean> predicate4,
                                   final Exp<O> branch4) {

        List<Exp<Boolean>> predicates = new ArrayList<>();
        List<Exp<O>>       branches   = new ArrayList<>();
        predicates.add(predicate1);
        predicates.add(predicate2);
        predicates.add(predicate3);
        predicates.add(predicate4);
        branches.add(branch1);
        branches.add(branch2);
        branches.add(branch3);
        branches.add(branch4);

        return new Switch<>(predicates,
                            branches
        );

    }


    public static <I, O> Switch<O> of(final Exp<Boolean> predicate1,
                                      final Exp<O> branch1,
                                      final Exp<Boolean> predicate2,
                                      final Exp<O> branch2,
                                      final Exp<Boolean> predicate3,
                                      final Exp<O> branch3,
                                      final Exp<Boolean> predicate4,
                                      final Exp<O> branch4,
                                      final Exp<Boolean> predicate5,
                                      final Exp<O> branch5) {


        List<Exp<Boolean>> predicates = new ArrayList<>();
        List<Exp<O>>       branches   = new ArrayList<>();
        predicates.add(predicate1);
        predicates.add(predicate2);
        predicates.add(predicate3);
        predicates.add(predicate4);
        predicates.add(predicate5);
        branches.add(branch1);
        branches.add(branch2);
        branches.add(branch3);
        branches.add(branch4);
        branches.add(branch5);

        return new Switch<>(predicates,
                            branches
        );


    }


    public Switch(final List<Exp<Boolean>> predicates,
                  final List<Exp<O>> branches) {
        this.predicates = predicates;
        this.branches = branches;
    }

    public static <I, O> Switch<O> of(final Exp<Boolean> predicate1,
                                      final Exp<O> branch1,
                                      final Exp<Boolean> predicate2,
                                      final Exp<O> branch2,
                                      final Exp<Boolean> predicate3,
                                      final Exp<O> branch3,
                                      final Exp<Boolean> predicate4,
                                      final Exp<O> branch4,
                                      final Exp<Boolean> predicate5,
                                      final Exp<O> branch5,
                                      final Exp<Boolean> predicate6,
                                      final Exp<O> branch6) {

        List<Exp<Boolean>> predicates = new ArrayList<>();
        List<Exp<O>>       branches   = new ArrayList<>();
        predicates.add(predicate1);
        predicates.add(predicate2);
        predicates.add(predicate3);
        predicates.add(predicate4);
        predicates.add(predicate5);
        predicates.add(predicate6);
        branches.add(branch1);
        branches.add(branch2);
        branches.add(branch3);
        branches.add(branch4);
        branches.add(branch5);
        branches.add(branch6);

        return new Switch<>(predicates,
                            branches
        );
    }

    @Override
    public <P> Exp<P> map(final Function<O, P> fn) {
        return Val.of(()->get().map(fn));
    }

    @Override
    public O result() {
        OptionalInt first = IntStream.range(0,
                                            predicates.size())
                                     .filter(n -> predicates.get(n)
                                                            .get()
                                                            .result())
                                     .findFirst();

        if(first.isPresent())return branches.get(first.getAsInt()).get().result();
        else return null;


    }

    @Override
    public Exp<O> retry(final int attempts) {
        return new Switch<>(predicates.stream().map(it->it.retry(attempts)).collect(Collectors.toList()),
                            branches.stream().map(it->it.retry(attempts)).collect(Collectors.toList())
        );
    }

    @Override
    public Exp<O> retryIf(final Predicate<Throwable> predicate,
                          final int attempts) {
        return new Switch<>(predicates.stream().map(it->it.retryIf(predicate,attempts)).collect(Collectors.toList()),
                            branches.stream().map(it->it.retryIf(predicate,attempts)).collect(Collectors.toList())
        );
    }

    @Override
    public Future<O> get() {

        return CompositeFuture.all(predicates.stream()
                                             .map(Supplier::get)
                                             .collect(Collectors.toList()))
                              .flatMap(it -> {
                                  List<Object> predicatesResult = it.list();
                                  OptionalInt first = IntStream.range(0,
                                                                      predicatesResult.size()
                                                                     )
                                                               .filter(i -> {
                                                                   Object o = predicatesResult.get(i);
                                                                   return (Boolean) o;
                                                               })
                                                               .findFirst();
                                  if (first.isPresent()) return branches.get(first.getAsInt())
                                                                        .get();
                                  return Future.succeededFuture(null);
                              });


    }


}
