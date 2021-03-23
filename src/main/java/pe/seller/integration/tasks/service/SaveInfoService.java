package pe.seller.integration.tasks.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.model.dto.RequestDTO;
import pe.seller.integration.domain.service.ISaveInfoService;
import pe.seller.integration.domain.service.http.ISaveClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Log4j2
@Service
public class SaveInfoService implements ISaveInfoService {

    ISaveClient saveClient;

    public SaveInfoService() {
        final var retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        saveClient = retrofit.create(ISaveClient.class);
    }

    @Override
    public void save(RequestDTO info, String url) {
        try {
            print(info);
            final var result = saveClient.listRepos(url, info).execute();

            if (result.isSuccessful()){
                log.info(result.body() == null ? "Empty" : result.body().string());
                return;
            }

            throw new RuntimeException("Falló el guardado de la información: " + result.code() + ". Mensaje: " + result.body());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void print(RequestDTO info) {
        try {
            final var str = new ObjectMapper().writeValueAsString(info);
            log.info("Request enviado: {}", str);
        }
        catch (Exception e) {}
    }
}
