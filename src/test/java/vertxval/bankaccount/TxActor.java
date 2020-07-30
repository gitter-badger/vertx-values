package vertxval.bankaccount;

import vertxval.exp.λ;
import vertxval.exp.Val;
import vertxval.exp.Cons;
import jsonvalues.JsObj;

import static vertxval.bankaccount.BankAccountModule.IS_OK_RESP;
import static vertxval.bankaccount.Operation.makeDeposit;
import static vertxval.bankaccount.Operation.makeWithdraw;

public class TxActor implements λ<Integer, Integer> {

    private λ<JsObj, Integer> from;
    private λ<JsObj, Integer> to;

    public TxActor(final λ<JsObj, Integer> from,
                   final λ<JsObj, Integer> to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public Val<Integer> apply(final Integer amount) {
        return from.apply(makeWithdraw.apply(amount))
                   .flatMap(resp -> IS_OK_RESP.test(resp) ?
                                    to.apply(makeDeposit.apply(amount)) :
                                    Cons.success(resp)
                           );
    }
}
