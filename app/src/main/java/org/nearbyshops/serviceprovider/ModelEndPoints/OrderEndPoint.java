package org.nearbyshops.serviceprovider.ModelEndPoints;


import org.nearbyshops.serviceprovider.Model.Order;

import java.util.List;

/**
 * Created by sumeet on 30/6/16.
 */
public class OrderEndPoint {

    private int itemCount;
    private int offset;
    private int limit;
    private int max_limit;
    private List<Order> results;


    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<Order> getResults() {
        return results;
    }

    public void setResults(List<Order> results) {
        this.results = results;
    }

    public Integer getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(Integer max_limit) {
        this.max_limit = max_limit;
    }
}
