package pe.seller.integration.domain.service;

import pe.seller.integration.domain.model.dto.RequestDTO;

public interface ISaveInfoService {
    void save(RequestDTO info, String url);
}
