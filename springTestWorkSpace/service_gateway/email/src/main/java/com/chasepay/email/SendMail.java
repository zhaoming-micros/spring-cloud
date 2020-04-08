/*
 * Created on 2005-8-18
 *
 * Project   : JavaMail
 * Package   : com.utility.mail
 * File name : SendMail.java
 * 
 * Author:
 * Revision:
 * 
 */

package com.chasepay.email;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import sslpayment.logger.Logger;

/**
 * @author xuling
 */
public class SendMail {
    
	static Logger logger = LogManager.getLogger(SendMail.class);	
	
    //protected BodyPart messageBodyPart = null; 
    private Multipart multipart = null; 
    private MimeMessage mailMessage = null; 
    private Session mailSession = null; 
    private Properties mailProperties = System.getProperties(); 
    
    private InternetAddress mailFromAddress = null; 
  
    public SendMail(String smtpHost,String username,String password){ 
        
        //System.out.println("smthHost="+smtpHost+" username="+username+" password="+password);
        mailProperties.put("mail.smtp.host",smtpHost); 
        
        
        mailProperties.put("mail.smtp.auth","false"); 
        //mailProperties.put("mail.debug", "true");
        mailProperties.put("mail.smtp.starttls.enable","true");
        /*
        authenticator = new MailAuthenticator(username,password); 
        //passwordAuth = authenticator.performCheck(username, password);
        mailSession = Session.getDefaultInstance(mailProperties,authenticator); 
        */
        //mailSession = Session.getDefaultInstance(mailProperties,passwordAuth);
        
        mailSession = Session.getDefaultInstance(mailProperties); 
        mailMessage = new MimeMessage(mailSession); 
        
        multipart = new MimeMultipart();
    } 
     
    public void setSubject(String mailSubject)throws MessagingException{ 
        //this.mailSubject = mailSubject; 
        mailMessage.setSubject(mailSubject); 
        if (logger.isDebugEnabled())
            logger.debug("Mail subject:"+mailSubject);
    } 
   
    public void setHtmlMailContent(String mailContent)throws MessagingException
    {
        //System.out.println("mail add content:"+mailContent);
        MimeBodyPart messageBodyPart = new MimeBodyPart(); 
        messageBodyPart.setContent(mailContent, "text/html");
        //messageBodyPart.setText(mailContent); 
        multipart.addBodyPart(messageBodyPart); 
        
        if (logger.isDebugEnabled())
            logger.debug("Mail content:"+mailContent);
    }
    
    public void setMailContent(String mailContent)throws MessagingException
    {
        //System.out.println("mail add content:"+mailContent);
        MimeBodyPart messageBodyPart = new MimeBodyPart(); 
        messageBodyPart.setText(mailContent); 
        multipart.addBodyPart(messageBodyPart); 
        
        if (logger.isDebugEnabled())
            logger.debug("Mail content:"+mailContent);
    }
    
    
    public void setSendDate(Date sendDate)throws MessagingException{ 
        //this.mailSendDate = sendDate; 
        mailMessage.setSentDate(sendDate); 
    } 
    
    
    public void setAttachments(String attachmentName)throws MessagingException{ 
        MimeBodyPart messageBodyPart = new MimeBodyPart(); 
        DataSource source = new FileDataSource(attachmentName); 
        messageBodyPart.setDataHandler(new DataHandler(source)); 
        //int index = attachmentName.lastIndexOf('\\'); 
        //String attachmentRealName = attachmentName.substring(index+1); 
        //messageBodyPart.setFileName(attachmentRealName); 
        messageBodyPart.setFileName(source.getName()); 
        //System.out.println("mail add attachement:"+source.getName());
        multipart.addBodyPart(messageBodyPart); 
        
        if (logger.isDebugEnabled())
            logger.debug("Mail attachement:"+attachmentName);
        
    } 
    
    public void setMailFrom(String mailFrom)throws MessagingException{ 
        mailFromAddress = new InternetAddress(mailFrom); 
        mailMessage.setFrom(mailFromAddress); 
    } 
    
    public void setMailTo(String[] to, String[] cc, String[] bcc)
    throws MessagingException, AddressException
    {
        if (to!=null)
        for (String mail:to)
        {
        	if (mail!=null && mail.trim().length()>5)
        	{
        	mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail)); 
        	if (logger.isDebugEnabled())
                logger.debug("Mail to:"+mail);
        	}
        }
        if (cc!=null)
        for (String mail:cc)
        {
        	mailMessage.addRecipient(Message.RecipientType.CC,new InternetAddress(mail)); 
        	if (logger.isDebugEnabled())
                logger.debug("Mail cc:"+mail);
        }
        /*
        if (bcc!=null)
        for (String mail:bcc)
        {
        	mailMessage.addRecipient(Message.RecipientType.BCC,new InternetAddress(mail)); 
        	if (logger.isDebugEnabled())
                logger.debug("Mail bcc:"+mail);
        }
        */
            
    } 
    
    public void setMailTo(List<String> to, List<String> cc, List<String> bcc)
    throws MessagingException, AddressException
    {
        if (to!=null)
        for (String mail:to)
        {
        	if (mail!=null && mail.trim().length()>5)
        	{
        	mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail)); 
        	if (logger.isDebugEnabled())
                logger.debug("Mail to:"+mail);
        	}
        }
        if (cc!=null)
        for (String mail:cc)
        {
        	mailMessage.addRecipient(Message.RecipientType.CC,new InternetAddress(mail)); 
        	if (logger.isDebugEnabled())
                logger.debug("Mail cc:"+mail);
        }
     
        /*
        if (bcc!=null)
        for (String mail:bcc)
        {
        	mailMessage.addRecipient(Message.RecipientType.BCC,new InternetAddress(mail)); 
        	if (logger.isDebugEnabled())
                logger.debug("Mail bcc:"+mail);
        }
        */

            
    } 
  
    public void sendMail()throws MessagingException,SendFailedException{ 
        /*if(mailToAddress == null){ 
            throw new SendFailedException("Mail to Address is null");
        } 
        else{*/ 

            mailMessage.setContent(multipart); 
            Transport.send(mailMessage); 
            //Logger.log("Send mail ok", Logger.INFO_LEVEL);
            logger.debug("Send mail ok");
        //} 
    } 

}
