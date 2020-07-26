package actors.bankaccount;

import actors.Actor;
import actors.ActorRef;
import actors.ActorsModule;
import actors.exp.Exp;
import jsonvalues.JsObj;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import static actors.bankaccount.Account.creditLens;
import static actors.bankaccount.Account.nameLens;

public class BankAccountModule extends ActorsModule {


    public static final Predicate<Integer> IS_OK_RESP = i -> i >= 0;
    /**
     Code representing that an operation failed because some account doesn't have enough money
     */
    public static final int BROKE_RESP = -1;

    /**
     Account -> Fut[Actor[Operation, Credit]] where Actor[Operation, Credit] is an actor representing an Account
     it returns the credit of the account after the operation or -1 if
     Spawns a person account to send operations like deposits and withdraws
     */
    public Function<JsObj, Exp<ActorRef<JsObj, Integer>>> registerAccount;

    /**
     Transaction -> Code
     Performs a transaction between two accounts. The transaction contains the accounts and the amount of money
     to move
     */
    public BiFunction<Actor<JsObj, Integer>, Actor<JsObj, Integer>, Actor<Integer, Integer>> makeTx;


    @Override
    protected void onComplete() {
        registerAccount = account -> actors.register(nameLens.get.apply(account),
                                                     new AccountActor(creditLens.get.apply(account))
                                                    );

        makeTx = (from, to) -> actors.spawn(new TxActor(from,
                                                        to
                                            )
                                           );
    }

    @Override
    protected void registerActors() {

    }

}


