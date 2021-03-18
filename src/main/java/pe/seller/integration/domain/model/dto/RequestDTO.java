package pe.seller.integration.domain.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {
    private String marketplaceId;
    private String sellerCorporateDocument;
    private String sellerCorporateName;
    private String sellerAddress;
    private String sellerPhone;
    private String sellerEmail;
    private String sellerUbigeo;
    private String clientDocumentType;
    private String clientDocument;
    private String clientFirstName;
    private String clientLastName;
    private String clientAddressStreet;
    private String clientAddressNumber;
    private String clientAddressComplement;
    private String clientAddressReference;
    private String clientReceiverName;
    private String clientReceiverDocument;
    private String clientPhone;
    private String clientEmail;
    private String clientUbigeo;
    private String itemsQuantity;
    private String orderWeight;
    private String orderVolWeight;
    private String orderNumber;
    private String currencyCode;
    private Integer totalValue;

    private List<RequestItemDTO> items;
}
