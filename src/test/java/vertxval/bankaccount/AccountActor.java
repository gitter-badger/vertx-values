package vertxval.bankaccount;

import jsonvalues.JsObj;
import jsonvalues.spec.JsErrorPair;
import vertxval.exp.Val;
import vertxval.exp.Cons;
import vertxval.exp.λ;

import java.util.Set;

import static vertxval.VertxValException.GET_BAD_MESSAGE_EXCEPTION;
import static vertxval.bankaccount.BankAccountModule.BROKE_RESP;
import static vertxval.bankaccount.Operation.IS_DEPOSIT;
import static vertxval.bankaccount.Operation.amountLens;

public class AccountActor implements λ<JsObj, Integer> {

    private int credit;

    public AccountActor(final int credit) {
        this.credit = credit;
    }

    @Override
    public Val<Integer> apply(final JsObj op) {
        Set<JsErrorPair> errors = Operation.spec.test(op);
        if (!errors.isEmpty())
            return Cons.failure(GET_BAD_MESSAGE_EXCEPTION.apply(errors.toString()));
        else {
            int amount = amountLens.get.apply(op);
            if (IS_DEPOSIT.test(op)) {
                return Cons.success(credit += amount);
            }
            else {
                if (credit - amount < 0) return Cons.success(BROKE_RESP);
                else return Cons.success(credit -= amount);
            }
        }
    }
}
