package pe.seller.integration.tasks.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.model.dto.RequestDTO;
import pe.seller.integration.domain.service.ISaveInfoService;

@Log4j2
@Service
public class SaveInfoService implements ISaveInfoService {
    @Override
    public void save(RequestDTO info, String url) {
        log.info(info);
    }
}
