package com.bilev.service.impl;

import com.bilev.dao.api.UserDao;
import com.bilev.dto.UserDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.User;
import com.bilev.service.api.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mailService")
public class MailServiceImpl implements MailService, ServiceErrors {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDao userDao;

    private final String newPwdLink = "http://localhost:8181/n42/newPassword?";


    @Override
    @Transactional(readOnly = true)
    public void sendNewPwdLink(String email, String token) throws OperationFailed {

        try {
            if (email == null || token == null) throw new OperationFailed(USER_NOT_FOUND);

            User user = userDao.findClientByEmail(email);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("New password");
            mailMessage.setText(newPwdLink + "userId=" + user.getId() + "&token=" + token);

            mailSender.send(mailMessage);

        } catch (Exception e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }


    }
}
