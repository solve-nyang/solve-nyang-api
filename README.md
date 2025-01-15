# _be
## 📝 Develop Rule

---

**main branch에 push 하지말 것!**

## Branch Convention

  - feature/
    - 새로운 기능 개발
    예: feature/login, feature/payment

  - fix/ 또는 bugfix/
    - 일반적인 버그 수정
    예: fix/login-error, bugfix/typo

  - hotfix/
    - 운영 중인 버전에서 발생한 긴급한 버그 수정
    예: hotfix/security-issue

  - chore/
    - 소스 코드를 제외한 빌드 스크립트, 개발 환경 설정 등의 변경
    예: chore/update-dependencies

  - refactor/
    - 코드 리팩토링
    예: refactor/auth-logic

  - test/
    - 테스트 코드 추가/수정
    예: test/user-service

  - docs/
    - 문서 작업
    예: docs/api-guide

  - style/
    - 코드 포맷팅, 세미콜론 등 스타일 관련 수정
    예: style/indent-fix
  
## 🚩 Commit Convention

---

### Commit Message Structure

- 기본적인 commit message 구조

  - 각 파트는 빈 줄로 구분

    > 제목 (Type: Subject) <br />
    > (공백) <br />
    > 본문 (Body) <br />
    > (공백) <br />
    > Footer

  - Subject
    - 제목은 50자 이내
    - 마침표 및 특수기호 사용 금지
    - 영문인 경우 동사(원형)을 가장 앞에, 첫 글자는 대문자로 작성
  - Body
    - 72자 이내로 작성
    - 최대한 상세히 작성
    - 무엇을, 왜 변경했는지 작성
  - Footer(optional)
    - issue tracker ID 명시하고 싶은 경우 작성
    - 유형: #이슈 번호 형식으로 작성
    - 이슈 트래커 유형
      - Fixes: 수정 중(아직 해결되지 않은 경우)
      - Resolves: 이슈를 해결
      - Ref: 참고할 이슈가 있을 때 사용
      - Related to: 관련된 이슈 번호

### Commit Type

| Tag Name |                            Description                             |
| :------: | :----------------------------------------------------------------: |
|   feat   |                          새로운 기능 추가                          |
|   fix    |                             버그 수정                              |
|   docs   |                             문서 수정                              |
|  style   |                 코드 포맷팅, 코드 변경이 없는 경우                 |
| refactor |                       프로덕션 코드 리팩토링                       |
|   test   |                          테스트 코드 추가                          |
|  chore   | 빌드 업무 수정, 패키지 매니저 수정, 프로덕션 코드 변경이 없는 경우 |
|  remove  |                       파일을 삭제만 한 경우                        |
|  rename  |     파일 또는 디렉터리 명을 수정하거나 옮기는 작업만 있는 경우     |

### 예시

```
fix: 사용자 정보 조회 시 발생하는 NullPointerException 수정

사용자 정보가 없을 경우 기본값 반환하도록 로직 변경

---------------------------------------------------------
feat: 사용자 정보 조회 기능 추가

마이페이지에서 사용하기 위한
사용자 정보를 ID를 기준으로 조회 하는 메서드 추가
MyPageDTO에 정보를 담아 반환
```

## 🚩 Naming Convention

---

- Branch Name

  - 일반적으로 목적을 나타내는 이름을 사용

    - 기능 개발: `feature/{name}/{feature name}`

    - 버그 수정: `fix/{name}/{feature name}`

- File Name

  - Class

    - PascalCase

    - ex: `UserController.java, PaymentService.java`

  - Interface

    - PascalCase

    - 'I' 접두사 사용 가능

    - ex: `UserRepository.java 또는 IUserRepository.java`

- Test

  - 테스트 대상 클래스 이름 + "Test"

  - ex: `UserControllerTest.java`

- Variable or Method

  - Variable

    - camelCase

    - ex: `userId, paymentAmount`

  - Method

    - camelCase

    - 동사로 시작

    - ex: `getUserById(), processPayment()`

  - Constant
    - UPPER_SNAKE_CASE
    - ex: `MAX_RETRY_COUNT, API_BASE_URL`

- Pagkage

  - 모두 소문자 사용
  - ex: `com.company.project.controller`

- Database

  - Table Name

    - snake_case + 복수형

    - ex: `users, payment_transactions`

  - Column Name

    - snake_case

    - ex: `id, created_at`

- API Endpoint

  - RESTful 규칙 따르기

  - ex: `/users, /payments`

- Config files

  - 환경별 설정 파일 사용

  - ex: `application-local.properties, application-prod.properties`
