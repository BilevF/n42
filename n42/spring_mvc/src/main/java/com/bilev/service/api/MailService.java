package com.bilev.service.api;

import com.bilev.exception.service.OperationFailed;

public interface MailService {

    void sendNewPwdLink(String email, String token) throws OperationFailed;
}
