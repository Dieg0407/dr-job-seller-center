package pe.seller.integration.domain.model.dto;

import lombok.Data;

@Data
public class RequestItemDTO {
    private String id;
    private String description;
    private String quantity;
    private String weight;
    private String volWeight;
    private String price;
    private String width;
    private String height;
    private String lenght;
}
