package vertxval.bankaccount;

import jsonvalues.JsObj;
import jsonvalues.spec.JsErrorPair;
import vertxval.exp.Exp;
import vertxval.exp.Val;
import vertxval.exp.λ;

import java.util.Set;

import static vertxval.Exceptions.GET_BAD_MESSAGE_EXCEPTION;
import static vertxval.bankaccount.BankAccountModule.BROKE_RESP;
import static vertxval.bankaccount.Operation.IS_DEPOSIT;
import static vertxval.bankaccount.Operation.amountLens;

public class AccountActor implements λ<JsObj, Integer> {

    private int credit;

    public AccountActor(final int credit) {
        this.credit = credit;
    }

    @Override
    public Exp<Integer> apply(final JsObj op) {
        Set<JsErrorPair> errors = Operation.spec.test(op);
        if (!errors.isEmpty())
            return Val.failure(GET_BAD_MESSAGE_EXCEPTION.apply(errors.toString()));
        else {
            int amount = amountLens.get.apply(op);
            if (IS_DEPOSIT.test(op)) {
                return Val.success(credit += amount);
            }
            else {
                if (credit - amount < 0) return Val.success(BROKE_RESP);
                else return Val.success(credit -= amount);
            }
        }
    }
}
