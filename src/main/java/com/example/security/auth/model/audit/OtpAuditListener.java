package com.example.security.auth.model.audit;


import com.example.security.auth.model.entity.Otp;
import com.example.security.common.utils.CommonUtils;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class OtpAuditListener {

    @PostPersist
    public void PostPersist(Otp value) {
        UUID uuid = UUID.randomUUID();
        value.setUuid(uuid.toString());
        value.setOtpCode(CommonUtils.randomNumber(100000, 999999));// 100000 to 999999
    }

}
