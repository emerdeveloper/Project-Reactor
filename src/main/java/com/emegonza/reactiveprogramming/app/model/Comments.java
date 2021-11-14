package com.emegonza.reactiveprogramming.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Comments {

    private List<String> comments;

    public Comments() {
        this.comments = new ArrayList<>();
    }

    public Comments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }
}
