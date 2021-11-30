package cl.transbank.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MallTransactionCreateDetails {
    private List<Detail> detailList = new ArrayList<>();

    private MallTransactionCreateDetails() {}

    public static MallTransactionCreateDetails build() {
        return new MallTransactionCreateDetails();
    }

    public static MallTransactionCreateDetails build(double amount, String commerceCode, String buyOrder) {
        return MallTransactionCreateDetails.build().add(amount, commerceCode, buyOrder);
    }

    public MallTransactionCreateDetails add(double amount, String commerceCode, String buyOrder) {
        detailList.add(new Detail(amount, commerceCode, buyOrder));
        return this;
    }

    public boolean remove(double amount, String commerceCode, String buyOrder) {
        return getDetails().remove(new Detail(amount, commerceCode, buyOrder));
    }

    public List<Detail> getDetails() {
        return Collections.unmodifiableList(detailList);
    }

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class Detail {
        private double amount;
        private String commerceCode;
        private String buyOrder;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Detail))
                return false;

            Detail another = (Detail) obj;
            return getAmount() == another.getAmount() && getCommerceCode().equals(another.getBuyOrder()) &&
                    getBuyOrder().equals(another.getBuyOrder());
        }
    }
}

