package edu.kirkwood.shared;

import com.azure.communication.email.*;
import com.azure.communication.email.implementation.models.ErrorResponseException;
import com.azure.communication.email.models.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import io.github.cdimascio.dotenv.Dotenv;

public class CommunicationService {

    private static EmailClient createEmailClient() {
        Dotenv dotenv = Dotenv.load();
        String connectionString = dotenv.get("EMAIL_CONN");
        return new EmailClientBuilder().connectionString(connectionString).buildClient();
    }
    public static boolean sendEmail(String toAddr, String subject, String message) {
        try {
            Dotenv dotenv = Dotenv.load();
            EmailClient emailClient = createEmailClient();
            EmailAddress toAddress = new EmailAddress(toAddr);

            EmailMessage emailMessage = new EmailMessage()
                    .setSenderAddress(dotenv.get("FROM_EMAIL"))
                    .setToRecipients(toAddress)
                    .setSubject(subject)
                    .setBodyHtml(message);

            SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage, null);
            PollResponse<EmailSendResult> result = poller.waitForCompletion();
            return true;
        } catch (ErrorResponseException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getClass().getSimpleName());
            return false;
        }
    }
}

