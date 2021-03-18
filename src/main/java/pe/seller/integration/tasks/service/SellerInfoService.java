package pe.seller.integration.tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.model.dto.SellerInfoDTO;
import pe.seller.integration.domain.service.ILoginInfoService;
import pe.seller.integration.domain.service.ISellerInfoService;
import pe.seller.integration.domain.service.http.ISellerInfoClient;
import retrofit2.Retrofit;

@Service
public class SellerInfoService implements ISellerInfoService {
    ILoginInfoService loginService;
    ISellerInfoClient client;
    Environment env;

    @Autowired
    public SellerInfoService(Retrofit retrofit, ILoginInfoService loginService, Environment env) {
        client = retrofit.create(ISellerInfoClient.class);
        this.loginService = loginService;
        this.env = env;
    }

    @Override
    public SellerInfoDTO findSellerData(String accountName) {
        return findSellerData(accountName, true);
    }

    SellerInfoDTO findSellerData(String accountName, boolean retry) {
        final var loginData = loginService.login();
        final var call = client.get(
                env.getProperty("app.inretail.seller.path") + "/" + accountName,
                String.format("%s %s", loginData.getTokenType(), loginData.getAccessToken())
        );

        try {
            final var result = call.execute();
            if (result.isSuccessful()) {
                return result.body();
            }
            else if (result.code() == 401 && retry) {
                loginService.refresh();
                return findSellerData(accountName, false);
            }
            throw new RuntimeException("Falló la obtención de datos del seller note: " + result.code() + ". Mensaje: " + result.message());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
