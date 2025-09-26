package com.scm.entities;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class Resume {

    private String name;

    private String address;

    private String email;

    private String ph;

    private String objective;

    public Resume(String name, String address, String email, String ph, String objective) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.ph = ph;
        this.objective = objective;
    }
}
