package pe.seller.integration.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseDTO implements Serializable {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("tokenType")
    private String tokenType;
}
