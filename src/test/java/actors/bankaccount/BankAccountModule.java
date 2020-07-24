package actors.bankaccount;

import actors.Actor;
import actors.ActorsModule;
import actors.VerticleRef;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class BankAccountModule extends ActorsModule {


    /**
     Code representing that an operation succeeded
     */
    public static final int OK_RESP = 0;

    /**
     Code representing that an operation failed because some account doesn't have enough money
     */
    public static final int BROKE_RESP = -1;

    public static final Predicate<Integer> IS_OK_RESP = i -> i == OK_RESP;
    public static final Predicate<Integer> IS_BROKE_RESP = i -> i == BROKE_RESP;


    /**
     name -> Account
     Given a name it returns its associated account
     */
    public static Actor<String, JsObj> getAccountActor;

    /**
     Account -> Void
     It persists the new account state
     */
    public static Actor<JsObj, Void> saveAccountActor;

    /**
     Account -> Fut[Actor[Operation, Code]] where Actor[Operation, Code] is an actor representing an Account
     Spawns a person account to send operations like deposits and withdraws
     */
    public static Function<JsObj, Future<Actor<JsObj, Integer>>> registerAccountActor;

    /**
     Actor[Operation,Code],Actor[Operation,Code] -> Fut[Actor[Amount,Code]]  where Actor[Amount,Code] is an actor
     representing an Accountant.
     Given two accounts, it spawns an accountant that will move money from one account to the other, sending operations
     to them
     */
    public static BiFunction<Actor<JsObj, Integer>, Actor<JsObj, Integer>, Actor<Integer, Integer>> spawnAccountantActor;


    /**
     Transaction -> Code
     Performs a transaction between two accounts. The transaction contains the accounts and the amount of money
     to move
     */
    public static Actor<JsObj, Integer> makeTx;

    @Override
    protected void defineActors(final List<Object> futures) {

        registerAccountActor = account -> {
            Future<VerticleRef<JsObj, Integer>> deploy = actors.register(Account.nameLens.get.apply(account),
                                                                         new AccountActor(Account.creditLens.get.apply(account))
                                                                        );
            return deploy.map(VerticleRef::ask);
        };
        spawnAccountantActor = (from, to) -> actors.spawn(new AccountantActor(from,
                                                                          to
                                                      )
                                                     );

        makeTx = tx -> {
            JsObj fromAccount = JsObj.empty();
            JsObj toAccount = JsObj.empty();

            Future<Actor<JsObj, Integer>> fromFut = registerAccountActor.apply(fromAccount);

            Future<Actor<JsObj, Integer>> toFut = registerAccountActor.apply(toAccount);

            return fromFut.flatMap(fromActor ->
                                           toFut.flatMap(toActor ->
                                                                 spawnAccountantActor.apply(fromActor,
                                                                                            toActor
                                                                                           )
                                                                                     .apply(Transaction.amountLens.get.apply(tx)))
                                  );


        };

        VerticleRef<String, JsObj> getAccountRef = toVerticleRef(futures.get(0));

        getAccountActor = getAccountRef.ask();

        VerticleRef<JsObj, Void> saveAccountRef = toVerticleRef(futures.get(1));

        saveAccountActor = saveAccountRef.ask();


    }

    @Override
    protected List<Future> registerActors() {

        Function<String, JsObj>  getAccount  = name -> JsObj.empty();
        Consumer<Message<JsObj>> saveAccount = message -> {};

        return Arrays.asList(actors.register(getAccount),
                             actors.register(saveAccount)
                            );

    }
}
