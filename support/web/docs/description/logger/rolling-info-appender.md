# rolling-info-appender.xml

> INFO 수준의 로그를 파일로 저장하고 관리하기 위한 설정입니다.

- RollingFileAppender를 기반으로 INFO 수준의 로그를 파일로 저장하기 위한 설정을 적용합니다.
- [base-rolling-appender.xml](base-rolling-appender.md)에 정의된 SizeAndTimeBasedRollingPolicy를 기반으로 INFO 수준의 로그 파일을 관리하기 위한 정책을 정의 합니다.

## 주요 설정

| 옵션                    | 설명                  | 적용 상세                                                       |
|-----------------------|---------------------|-------------------------------------------------------------|
| `<appender />`        | 로그 파일 저장 설정         | `RollingFileAppender를 기반`으로 로그 출력 패턴과 로그 파일 경로 및 파일 이름을 설정  |
| `<pattern />`         | 로그 출력 패턴 설정         | logback-spring.xml에 정의된 `INFO_LOG_PATTERN` 적용               |
| `<file />`            | 로그 파일 경로 및 이름 설정    | logback-spring.xml에 정의된 `LOG_PATH` 경로의 `info.log` 파일에 로그 저장 |
| `<filter />`          | 로그 레벨 필터            | `ThresholdFilter를 기반`으로 INFO 수준의 로그 추출                      |
| `<rollingPolicy />`   | 로그 파일 교체 및 보관 정책    | `SizeAndTimeBasedRollingPolicy`를 기반으로 로그 파일 롤링(교체) 정책 정의    |
| `<fileNamePattern />` | 보관할 로그 파일의 이름 패턴 설정 | 지정된 경로에 지정된 형식으로 로그 파일을 압축하여 보관                             |
| `<maxFileSize />`     | 보관할 로그 파일의 최대 크기 설정 | info.log의 크기가 `20MB를 초과`하면 `새로운 파일로 롤링(교체)`	                |

## 오류 로그 롤링 및 보관 정책

### 로그 파일 롤링 정책

- `info.log 파일`의 크기가 `20MB를 초과`하거나, `날짜가 변경`되면 `새로운 파일로 교체`됩니다.
- `기존 로그`는 `${LOG_PATH}/archived/info 경로`에 `info-날짜.로그 파일 index.log.gz 형식`의 로그 파일로 `이관`된 후 `압축` 되어 `보관`합니다.

### 로그 파일 보관 정책

- [base-rolling-appender.xml](base-rolling-appender.md)에 정의된 로그 파일 보관 정책을 따릅니다.
