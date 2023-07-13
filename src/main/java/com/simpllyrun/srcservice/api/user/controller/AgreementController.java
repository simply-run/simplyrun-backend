package com.simpllyrun.srcservice.api.user.controller;

import com.simpllyrun.srcservice.api.user.dto.AgreementDto;
import com.simpllyrun.srcservice.api.user.dto.UserAgreementDto;
import com.simpllyrun.srcservice.api.user.service.AgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agreements")
@Tag(name = "agreements", description = "서비스 이용 약관 API")
public class AgreementController {

    private final AgreementService agreementService;

    @GetMapping
    @Operation(summary = "서비스 이용 약관 조회")
    @ResponseStatus(HttpStatus.OK)
    public List<AgreementDto> getAgreements() {
        return agreementService.getAgreements();
    }

    @GetMapping("/users")
    @Operation(summary = "사용자 이용 약관 조회")
    @ResponseStatus(HttpStatus.OK)
    public List<UserAgreementDto> getUserAgreements() {
        return agreementService.getUserAgreements();
    }

    @PostMapping("/users")
    @Operation(summary = "사용자 이용 약관 추가")
    public ResponseEntity<Long> addUserAgreements(@Valid @RequestBody List<UserAgreementDto> userAgreementDtos) {
        return ResponseEntity.ok(agreementService.addUserAgreement(userAgreementDtos));
    }

    @PutMapping("/users")
    @Operation(summary = "사용자 이용 약관 수정")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserAgreements(@Valid @RequestBody List<UserAgreementDto> userAgreementDtos) {
        agreementService.updateUserAgreement(userAgreementDtos);
    }
}
