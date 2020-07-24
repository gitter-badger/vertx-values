package actors.bankaccount;

import actors.Actor;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;

import java.util.function.Consumer;

import static actors.bankaccount.BankAccountModule.IS_OK_RESP;
import static actors.bankaccount.Operation.makeDeposit;
import static actors.bankaccount.Operation.makeWithdraw;


public class AccountantActor implements Consumer<Message<Integer>> {

    /**
     from :: Operation -> Code
    */

    private final Actor<JsObj,Integer> from;

    /**
     to :: Operation -> Code
     */
    private final Actor<JsObj, Integer> to;

    public AccountantActor(final Actor<JsObj,Integer> from,
                           final Actor<JsObj, Integer> to) {
        this.from = from;
        this.to = to;
    }

    /**
     @param message amount of money to move between two accounts
     */
    @Override
    public void accept(final Message<Integer> message) {
        Integer amount = message.body();
        from.apply(makeWithdraw.apply(amount))
            .flatMap(resp -> IS_OK_RESP.test(resp) ?
                             to.apply(makeDeposit.apply(amount)) :
                             Future.succeededFuture(resp)
                    )
            .onSuccess(message::reply);

    }
}
