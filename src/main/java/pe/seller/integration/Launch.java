package pe.seller.integration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;

@Log4j2
@SpringBootApplication
public class Launch implements CommandLineRunner {
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
        log.info("Message arrived via an inbound channel adapter from sub-one! Payload: " + payload);
        message.ack();
    }
}
