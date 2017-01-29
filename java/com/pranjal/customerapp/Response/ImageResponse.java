package com.pranjal.customerapp.Response;

import java.util.List;

/**
 * Created by royalpranjal on 1/29/2017.
 */

public class ImageResponse {

    private List<Tag> tags;
    private String requestId;
    private Metadata metadata;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
