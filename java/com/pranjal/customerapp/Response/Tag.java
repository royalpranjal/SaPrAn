package com.pranjal.customerapp.Response;

/**
 * Created by royalpranjal on 1/29/2017.
 */

public class Tag {

    private String name;
    private Double confidence;
    private String hint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

}
