package com.jm.marketplace.util.mail;

import com.jm.marketplace.model.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(@NonNull User user, @NonNull String subject, @NonNull String message) {
        sendMessages(createSimpleMessage(user, subject, message));
    }

    @Override
    public void send(@NonNull User user, @NonNull String subject, @NonNull String message, @NonNull File... files) {
        sendMessages(createMimeMessage(user, subject, message, files));
    }

    @Override
    public void broadcast(@NonNull Collection<User> users, @NonNull String subject, @NonNull String message) {
        List<SimpleMailMessage> messages = users.stream().map(user -> createSimpleMessage(user, subject, message)).collect(Collectors.toList());
        sendMessages(messages.toArray(new SimpleMailMessage[]{}));
    }

    @Override
    public void broadcast(@NonNull Collection<User> users, @NonNull String subject, @NonNull String message, @NonNull File... files) {
        List<MimeMessage> messages = users.stream().map(user -> createMimeMessage(user, subject, message, files)).collect(Collectors.toList());
        sendMessages(messages.toArray(new MimeMessage[]{}));
    }

    private SimpleMailMessage createSimpleMessage(User user, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(message);
        mailMessage.setSubject(subject);
        mailMessage.setFrom("test01marketplace@gmail.com");
        mailMessage.setTo(user.getEmail());
        return mailMessage;
    }

    private MimeMessage createMimeMessage(User user, String subject, String message, File... files) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText(message);
            helper.setSubject(subject);
            helper.setTo(user.getEmail());
            setAttachmentFiles(helper, files);
            return mimeMessage;
        } catch (MessagingException messageException) {
            throw new RuntimeException(
                    String.format("Error with create message: email = %s, subject = %s, message = %s",
                            user.getEmail(), subject, message)
                    , messageException);
        }
    }

    private void sendMessages(MimeMessage... messages) {
        try {
            javaMailSender.send(messages);
        } catch (MailSendException e) {
            throw new RuntimeException("Error send message!", e);
        }
    }

    private void sendMessages(SimpleMailMessage... messages) {
        try {
            javaMailSender.send(messages);
        } catch (MailSendException e) {
            throw new RuntimeException("Error send message!", e);
        }
    }

    private void setAttachmentFiles(MimeMessageHelper helper, File[] files) {
        Arrays.stream(files).forEach(file -> {
            try {
                helper.addAttachment(file.getName(), new FileSystemResource(file));
            } catch (MessagingException e) {
                throw new RuntimeException("Error with create message!", e);
            }
        });
    }
}
