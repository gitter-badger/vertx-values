package vertxval.bankaccount;

import jsonvalues.JsObj;
import vertxval.exp.Cons;
import vertxval.exp.Val;
import vertxval.exp.λ;

import static vertxval.bankaccount.BankAccountModule.BROKE_RESP;
import static vertxval.bankaccount.Operation.IS_DEPOSIT;
import static vertxval.bankaccount.Operation.amountLens;

public class AccountVerticle implements λ<JsObj, Integer> {

    private int credit;

    private λ<JsObj, JsObj> validateOp;

    public AccountVerticle(final int credit,
                           final λ<JsObj, JsObj> validateOp) {
        this.credit = credit;
        this.validateOp = validateOp;
    }

    @Override
    public Val<Integer> apply(final JsObj op) {
        return validateOp.apply(op)
                         .flatMap(o -> {
                             int amount = amountLens.get.apply(op);
                             if (IS_DEPOSIT.test(op)) return Cons.success(credit += amount);
                             else {
                                 if (credit - amount < 0) return Cons.success(BROKE_RESP);
                                 else return Cons.success(credit -= amount);
                             }
                         });
    }
}
