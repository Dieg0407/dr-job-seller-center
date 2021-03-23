package pe.seller.integration.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.seller.integration.domain.model.dto.IntegrationRequestDTO;
import pe.seller.integration.domain.service.IIntegrationService;

@RestController
@RequestMapping(path = "/integraion/v1")
public class IntegrationController {
    @Autowired
    IIntegrationService service;

    @PostMapping(path = "")
    public ResponseEntity<Void> create(@RequestBody IntegrationRequestDTO data) {
        if (service.process(data)) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
