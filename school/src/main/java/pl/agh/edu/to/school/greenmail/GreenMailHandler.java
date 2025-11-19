package pl.agh.edu.to.school.greenmail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Profile("dev")
public class GreenMailHandler {

    private final GreenMail greenMail;

    public GreenMailHandler(GreenMail greenMail) {
        this.greenMail = greenMail;
        this.greenMail.start();
        System.out.println("GreenMailHandler()");
    }

    @PostConstruct
    public void onServiceStarted() {
        System.out.println("GreenMailHandler.onServiceStarted()");
    }

    @PreDestroy
    public void onServiceDestroyed() throws MessagingException {
        System.out.println("GreenMailHandler.onServiceDestroyed()");

        for (MimeMessage message : greenMail.getReceivedMessages()) {
            String formattedMessage = "From: " + Arrays.toString(message.getFrom()) +
                    "| Subject: " + message.getSubject() +
                    "| Body: " + GreenMailUtil.getBody(message);
            System.out.println(formattedMessage);
        }

        greenMail.stop();
    }

}
