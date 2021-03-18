package pe.seller.integration.domain.service.http;

import pe.seller.integration.domain.model.dto.SellerInfoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ISellerInfoClient {

    @GET()
    Call<SellerInfoDTO> get(
            @Url String path,
            @Header("Authorization") String authHeader
    );
}
