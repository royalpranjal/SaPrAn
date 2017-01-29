package com.pranjal.customerapp.Response;

import java.util.List;

/**
 * Created by royalpranjal on 1/29/2017.
 */

public class NutrientsResponse {
    private Integer total_hits;
    private Float max_score;
    private List<Hit> hits = null;

    public Integer getTotalHits() {
        return total_hits;
    }

    public void setTotalHits(Integer totalHits) {
        this.total_hits = totalHits;
    }

    public Float getMaxScore() {
        return max_score;
    }

    public void setMaxScore(Float maxScore) {
        this.max_score = maxScore;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

}
