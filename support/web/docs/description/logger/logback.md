Logback
===

# 개요

- Logback을 이용하여 Spring Boot 애플리케이션에서 로그를 관리하는 방법을 설명합니다.
- 로그 출력, 로그 파일 저장, 롤링 정책 등 각 설정의 목적과 동작에 대한 설명을 포함합니다.

---

# logback-spring.xml

- Logback의 설정 파일인 logback-spring.xml을 구성하여 로그 출력과 저장을 처리합니다.
- Application 전체에 적용되는 Root Logger에 적용할 Appender와 공통 설정을 구성합니다.
- 로그는 콘솔과 파일로 나뉘어 출력되며, 로그 파일은 일정 크기 또는 시간이 지나면 롤링(교체)되어 새로운 파일로 저장됩니다.
- INFO 수준의 로그와, WARN 이상의 로그는 별도의 파일로 저장합니다.

## 주요 설정

| 옵션                                                      | 설명                              | 적용 상세                                                              |
|---------------------------------------------------------|---------------------------------|--------------------------------------------------------------------|
| `<include resource="{file-name}"/>`                     | Appender 등의 외부 파일의 설정 적용        | `RollingFileAppender를 기반`으로 로그 출력 패턴과 로그 파일 경로 및 이름을 설정            |
| `<property name="LOG_PATH" value="{path}"/>`            | 로그 파일이 저장될 경로 정의                | ${user.dir}/logs 경로에 로그 저장                                         |
| `<property name="INFO_LOG_PATTERN" value="{pattern}"/>` | INFO 레벨 로그의 출력 패턴 정의            | INFO 로그의 패턴 정의 [console-appender.xml](console-log-appender.md) 참조  |
| `<property name="ERROR_LOG_PATTERN value="{pattern}"/>` | ERROR 레벨 로그의 출력 패턴 정의           | ERROR 로그의 패턴 정의 [console-appender.xml](console-log-appender.md) 참조 |
| `<root level="{log-level}">`                            | Root Logger의 기본 로그 레벨 정의        | Application 전체에 적용되는 Root Logger의 log-level을 INFO 이상으로 설정          |
| `<appender-ref ref="{appender-name}"/>`                 | Root Logger에 정의된 Appender 설정 추가 | 목적에 따라 CONSOLE, ROLLING_INFO, ROLLING_ERROR 정책이 각각 정의된 Appender 적용 |

---

# Appender

- ### [console-appender.xml](console-log-appender.md)
- ### [base-rolling-appender.xml](base-rolling-appender.md)
- ### [rolling-info-appender.xml](rolling-info-appender.md)
- ### [rolling-error-appender.xml](rolling-error-appender.md)

---
