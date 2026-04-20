package com.bajajfinserv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookGenerationResponse {

    @JsonProperty("webhook")
    private String webhook;

    @JsonProperty("accessToken")
    private String accessToken;

}
