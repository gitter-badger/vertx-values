package actors.bankaccount;

import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;
import jsonvalues.spec.JsErrorPair;

import java.util.Set;
import java.util.function.Consumer;
import static actors.ActorExceptions.GET_BAD_MESSAGE_EXCEPTION;
import static actors.bankaccount.BankAccountModule.BROKE_RESP;
import static actors.bankaccount.Operation.IS_DEPOSIT;
import static actors.bankaccount.Operation.amountLens;

public class AccountActor implements Consumer<Message<JsObj>> {





    private int credit;

    public AccountActor(final int credit) {
        this.credit = credit;
    }

    /**
     @param message Operation to be carried out
     */
    @Override
    public void accept(final Message<JsObj> message) {

        JsObj op = message.body();
        Set<JsErrorPair> errors = Operation.spec.test(op);
        if(!errors.isEmpty())
            message.reply(GET_BAD_MESSAGE_EXCEPTION.apply(errors.toString()));
        else {
            int amount = amountLens.get.apply(op);
            if (IS_DEPOSIT.test(op)) {
               message.reply(credit += amount);
            }
            else {
                if(credit - amount < 0) message.reply(BROKE_RESP);
                else message.reply(credit -= amount);
            }
        }
    }
}
