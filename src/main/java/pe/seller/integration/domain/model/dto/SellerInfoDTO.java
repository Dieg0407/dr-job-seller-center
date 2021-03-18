package pe.seller.integration.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerInfoDTO {
    private String id;
    private String corporateDocument;
    private String corporateName;
    private String address;
    private String phone;
    private String email;
    private String ubigeo;
}
