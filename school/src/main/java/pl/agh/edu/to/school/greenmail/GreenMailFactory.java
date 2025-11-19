package pl.agh.edu.to.school.greenmail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class GreenMailFactory {

    public GreenMailFactory() {
        System.out.println("GreenMailFactory()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("GreenMailFactory.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() {
        System.out.println("GreenMailFactory.onServiceDestroyed()");
    }

    @Bean
    public GreenMail greenMail() {
        return new GreenMail(new ServerSetup(1025, "localhost", ServerSetup.PROTOCOL_SMTP));
    }

}
