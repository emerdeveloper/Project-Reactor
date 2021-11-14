package com.emegonza.reactiveprogramming.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String name;
    private String lastName;

    public String getCompleteName() {
        return getName().concat(" ").concat(getLastName());
    }
}
