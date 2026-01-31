package com.example.security.repositories;

import com.example.security.model.entity.EmailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRecordRepository extends JpaRepository<EmailRecord, Long> {
}
