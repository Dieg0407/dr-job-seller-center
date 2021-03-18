package pe.seller.integration.tasks.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.model.dto.LoginResponseDTO;
import pe.seller.integration.domain.service.ILoginInfoService;
import pe.seller.integration.domain.service.http.ILoginClient;
import retrofit2.Retrofit;

@Service
public class LoginInfoService implements ILoginInfoService {
    LoginResponseDTO data;
    ILoginClient client;
    Environment env;


    @Autowired
    public LoginInfoService(Retrofit retrofit, Environment env) {
        client = retrofit.create(ILoginClient.class);
        this.env = env;
    }

    @Override
    public LoginResponseDTO login() {
        if (data != null) return data;
        return refresh();
    }

    @Override
    public LoginResponseDTO refresh() {
        final var call = client.login(
                env.getProperty("app.inretail.login.path"),
                env.getProperty("app.inretail.login.grant-type"),
                env.getProperty("app.inretail.login.username"),
                env.getProperty("app.inretail.login.password"),
                env.getProperty("app.inretail.login.authorization")
        );

        try {
            final var result = call.execute();
            if (result.isSuccessful()) {
                this.data = result.body();

                return this.data;
            }
            throw new RuntimeException("Falló el login al intercorp con status: " +
                    result.code() +
                    ". Mensaje: " +
                    (result.errorBody() != null ?  result.errorBody().string() : "" )
            );
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
