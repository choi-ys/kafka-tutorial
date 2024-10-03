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

#### logback-spring.xml

> root logger에 사용할 appender를 설정합니다.

#### console-appender.xml

> console에 출력할 로그 포맷을 설정합니다.

- 로그 출력 시 발생 시간, 쓰레드, 로그 레벨, 클래스, 라인 정보를 포함 할 수 있도록 pattern을 구성합니다.
- 색상 옵션을 적용하여 각 요소를 직관적으로 파악 할 수 있습니다.

| 옵션                            | 설명                                                           | 출력 예시                                    |
|-------------------------------|--------------------------------------------------------------|------------------------------------------|
| `%d{yyyy-MM-dd HH:mm:ss.SSS}` | 로그 발생 시간 출력                                                  | 2024-10-02 23:23:18.654                  |
| `[%13.13t]`                   | 쓰레드 이름 출력 <br/> - 13자리로 고정 후 우측 정렬(초과 시 축약)                  | [         main]                          |
| `%-5level`                    | 로그 레벨 출력 <br/> - TRACE / DEBUG / INFO / WARN / ERROR / FATAL | INFO                                     |
| `%35.35logger{35}`            | 로거 이름 출력 <br/> - 35자리로 고정 후 우측 정렬(초과 시 축약)                   | mbedded.tomcat.TomcatWebServer           |
| `:%4L`                        | 로깅 호출 라인 번호 출력                                               | : 111                                    |
| `- %msg%n`                    | 로그 메시지 출력                                                    | Tomcat initialized with port 8081 (http) |

```log
2024-10-02 23:23:17.828 [         main] INFO  mbedded.tomcat.TomcatWebServer: 111 - Tomcat initialized with port 8081 (http)
```

#### rolling-error-appender.xml

>

#### rolling-info-appender.xml

> 
