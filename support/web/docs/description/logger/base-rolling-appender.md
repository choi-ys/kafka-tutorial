# base-rolling-appender.xml

> base-rolling-appender는 로그를 파일에 저장하고, 효율적으로 관리하기 위한 공통 설정입니다.

- SizeAndTimeBasedRollingPolicy를 기반으로 로그 파일을 크기 또는 날짜에 따라 롤링(교체)합니다.
- 교체된 로그 파일은 압축하여 저장되며, 특정 개수 또는 전체 파일 크기가 초과되면 오래된 로그 파일을 자동으로 삭제합니다.

| 옵션                  | 설명                    | 적용 상세                                                            |
|---------------------|-----------------------|------------------------------------------------------------------|
| `<rollingPolicy />` | 로그 파일 교체 및 보관 정책      | `SizeAndTimeBasedRollingPolicy`를 통해 크기 또는 날짜 기준으로 로그 파일을 롤링 (교체) |
| `<maxHistory />`    | 보관할 로그 파일의 최대 개수 설정   | `최대 30개`의 `로그 파일 보관`	                                            |
| `<totalSizeCap />`  | 보관 중인 로그 파일의 최대 용량 설정 | `보관 중인 로그 파일의 합이 200MB를 초과`하는 경우 `가장 오래된 로그 파일 삭제`	              |

### 로그 파일 보관 정책

- `최대 30개`의 로그 파일을 압축하여 보관하며, `총 보관 용량이 200MB를 초과`하는 경우 가장 `오래된 파일`부터 `삭제`됩니다.
