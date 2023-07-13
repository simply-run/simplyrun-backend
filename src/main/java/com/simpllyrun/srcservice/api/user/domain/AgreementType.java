package com.simpllyrun.srcservice.api.user.domain;

public enum AgreementType {
    TERMS_OF_SERVICE("이용 약관 동의"),
    PRIVACY_POLICY("개인정보 처리방침 동의"),
    LOCATION_SERVICE("위치기반 서비스 이용약관 동의"),
    OPTIONAL_AGREEMENT("선택 약관 동의"),
    EVENT_ADVERTISEMENT("이벤트 및 광고 수신 동의");

    private final String displayName;
    AgreementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
