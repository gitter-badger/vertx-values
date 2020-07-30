package vertxval.bankaccount;

import jsonvalues.JsObj;
import vertxval.VertxModule;
import vertxval.VerticleRef;
import vertxval.exp.Val;
import vertxval.exp.λ;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static vertxval.bankaccount.Account.creditLens;
import static vertxval.bankaccount.Account.nameLens;

public class BankAccountModule extends VertxModule {


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
    public Function<JsObj, Val<VerticleRef<JsObj, Integer>>> registerAccount;

    /**
     Transaction -> Code
     Performs a transaction between two accounts. The transaction contains the accounts and the amount of money
     to move
     */
    public BiFunction<λ<JsObj, Integer>, λ<JsObj, Integer>, λ<Integer, Integer>> makeTx;


    @Override
    protected void define() {
        registerAccount = account -> deployer.deployλ(nameLens.get.apply(account),
                                                      new AccountActor(creditLens.get.apply(account))
                                                     );

        makeTx = (from, to) -> deployer.spawnλ(new TxActor(from,
                                                           to
                                               )
                                              );
    }

    @Override
    protected void deploy() {

    }

}


