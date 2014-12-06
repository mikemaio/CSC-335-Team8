//package ClientServer;

// -- Download JavaMail API from here: http://www.oracle.com/technetwork/java/javamail/index.html
// -- Download JavaBeans Activation Framework from here: http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-plat-419418.html#jaf-1.1.1-fcs-oth-JPR
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// -- need to include activation.jar, mailapi.jar, and smtp.jar to the build path
// -- need to include activation.jar, mailapi.jar, and smtp.jar to the build path
// -- need to include activation.jar, mailapi.jar, and smtp.jar to the build path
// -- need to include activation.jar, mailapi.jar, and smtp.jar to the build path
// -- need to include activation.jar, mailapi.jar, and smtp.jar to the build path
// -- need to include activation.jar, mailapi.jar, and smtp.jar to the build path
public class SendEmailUsingGMailSMTP {

	// -- set the gmail host URL
	final static private String host = "smtp.gmail.com";

	// -- You must have a valid gmail username/password pair to use
	// gmail as a SMTP service
	final static private String username = "CLUCSC335";
	final static private String password = "CLUC$C335";

	public static void main(String[] args) {

		// -- set up host properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// -- Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		// -- Set up the sender's email account information
		String from = "CLUCSC335@gmail.com";

		// -- Set up the recipient's email address
		String to = "reinhart@clunet.edu";
				
		try {
			// -- Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// -- Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// -- Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// -- Set Subject: header field
			message.setSubject("CSC335 Project");

			// Now set the actual message
			message.setText("This is the message.\nThis is the message.\nThis is the message.\n");

			// -- Send message
			// -- use either these three lines or...
			// Transport t = session.getTransport("smtp");
			// t.connect();
			// t.sendMessage(message, message.getAllRecipients());
			
			// -- .. this one (which ultimately calls sendMessage(...)
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
