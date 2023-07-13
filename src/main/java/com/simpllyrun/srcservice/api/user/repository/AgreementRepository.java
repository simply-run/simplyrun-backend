package com.simpllyrun.srcservice.api.user.repository;

import com.simpllyrun.srcservice.api.user.domain.Agreement;
import com.simpllyrun.srcservice.api.user.domain.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

}
