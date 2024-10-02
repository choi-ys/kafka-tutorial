MODULE WEB-SUPPORT, 웹 계층 지원 모듈
===

# 개요

Multi module 환경에서 web service를 제공하는 application에 공통으로 사용되는 기능과 설정을 지원합니다.

# 구성

- BusinessException
- ExceptionHandler
- CommonErrorResponse
- ObjectMapper
- Logback
- Utils
- Presentation Test Base

## Logback

### 개요

로그를 출력하기 위한 설정과 파일로 저장하기 위한 정책을 설정합니다.
로그 출력 수준에 따라 CONSOLE / INFO / ERROR로 분리된 Appender를 정의합니다.
