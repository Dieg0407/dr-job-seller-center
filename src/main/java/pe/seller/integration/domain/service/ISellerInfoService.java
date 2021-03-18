package pe.seller.integration.domain.service;

import pe.seller.integration.domain.model.dto.SellerInfoDTO;

public interface ISellerInfoService {
    SellerInfoDTO findSellerData(String accountName);
}
