package ClientServer;

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

public class SendEmailUsingGMailSMTP {

	// -- set the gmail host URL
	final static private String host = "smtp.gmail.com";

	// -- You must have a valid gmail username/password pair to use
	// gmail as a SMTP service
	final static private String username = "CLUCSC335";
	final static private String password = "CLUC$C335";
	private String emailRecipient = "";
	
	public SendEmailUsingGMailSMTP()
	{

	}
	public void sendRecoveryEmail(String account, String accpassword, String email, String notification)
	{
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
		String to = email;
		
		try {
			// -- Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// -- Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// -- Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// -- Set Subject: header field
			message.setSubject("CSC335 Account");

			// Now set the actual message
			message.setText(notification);

			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	public void sendNotificationEmail(String account, String email, String notification)
	{
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
		String to = email;
				
		try {
			// -- Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// -- Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// -- Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// -- Set Subject: header field
			message.setSubject("CSC335 Account");

			// Now set the actual message
			message.setText(notification);

			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
