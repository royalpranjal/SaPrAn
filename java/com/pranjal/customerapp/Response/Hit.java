package com.pranjal.customerapp.Response;

/**
 * Created by royalpranjal on 1/29/2017.
 */


public class Hit {

    private String _index;
    private String _type;
    private String _id;
    private Float _score;
    private Fields fields;

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Float get_score() {
        return _score;
    }

    public void set_score(Float _score) {
        this._score = _score;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

}
