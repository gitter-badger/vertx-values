package vertxval.exp;


import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;

public final class Switch<O> extends AbstractVal<O> {

    final List<Val<Boolean>> predicates;
    final List<Val<O>> branches;


    public static <O> Switch<O> of(final Val<Boolean> predicate1,
                                   final Val<O> branch1,
                                   final Val<Boolean> predicate2,
                                   final Val<O> branch2) {

        List<Val<Boolean>> predicates = new ArrayList<>();
        List<Val<O>>       branches   = new ArrayList<>();
        predicates.add(requireNonNull(predicate1));
        predicates.add(requireNonNull(predicate2));
        branches.add(requireNonNull(branch1));
        branches.add(requireNonNull(branch2));
        return new Switch<>(predicates,
                            branches
        );

    }


    public static <O> Switch<O> of(final Val<Boolean> predicate1,
                                   final Val<O> branch1,
                                   final Val<Boolean> predicate2,
                                   final Val<O> branch2,
                                   final Val<Boolean> predicate3,
                                   final Val<O> branch3) {

        List<Val<Boolean>> predicates = new ArrayList<>();
        List<Val<O>>       branches   = new ArrayList<>();
        predicates.add(requireNonNull(predicate1));
        predicates.add(requireNonNull(predicate2));
        predicates.add(requireNonNull(predicate3));
        branches.add(requireNonNull(branch1));
        branches.add(requireNonNull(branch2));
        branches.add(requireNonNull(branch3));

        return new Switch<>(predicates,
                            branches
        );

    }


    public static <O> Switch<O> of(final Val<Boolean> predicate1,
                                   final Val<O> branch1,
                                   final Val<Boolean> predicate2,
                                   final Val<O> branch2,
                                   final Val<Boolean> predicate3,
                                   final Val<O> branch3,
                                   final Val<Boolean> predicate4,
                                   final Val<O> branch4) {

        List<Val<Boolean>> predicates = new ArrayList<>();
        List<Val<O>>       branches   = new ArrayList<>();
        predicates.add(requireNonNull(predicate1));
        predicates.add(requireNonNull(predicate2));
        predicates.add(requireNonNull(predicate3));
        predicates.add(requireNonNull(predicate4));
        branches.add(requireNonNull(branch1));
        branches.add(requireNonNull(branch2));
        branches.add(requireNonNull(branch3));
        branches.add(requireNonNull(branch4));

        return new Switch<>(predicates,
                            branches
        );

    }


    public static <I, O> Switch<O> of(final Val<Boolean> predicate1,
                                      final Val<O> branch1,
                                      final Val<Boolean> predicate2,
                                      final Val<O> branch2,
                                      final Val<Boolean> predicate3,
                                      final Val<O> branch3,
                                      final Val<Boolean> predicate4,
                                      final Val<O> branch4,
                                      final Val<Boolean> predicate5,
                                      final Val<O> branch5) {


        List<Val<Boolean>> predicates = new ArrayList<>();
        List<Val<O>>       branches   = new ArrayList<>();
        predicates.add(requireNonNull(predicate1));
        predicates.add(requireNonNull(predicate2));
        predicates.add(requireNonNull(predicate3));
        predicates.add(requireNonNull(predicate4));
        predicates.add(requireNonNull(predicate5));
        branches.add(requireNonNull(branch1));
        branches.add(requireNonNull(branch2));
        branches.add(requireNonNull(branch3));
        branches.add(requireNonNull(branch4));
        branches.add(requireNonNull(branch5));

        return new Switch<>(predicates,
                            branches
        );


    }


    public Switch(final List<Val<Boolean>> predicates,
                  final List<Val<O>> branches) {
        this.predicates = requireNonNull(predicates);
        this.branches = requireNonNull(branches);
    }

    public static <I, O> Switch<O> of(final Val<Boolean> predicate1,
                                      final Val<O> branch1,
                                      final Val<Boolean> predicate2,
                                      final Val<O> branch2,
                                      final Val<Boolean> predicate3,
                                      final Val<O> branch3,
                                      final Val<Boolean> predicate4,
                                      final Val<O> branch4,
                                      final Val<Boolean> predicate5,
                                      final Val<O> branch5,
                                      final Val<Boolean> predicate6,
                                      final Val<O> branch6) {

        List<Val<Boolean>> predicates = new ArrayList<>();
        List<Val<O>>       branches   = new ArrayList<>();
        predicates.add(requireNonNull(predicate1));
        predicates.add(requireNonNull(predicate2));
        predicates.add(requireNonNull(predicate3));
        predicates.add(requireNonNull(predicate4));
        predicates.add(requireNonNull(predicate5));
        predicates.add(requireNonNull(predicate6));
        branches.add(requireNonNull(branch1));
        branches.add(requireNonNull(branch2));
        branches.add(requireNonNull(branch3));
        branches.add(requireNonNull(branch4));
        branches.add(requireNonNull(branch5));
        branches.add(requireNonNull(branch6));

        return new Switch<>(predicates,
                            branches
        );
    }

    @Override
    public <P> Val<P> map(final Function<O, P> fn) {
        requireNonNull(fn);
        return Cons.of(()->get().map(fn));
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
    public Val<O> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new Switch<>(predicates.stream().map(it->it.retry(attempts)).collect(Collectors.toList()),
                            branches.stream().map(it->it.retry(attempts)).collect(Collectors.toList())
        );
    }

    @Override
    public Val<O> retryIf(final Predicate<Throwable> predicate,
                          final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
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
