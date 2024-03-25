package com.springboot.email.service;

import static com.springboot.email.utils.EmailUtils.getEmailMessage;
import static com.springboot.email.utils.EmailUtils.getVerificationUrl;

import com.springboot.email.repository.UserRepository;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
  @Value("${spring.mail.verify.host}")
  private String host;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
  public static final String UTF_8_ENCODING = "UTF-8";
  public static final String EMAIL_TEMPLATE = "emailtemplate";
  public static final String TEXT_HTML_ENCONDING = "text/html";
  private final JavaMailSender emailSender;
  private final TemplateEngine templateEngine;
  private final UserRepository userRepository;

  @Override
  @Async
  public void sendSimpleMailMessage(String name, String to, String token) {
    try {
      var mailMessage = new SimpleMailMessage();
      mailMessage.setSubject("New User Account Verifications");
      mailMessage.setFrom(fromEmail);
      mailMessage.setTo(to);
      mailMessage.setText(getEmailMessage(name,host,token));
      emailSender.send(mailMessage);
    }catch (Exception exception){
      System.out.println(exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
  }

  @Override
  @Async
  public void sendMimeMessageWithAttachments(String name, String to, String token) {
    try {
      var mailMessage = getMimeMessage();
      var helper = new MimeMessageHelper(mailMessage,true, UTF_8_ENCODING);
      helper.setPriority(1);
      helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setText(getEmailMessage(name,host,token));
      //Add attachments
      FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/fort.png"));
      FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/dog.png"));
      FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/homework.docx"));
      FileSystemResource bill = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/DuplicateBill.pdf"));
      helper.addAttachment(fort.getFilename(),fort);
      helper.addAttachment(dog.getFilename(),dog);
      helper.addAttachment(homework.getFilename(),homework);
      helper.addAttachment(bill.getFilename(),bill);
      emailSender.send(mailMessage);
    }catch (Exception exception){
      System.out.println(exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
  }

  @Override
  @Async
  public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
    try {
      MimeMessage message = getMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
      helper.setPriority(1);
      helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setText(getEmailMessage(name, host, token));
      //Add attachments
      FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/fort.png"));
      FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.png"));
      FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
      helper.addInline(getContentId(fort.getFilename()), fort);
      helper.addInline(getContentId(dog.getFilename()), dog);
      helper.addInline(getContentId(homework.getFilename()), homework);
      emailSender.send(message);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
  }

  @Override
  @Async
  public void sendHtmlEmail(String name, String to, String token) {
    try {
      var context = new Context();
      /*context.setVariable("name", name);
      context.setVariable("url", getVerificationUrl(host,token));*/
      context.setVariables(Map.of("name",name,"url",getVerificationUrl(host,token)));
      var text = templateEngine.process(EMAIL_TEMPLATE,context);
      var mailMessage = getMimeMessage();
      var helper = new MimeMessageHelper(mailMessage,true, UTF_8_ENCODING);
      helper.setPriority(1);
      helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setText(text,true);
      //Add attachments
      var fort = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/fort.png"));
      var dog = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/dog.png"));
      var homework = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/homework.docx"));
      var bill = new FileSystemResource(new File(System.getProperty("user.home")+ "/Downloads/images/DuplicateBill.pdf"));
      helper.addAttachment(getContentId(fort.getFilename()),fort);
      helper.addAttachment(getContentId(dog.getFilename()),dog);
      helper.addAttachment(getContentId(homework.getFilename()),homework);
      helper.addAttachment(getContentId(bill.getFilename()),bill);
      emailSender.send(mailMessage);
    }catch (Exception exception){
      System.out.println(exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
  }

  @Override
  @Async
  public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {
    try {
      // Create MimeMessage and MimeMessageHelper
      MimeMessage message = getMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
      helper.setPriority(1);
      helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
      helper.setFrom(fromEmail);
      helper.setTo(to);

      // Create a context for template engine variables
      Context context = new Context();
      context.setVariables(Map.of("name", name, "url", getVerificationUrl(host, token)));
      String text = templateEngine.process(EMAIL_TEMPLATE, context);

      // Create a multipart message for HTML content and embedded images
      MimeMultipart mimeMultipart = new MimeMultipart("related");

      // Add HTML content to the email body
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(text, TEXT_HTML_ENCONDING);
      mimeMultipart.addBodyPart(messageBodyPart);

      // Add inline image to the email body
      MimeBodyPart imageBodyPart = new MimeBodyPart();
      // Construct the file path using the user's home directory and the image file name
      String imagePath = System.getProperty("user.home") + "/Downloads/images/dog.png";

      // Create a FileDataSource with the constructed file path
      DataSource dataSource = new FileDataSource(imagePath);

      // Use the dataSource in your code (e.g., for adding attachments or inline images)
      imageBodyPart.setDataHandler(new DataHandler(dataSource));
      imageBodyPart.setHeader("Content-ID", "<image>"); // Set Content-ID for the image
      mimeMultipart.addBodyPart(imageBodyPart);

      // Set the content of the message as the multipart message
      message.setContent(mimeMultipart);

      // Send the email
      emailSender.send(message);
    } catch (Exception exception) {
      // Handle exceptions and log error messages
      System.out.println("Error sending HTML email with embedded files: " + exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
  }

  //=================ALTERNATIVES==========================
  /*@Override
  @Async
  public void sendMimeMessageWithAttachments(String name, String to, String token, String[] filePaths) {
    try {
      var mailMessage = getMimeMessage();
      var helper = new MimeMessageHelper(mailMessage,true, "UTF-8");
      helper.setPriority(1);
      helper.setSubject("New User Account Verification");
      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setText(getEmailMessage(name,host,token));

      // Add attachments dynamically based on file paths
      for (String path : filePaths) {
        FileSystemResource fileResource = new FileSystemResource(new File(path));
        helper.addAttachment(fileResource.getFilename(), fileResource);
      }

      emailSender.send(mailMessage);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
  }*/


  //-----------------------------------------------------------------------

  private MimeMessage getMimeMessage() {
    return emailSender.createMimeMessage();
  }

  private String getContentId(String filename) {
    return "<" + filename + ">";
  }
}
