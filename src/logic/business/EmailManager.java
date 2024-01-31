package logic.business;

import java.io.InputStream;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class EmailManager extends Thread {

    private final String embeddedPassword = "abcd*1234";
    private String recipient;
    private String password;

    public EmailManager(String recipient, String password) {
        this.password = password;
        this.recipient = recipient;
    }

    @Override
    public void run() {
        super.run();
        sendEmail(recipient, password);
    }

    public void sendEmail(String recipient, String newPassword) {
        String email = getMailPrompt().replace(embeddedPassword, newPassword);;

        final String ZOHO_HOST = "smtp.zoho.eu";
        final String TLS_PORT = "897";

        final String SENDER_USERNAME = "bmoncalvillo@zohomail.eu";
        final String SENDER_PASSWORD = "Du75zJLqbaZ1";

        // protocol properties
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST); // change to GMAIL_HOST for gmail                                                         // for gmail
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtps.auth", "true");

        // close connection upon quit being sent
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        try {
            // create the message
            final MimeMessage msg = new MimeMessage(session);

            // set recipients and content
            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSubject("Password Recovery");
            msg.setText(email, "utf-8", "html");
            msg.setSentDate(new Date());

            // send the mail
            Transport transport = session.getTransport("smtps");
            // send the mail
            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }

    private String getMailPrompt() {
        // Specify the path to HTML file
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("resources/Email.html");
            byte[] emailBytes = new byte[stream.available()];
            stream.read(emailBytes);
            return new String(emailBytes);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

}
