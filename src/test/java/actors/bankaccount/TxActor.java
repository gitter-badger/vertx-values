package actors.bankaccount;

import actors.Actor;
import actors.exp.Val;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;

import java.util.function.Consumer;

import static actors.bankaccount.BankAccountModule.IS_OK_RESP;
import static actors.bankaccount.Operation.makeDeposit;
import static actors.bankaccount.Operation.makeWithdraw;

public class TxActor implements Consumer<Message<Integer>> {

    private Actor<JsObj, Integer> from;
    private Actor<JsObj, Integer> to;

    public TxActor(final Actor<JsObj, Integer> from,
                   final Actor<JsObj, Integer> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void accept(final Message<Integer> message) {
        int amount = message.body();
        from.apply(makeWithdraw.apply(amount))
            .flatMap(resp -> IS_OK_RESP.test(resp) ?
                             to.apply(makeDeposit.apply(amount)) :
                             Val.of(resp)
                    )
            .pipeTo(message)
            .get();


    }


}
