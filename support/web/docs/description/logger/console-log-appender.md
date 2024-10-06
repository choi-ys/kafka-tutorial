# console-appender.xml

> console에 출력할 로그의 형태를 지정합니다.

- 로그 출력 시 발생 시간, 쓰레드, 로그 레벨, 클래스, 라인 정보를 포함 할 수 있도록 pattern을 구성합니다.
- 색상 옵션을 적용하여 각 요소를 직관적으로 파악 할 수 있습니다.

## 주요 설정

| 옵션                            | 설명                                                     | 출력 예시                                    |
|-------------------------------|--------------------------------------------------------|------------------------------------------|
| `%d{yyyy-MM-dd HH:mm:ss.SSS}` | 로그 발생 시간 출력                                            | 2024-10-02 23:23:18.654                  |
| `[%13.13t]`                   | 쓰레드 이름 출력 - 13자리로 고정 후 우측 정렬(초과 시 축약)                  | [         main]                          |
| `%-5level`                    | 로그 레벨 출력 - TRACE / DEBUG / INFO / WARN / ERROR / FATAL | INFO                                     |
| `%35.35logger{35}`            | 로거 이름 출력 - 35자리로 고정 후 우측 정렬(초과 시 축약)                   | mbedded.tomcat.TomcatWebServer           |
| `:%4L`                        | 로깅 호출 라인 번호 출력                                         | : 111                                    |
| `- %msg%n`                    | 로그 메시지 출력                                              | Tomcat initialized with port 8081 (http) |

## 출력 예시

```log
2024-10-02 23:23:17.828 [         main] INFO  mbedded.tomcat.TomcatWebServer: 111 - Tomcat initialized with port 8081 (http)
```
