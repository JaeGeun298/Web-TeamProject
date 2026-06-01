# TripCalendar

여행 공금 / 지출 정산 공용 캘린더 서비스

---

## 주요 기능

### 캘린더
- 로그인 후 메인 화면에서 내 여행 일정 캘린더 표시
- 여행별 지출 항목을 캘린더에 이벤트로 표시 (REST API 연동)

### 여행 관리
- 여행 생성 / 수정 / 삭제
- 여행 기간(시작일~종료일) 및 설명 설정
- 멤버 초대 / 탈퇴
- 여행 대시보드 (멤버 현황, 각 기능 링크)

### 공금 관리
- 멤버별 공금 납입 기록 / 삭제
- 공금 총액 및 납입 현황 조회

### 지출 관리
- 지출 등록 / 수정 / 삭제
- 카테고리별 분류 (식비, 교통, 숙박, 쇼핑, 관광, 기타)
- 결제자 지정 및 날짜 기록
- 날짜 / 카테고리 필터 조회
- 카테고리별 지출 합계 요약
- 정산 완료 시 수정·삭제 잠금

### 정산
- 총 지출액 및 1인당 분담액 계산 (인원 수 직접 조정 가능)
- 결제자별 잔액 표시 (지출액 - 1/N, 양수=돌려받을 금액, 음수=내야 할 금액)
- 카테고리별 지출 현황 요약
- 정산 확정 / 해제 기능 (확정 후 지출 수정 불가)

### 사용자 관리
- 회원가입 / 로그인 / 로그아웃
- 비밀번호 변경
- 회원 탈퇴
- 세션 기반 인증 (HttpSession)
- 중복 아이디 방지

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Backend | Spring Boot 4.0.6 |
| Language | Java 21 |
| Database | H2 (In-Memory) |
| ORM | Spring Data JPA |
| Build Tool | Gradle |
| Template | Mustache |
| Auth | HttpSession |

### Dependencies

- Spring Web MVC
- Spring Data JPA
- Mustache
- H2 Database
- Lombok

---

## 프로젝트 구조

```
TripCalendar/
├── src/
│   ├── main/
│   │   ├── java/com/example/TripCalendar/
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CalendarController.java
│   │   │   │   ├── ExpenseApiController.java
│   │   │   │   ├── ExpenseController.java
│   │   │   │   ├── FundController.java
│   │   │   │   ├── SettlementController.java
│   │   │   │   └── TripController.java
│   │   │   ├── dto/
│   │   │   │   ├── CalendarTripDTO.java
│   │   │   │   ├── FundFormDTO.java
│   │   │   │   ├── LoginDTO.java
│   │   │   │   ├── RegisterDTO.java
│   │   │   │   └── TripFormDTO.java
│   │   │   ├── entity/
│   │   │   │   ├── Expense.java
│   │   │   │   ├── Fund.java
│   │   │   │   ├── Trip.java
│   │   │   │   ├── TripMember.java
│   │   │   │   └── UserEntity.java
│   │   │   ├── repository/
│   │   │   │   ├── ExpenseRepository.java
│   │   │   │   ├── FundRepository.java
│   │   │   │   ├── TripMemberRepository.java
│   │   │   │   ├── TripRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/
│   │   │   │   ├── FundService.java
│   │   │   │   ├── SettlementService.java
│   │   │   │   ├── TripService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── UserServiceImpl.java
│   │   │   └── TripCalendarApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── auth/          # register.mustache, login.mustache, profile.mustache
│   │       │   ├── expense/       # list.mustache, edit.mustache
│   │       │   ├── fund/          # list.mustache
│   │       │   ├── layouts/       # header.mustache, footer.mustache, gnb.mustache
│   │       │   ├── settlement/    # result.mustache
│   │       │   ├── trip/          # list.mustache, form.mustache, detail.mustache, edit.mustache
│   │       │   └── calendar.mustache
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
├── build.gradle
└── README.md
```

---

## 팀 구성 (3인 미니 프로젝트)

| 팀원 | 담당 영역 | 상세 작업 |
|------|-----------|-----------|
| 이재근님 | 회원가입/로그인, 캘린더 | 회원가입 · 로그인 · 로그아웃 처리, 세션 인증, 캘린더 메인 화면, 여행 일정 캘린더 표시, 공통 레이아웃 |
| 이동건님 | 지출 관리, 정산 | 지출 등록 · 수정 · 삭제, 카테고리 · 결제자 관리, 정산 계산 로직, 정산 결과 조회, 정산 확정/해제 |
| 김다애님 | 여행 관리 | 여행 생성 · 수정 · 삭제 · 조회, 멤버 초대 · 탈퇴, 공금 납입 기록 · 현황 조회, 여행 대시보드 |

---

## ERD

```
User ──< TripMember >── Trip
                         │
                    Fund (공금 납입)
                    Expense (지출 내역)
```

| 테이블 | 설명 |
|--------|------|
| `users` | 사용자 계정 (username, password) |
| `trips` | 여행 정보 (title, startDate, endDate, description, settled) |
| `trip_member` | 여행 참여 멤버 (User-Trip 다대다) |
| `fund` | 공금 납입 기록 (납입자, 금액, 날짜) |
| `expense` | 지출 항목 (title, price, travelDate, payer, category) |

---

## URL 설계

### 인증
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/auth/register` | 회원가입 페이지 |
| POST | `/auth/register` | 회원가입 처리 |
| GET | `/auth/login` | 로그인 페이지 |
| POST | `/auth/login` | 로그인 처리 |
| POST | `/auth/logout` | 로그아웃 |
| GET | `/auth/profile` | 내 정보 페이지 |
| POST | `/auth/profile` | 비밀번호 변경 처리 |
| POST | `/auth/delete` | 회원 탈퇴 처리 |

### 캘린더
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/` | 캘린더 메인 (로그인 필요) |
| GET | `/api/trips/calendar` | 내 여행 목록 API (JSON) |
| GET | `/api/expenses/trip/{tripId}` | 여행별 지출 목록 API (JSON) |

### 여행
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/trips` | 내 여행 목록 |
| GET | `/trips/new` | 여행 생성 페이지 |
| POST | `/trips` | 여행 생성 처리 |
| GET | `/trips/{id}` | 여행 대시보드 |
| GET | `/trips/{id}/edit` | 여행 수정 페이지 |
| POST | `/trips/{id}/edit` | 여행 수정 처리 |
| POST | `/trips/{id}/delete` | 여행 삭제 |
| POST | `/trips/{id}/members` | 멤버 초대 |
| POST | `/trips/{id}/members/{memberId}/delete` | 멤버 탈퇴 |

### 공금
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/trips/{tripId}/funds` | 공금 현황 페이지 |
| POST | `/trips/{tripId}/funds` | 공금 납입 기록 |
| POST | `/trips/{tripId}/funds/{fundId}/delete` | 공금 기록 삭제 |

### 지출
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/expenses/trip/{tripId}` | 지출 목록 (날짜·카테고리 필터) |
| POST | `/expenses/create` | 지출 등록 처리 |
| GET | `/expenses/{id}/edit` | 지출 수정 페이지 |
| POST | `/expenses/{id}/update` | 지출 수정 처리 |
| POST | `/expenses/{id}/delete` | 지출 삭제 |

### 정산
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/settlements/trip/{tripId}` | 정산 결과 페이지 |
| POST | `/settlements/trip/{tripId}/settle` | 정산 확정 |
| POST | `/settlements/trip/{tripId}/unsettle` | 정산 해제 |

