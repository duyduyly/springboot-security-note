package com.example.security.auth.repository;

import com.example.security.auth.model.entity.EmailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRecordRepository extends JpaRepository<EmailRecord, Long> {
}
