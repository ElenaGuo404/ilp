package uk.ac.ed.inf.domain;

/**
 * Deliveries class is used to present a single delivery object and its data retrieved from Order.
 */
public class Deliveries {

    /**
     * The fields store individual order's numbers, cost and its order outcome.
     */
    public final String orderNo;
    public final String outcome;
    public final Integer costInPence;

    public Deliveries(String orderNo,String outcome,Integer costInPence) {
        this.orderNo = orderNo;
        this.outcome = outcome;
        this.costInPence = costInPence;
    }

}
