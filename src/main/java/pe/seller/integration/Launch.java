package pe.seller.integration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import pe.seller.integration.domain.service.IIntegrationService;

@Log4j2
@SpringBootApplication
public class Launch implements CommandLineRunner {
    @Autowired IIntegrationService service;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Launch.class)
                //.web(WebApplicationType.NONE)
                .run(args);
    }

    public void run(String... args) {
        while(true) {
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {}
        }
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(
            String payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message
    ) {
        log.info("Message arrived!: " + message.getPubsubMessage().getMessageId());
        if (service.process(payload)){
            log.info("Se guardó el mensaje: " + message.getPubsubMessage().getMessageId());
            message.ack();
        }
        else{
            log.info("Fallo el guardado del mensaje: " + message.getPubsubMessage().getMessageId());
            message.nack();
        }

    }
}
