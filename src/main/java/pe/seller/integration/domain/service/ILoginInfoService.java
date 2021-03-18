package pe.seller.integration.domain.service;

import pe.seller.integration.domain.model.dto.LoginResponseDTO;

public interface ILoginInfoService {
    LoginResponseDTO login();
    LoginResponseDTO refresh();
}
