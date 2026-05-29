# TripCalendar

여행 공금 / 지출 정산 공용 캘린더 서비스

---

## 주요 기능

### 여행 그룹
- 여행 생성 및 기간 설정
- 멤버 초대 / 탈퇴
- 여행별 대시보드 (공금 현황, 지출 현황, 정산 요약)

### 공금 관리
- 멤버별 공금 납입 기록
- 공금 총액 및 개인별 납입 현황 조회

### 지출 기록 (캘린더)
- 날짜별 지출 항목 등록 / 수정 / 삭제
- 카테고리별 분류 (식비, 교통, 숙박, 관광, 기타)
- 결제자 지정
- 캘린더 뷰로 일자별 지출 조회

### 정산 계산
- 1/N 균등 정산
- 지출 기반 자동 정산 (공금 납입액 - 지출 분담액)
- 정산 결과: 누가 누구에게 얼마를 보내야 하는지 계산

### 사용자 관리
- 회원가입 / 로그인

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Backend | Spring Boot 4.0.6 |
| Language | Java 21 |
| Database | H2 |
| Build Tool | Gradle |
| Controller | Spring MVC Controller |
| Template | Mustache |

### Dependencies

- Spring Web MVC
- Spring Data JPA
- Mustache
- H2 Database
- Lombok

---

## 프로젝트 구조 (예정)

```
TripCalendar/
├── src/
│   ├── main/
│   │   ├── java/com/example/TripCalendar/
│   │   │   ├── user/          # 사용자 (회원가입, 로그인)
│   │   │   ├── trip/          # 여행 그룹
│   │   │   ├── fund/          # 공금 납입
│   │   │   ├── expense/       # 지출 내역
│   │   │   └── settlement/    # 정산 계산
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── build.gradle
└── README.md
```

---

## 팀 구성 (3인 미니 프로젝트)

| 팀원 | 담당 영역 | 상세 작업 |
|------|-----------|-----------|
| 이재근님 | 회원가입/로그인, 캘린더 | 회원가입 · 로그인 · 로그아웃 처리, 세션 인증, 캘린더 메인 화면, 여행 일정 캘린더 표시, 공통 레이아웃 |
| 이동건님 | 지출 관리, 정산 | 지출 등록 · 수정 · 삭제, 카테고리 · 결제자 관리, 정산 계산 로직, 정산 결과 조회 |
| 김다애님 | 여행 관리 | 여행 생성 · 조회, 멤버 초대 · 탈퇴, 공금 납입 기록 · 현황 조회, 여행 대시보드 |

---

## ERD

```
User ──< TripMember >── Trip
              │
              │
         Fund (공금 납입)
         Expense (지출 내역) ──< ExpenseShare (분담)
```

| 테이블 | 설명 |
|--------|------|
| `User` | 사용자 계정 |
| `Trip` | 여행 정보 (이름, 기간) |
| `TripMember` | 여행 참여 멤버 (User-Trip 다대다) |
| `Fund` | 공금 납입 기록 (납입자, 금액, 날짜) |
| `Expense` | 지출 항목 (결제자, 금액, 카테고리, 날짜) |
| `ExpenseShare` | 지출 분담 (Expense당 각 멤버 부담액) |

---

## URL 설계 (예정)

### 인증
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/auth/register` | 회원가입 페이지 |
| POST | `/auth/register` | 회원가입 처리 |
| GET | `/auth/login` | 로그인 페이지 |
| POST | `/auth/login` | 로그인 처리 |
| POST | `/auth/logout` | 로그아웃 |

### 여행
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/trips` | 내 여행 목록 |
| GET | `/trips/new` | 여행 생성 페이지 |
| POST | `/trips` | 여행 생성 처리 |
| GET | `/trips/{id}` | 여행 대시보드 |
| POST | `/trips/{id}/members` | 멤버 초대 |
| POST | `/trips/{id}/members/{userId}/delete` | 멤버 탈퇴 |

### 공금
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/trips/{id}/funds` | 공금 현황 페이지 |
| POST | `/trips/{id}/funds` | 공금 납입 기록 |
| POST | `/trips/{id}/funds/{fundId}/delete` | 공금 기록 삭제 |

### 지출
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/trips/{id}/expenses` | 지출 목록 (캘린더) |
| GET | `/trips/{id}/expenses/new` | 지출 등록 페이지 |
| POST | `/trips/{id}/expenses` | 지출 등록 처리 |
| GET | `/trips/{id}/expenses/{expenseId}/edit` | 지출 수정 페이지 |
| POST | `/trips/{id}/expenses/{expenseId}/edit` | 지출 수정 처리 |
| POST | `/trips/{id}/expenses/{expenseId}/delete` | 지출 삭제 |

### 정산
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/trips/{id}/settlement` | 정산 결과 페이지 |

---

## 개발 단계

1. **베이스 세팅** - Spring Boot 초기화, DB 연결, 공통 구조
2. **사용자 기능** - 회원가입 / 로그인 / 인증
3. **여행 그룹** - 여행 생성, 멤버 관리
4. **공금 관리** - 납입 기록 CRUD
5. **지출 기록** - 지출 CRUD, 캘린더 뷰
6. **정산 계산** - 정산 로직 구현
7. **통합 테스트 및 마무리**
