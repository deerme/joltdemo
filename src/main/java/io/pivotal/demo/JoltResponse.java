package io.pivotal.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

class JoltResponse {

    @JsonProperty
    private String result;

    @JsonProperty
    private String responseDateTime;

    private JoltResponse(String result) {
        this.result = result;
        this.responseDateTime = ISO8601DateIO.now();
    }

    static JoltResponse simple(String result) {
        return new JoltResponse(result);
    }
}
