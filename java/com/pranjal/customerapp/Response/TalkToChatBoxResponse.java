package com.pranjal.customerapp.Response;

import java.util.List;

/**
 * Created by royalpranjal on 1/28/2017.
 */
public class TalkToChatBoxResponse {

    private String answerText;
    private List<Media> media = null;

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

}
