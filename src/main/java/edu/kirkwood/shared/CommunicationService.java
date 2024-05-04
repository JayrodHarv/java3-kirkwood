package edu.kirkwood.shared;

import com.azure.communication.email.*;
import com.azure.communication.email.implementation.models.ErrorResponseException;
import com.azure.communication.email.models.*;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
public class CommunicationService {

    public static boolean sendNewUserEmail(String code, String email) {
        // Send user and email
        String subject = "LearnX New User Confirmation";
        String message = "<h2>Welcome to LearnX</h2>";
        message += "<p>Please enter code <b>" + code + "</b> to activate your account.</p>";
        return sendEmail(email, subject, message);
    }

    public static boolean sendNewSMPUserEmail(String code, String email) {
        // Send user and email
        String subject = "No Go Outside SMP New User Confirmation";
        String message = "<h2>Welcome to No Go Outside</h2>";
        message += "<p>Please enter code <b>" + code + "</b> to activate your account.</p>";
        return sendEmail(email, subject, message);
    }

    private static EmailClient createEmailClient() {
        Dotenv dotenv = Dotenv.load();
        String connectionString = dotenv.get("EMAIL_CONN");
        EmailClient emailClient = new EmailClientBuilder().connectionString(connectionString).buildClient();
        return emailClient;
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

    public static boolean sendPasswordResetEmail(String email, String token, HttpServletRequest req) {
        // Send user and email
        String subject = "LearnX Password Reset";
        String message = "<h2>Reset Your Password</h2>";
        message += "<p>Please use this link to securely reset your password. This link will expire in 30 minutes.</p>";
        String appURL = "";
        if(req.isSecure()) {
            appURL = req.getServletContext().getInitParameter("appURLCloud");
        } else {
            appURL = req.getServletContext().getInitParameter("appURLLocal");
        }
        String fullURL = String.format("%s/new-password?token=%s", appURL, token);
        message += String.format("<p><a href=\"%s\" target=\"_blank\">%s</a></p>", fullURL, fullURL);
        message += "<p>If you did not request to reset your password, you can ignore this message. Your password will not be changed.</p>";
        return sendEmail(email, subject, message);
    }

    public static boolean sendPasswordResetEmailSMP(String email, String token, HttpServletRequest req) {
        // Send user and email
        String subject = "No Go Outside Password Reset";
        String message = "<h2>Reset Your Password</h2>";
        message += "<p>Please use this link to securely reset your password. This link will expire in 30 minutes.</p>";
        String appURL = "";
        if(req.isSecure()) {
            appURL = req.getServletContext().getInitParameter("appURLCloud");
        } else {
            appURL = req.getServletContext().getInitParameter("appURLLocal");
        }
        String fullURL = String.format("%s/smp-new-password?token=%s", appURL, token);
        message += String.format("<p><a href=\"%s\" target=\"_blank\">%s</a></p>", fullURL, fullURL);
        message += "<p>If you did not request to reset your password, you can ignore this message. Your password will not be changed.</p>";
        return sendEmail(email, subject, message);
    }

}

