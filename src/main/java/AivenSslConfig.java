import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Component
public class AivenSslConfig {

    @Value("${AIVEN_CA_CERT:}")
    private String caCertBase64;

    @PostConstruct
    public void setupSslCert() throws Exception {
        if (caCertBase64 == null || caCertBase64.isEmpty()) return;

        byte[] certBytes = Base64.getDecoder().decode(caCertBase64);
        Path certPath = Path.of("/tmp/aiven-ca.pem");
        Files.write(certPath, certBytes);

        System.out.println("✅ Aiven CA cert written to /tmp/aiven-ca.pem");
    }
}