package actors.bankaccount;

import actors.Fn;
import actors.exp.Val;
import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;

import java.util.function.Consumer;

import static actors.bankaccount.BankAccountModule.IS_OK_RESP;
import static actors.bankaccount.Operation.makeDeposit;
import static actors.bankaccount.Operation.makeWithdraw;

public class TxActor implements Consumer<Message<Integer>> {

    private Fn<JsObj, Integer> from;
    private Fn<JsObj, Integer> to;

    public TxActor(final Fn<JsObj, Integer> from,
                   final Fn<JsObj, Integer> to) {
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
            .pipeTo(message).get();


    }


}
