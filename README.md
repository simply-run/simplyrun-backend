# SimplyRun - A simple running
심플리런 SNS 기반 '종합 커뮤니티 플랫폼 서비스' 

## 데모 사이트
[심플리런](http://simply-rn.com)

### 기술 스택
- Java17
- Springboot3.0.5
- Swagger v3
- Mysql or postgresql(H2 비용 최소화를 위해 db를 올리게 된다면 그 때 db 적용)
- JPA

## git 방식
- git flow 방식을 사용합니다.
- master, develop, feature 브랜치 사용합니다.
- pr은 develop 브랜치로 최소 1명 이상의 리뷰어가 필요합니다.

## git 커밋 메시지
- {type} : {subject}
- Feat : 새로운 기능 추가
- Fix : 버그 수정
- Refactor : 코드 리팩토링
- Docs : 문서 수정
- Style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- Test : 테스트 코드, 리팩토링 테스트 코드 추가
- Chore : 빌드 업무 수정, 패키지 매니저 수정
- Etc : 그 외 자잘한 수정