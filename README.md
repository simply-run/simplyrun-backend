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
- Junit5
- Gradle

## 협업 규칙
- Label을 적극 사용해주세요.
### 브랜치명
- 타입/#이슈번호-세부내용
- 예) feat/#1-oauth
### 이슈 제목
- 내용
- 예) oauth 기능 개발
### PR 제목
- #이슈번호 내용
- 예) #27 oauth 기능 개발

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

## PR 규칙
- merge를 하기 전에는 “오류“가 없는지, “필요없는 파일“이 있는지 확인
- merge를 할 땐 PR에서 동료들의 “확인” 커멘트를 받고 merge를 진행
- PR을 작성할 때는 어떠한 작업을 했는지 대략적으로라도 세부내용을 작성
- PR에서 디테일하게 피드백을 받고싶다면 피드백 요청 라벨
- PR을 올라왔을 때 가급적 빠른시일내로 간단하게 코드를 확인하고 “확인” 코멘트
- 에러가 발생할수 있는 부분이 있는지 우선적으로 확인