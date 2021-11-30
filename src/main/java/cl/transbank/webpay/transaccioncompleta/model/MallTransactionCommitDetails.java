package cl.transbank.webpay.transaccioncompleta.model;

import cl.transbank.webpay.transaccioncompleta.requests.TransactionCommitRequest;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MallTransactionCommitDetails {
    private List<Detail> detailList = new ArrayList<>();

    private MallTransactionCommitDetails() {}

    public static MallTransactionCommitDetails build() {
        return new MallTransactionCommitDetails();
    }

    public static MallTransactionCommitDetails build(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex, boolean gracePeriod) {
        return MallTransactionCommitDetails.build().add(commerceCode, buyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod);
    }

    public MallTransactionCommitDetails add(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex, boolean gracePeriod) {
        detailList.add(new Detail(commerceCode, buyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod));
        return this;
    }

    public boolean remove(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex, boolean gracePeriod) {
        return getDetails().remove(new Detail(commerceCode, buyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod));
    }

    public List<Detail> getDetails() {
        return Collections.unmodifiableList(detailList);
    }

    @Data
    @EqualsAndHashCode(callSuper=true)
    @ToString(callSuper=true)
    @AllArgsConstructor
    public class Detail extends TransactionCommitRequest {
        private String commerceCode;
        private String buyOrder;

        Detail(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex,boolean gracePeriod){
            super(idQueryInstallments, deferredPeriodIndex, gracePeriod);
            this.commerceCode = commerceCode;
            this.buyOrder = buyOrder;
        }

    }
}
