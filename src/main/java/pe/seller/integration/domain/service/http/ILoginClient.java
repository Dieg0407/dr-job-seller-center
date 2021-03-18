package pe.seller.integration.domain.service.http;

import pe.seller.integration.domain.model.dto.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ILoginClient {
    @POST()
    Call<LoginResponseDTO> login(
            @Url String path,
            @Query("grant_type") String grantType,
            @Query("username") String username,
            @Query("password") String password,
            @Header("Authorization") String authorization
    );
}
