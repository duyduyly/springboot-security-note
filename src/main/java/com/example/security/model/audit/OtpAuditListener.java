package com.example.security.model.audit;


import com.example.security.model.entity.OtpCode;
import com.example.security.utils.CommonUtils;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class OtpAuditListener {


    @PostPersist
    public void PostPersist(OtpCode value) {
        UUID uuid = UUID.randomUUID();
        value.setOtpKey(uuid.toString());
        value.setOtpCode(CommonUtils.randomNumber(100000, 999999));// 100000 to 999999
    }

}
