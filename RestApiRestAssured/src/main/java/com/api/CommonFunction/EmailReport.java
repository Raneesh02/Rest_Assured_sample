package com.api.CommonFunction;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailReport {

	private static String host = null;
	private static String port = null;
	private static String toAddressList = null;
	private static String ccAddressList=null;
	private static String bccAddress=null;
	private static String fromAddress = null;
	private static String EmailSubject = null;
	private static String messageText = null;
	private static String attachment = null;
	private static Properties properties = null;
	private static Session session = null;
	private static Message message = null;
	private static MimeMessage mimeMessage = null;
	private static MimeBodyPart bodyPart = null;

	public EmailReport() {

		
		
		/*System.out.println("mailConfigFile" +mailConfigFile);*/
		host = "smtp.jci.com";
		port = "null";
		toAddressList = "ashikali.maner@jci.com";
		fromAddress = "No_Reply@APIAutomation.com";
		ccAddressList= "";
		bccAddress="";
		EmailSubject = "API Automation Report";
		//messageText = ReadPropertyFile.getInstance(mailConfigFile).getProperty("messageText");
		attachment = "C:\\Users\\jmanera\\Documents\\Document\\Framework\\API_Reports\\API.html";
		messageText="Hi, Please find report attached";
		properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		session = Session.getDefaultInstance(properties);
	}

	
	public void sendEmailWithReports() {
		try {

			// Create new Email Message
			message = new MimeMessage(session);

			// Set From Address
			message.setFrom(new InternetAddress(fromAddress));

			String[] toAddressListAray = toAddressList.split(";");
			//String[] ccAddressListArray=ccAddressList.split(";");
			InternetAddress[] recipients = new InternetAddress[toAddressListAray.length];
			//InternetAddress[] ccRecipients = new InternetAddress[ccAddressListArray.length];
			//InternetAddress bccRecipient = new InternetAddress(bccAddress);
			for (int i = 0; i < recipients.length; i++) {
				recipients[i] = new InternetAddress(toAddressListAray[i].trim());
			}
		/*	for (int i = 0; i < ccRecipients.length; i++) {
				ccRecipients[i] = new InternetAddress(ccAddressListArray[i].trim());
			}*/
			// InternetAddress[] recipients = { new
			// InternetAddress(toAddressList) };
			message.setRecipients(Message.RecipientType.TO, recipients);
			/*message.setRecipients(Message.RecipientType.CC, ccRecipients);
			message.setRecipient(Message.RecipientType.BCC, bccRecipient);*/

			// Set Email Subject
			message.setSubject(EmailSubject);

			// Set Sent date
			message.setSentDate(new Date());

			// Create Message Part
			bodyPart = new MimeBodyPart();
			
			bodyPart.setContent(messageText, "text/plain; charset=utf-8");
		
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);

			// Add Attachment
			if (attachment != null && attachment.length() > 0) {
				MimeBodyPart includeAttachment = new MimeBodyPart();

				try {
					includeAttachment.attachFile(attachment);
				} catch (IOException ioe) {
					System.out.println(
							"Error including attachment " + attachment + "to the message \n" + ioe.getMessage());
				}
				multipart.addBodyPart(includeAttachment);
			}

			// Set multipart as email content
			message.setContent(multipart);

			// Send Email
			Transport.send(message);

		} catch (Exception e) {
			
			System.out.println("Error occured while sending email. \nError message - " + e.getMessage());
			 
			
		} 
	}
}
