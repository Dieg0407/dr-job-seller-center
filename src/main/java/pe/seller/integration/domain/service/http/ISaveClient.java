package pe.seller.integration.domain.service.http;

import okhttp3.ResponseBody;
import pe.seller.integration.domain.model.dto.RequestDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ISaveClient {
    @POST
    Call<ResponseBody> listRepos(
            @Url String url,
            @Body RequestDTO dto
    );
}
