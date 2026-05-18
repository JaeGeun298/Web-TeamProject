# TodoCalendar

개인 및 그룹 공용 Todo 캘린더 서비스

---

## 프로젝트 개요

개인 할 일 관리와 그룹 단위의 공용 Todo 캘린더를 통합 제공하는 웹 애플리케이션입니다.  
사용자는 자신의 개인 캘린더를 관리하는 동시에, 그룹에 참여하여 팀원들과 일정을 공유하고 협업할 수 있습니다.

---

## 주요 기능

### 개인 캘린더
- 개인 Todo 항목 등록 / 수정 / 삭제
- 날짜별 일정 조회
- 완료 여부 체크

### 그룹 공용 캘린더
- 그룹 생성 및 초대
- 그룹 내 공용 Todo 등록 / 수정 / 삭제
- 그룹원 간 일정 공유 및 열람
- 담당자 지정

### 사용자 관리
- 회원가입 / 로그인
- 그룹 멤버 관리 (초대 / 탈퇴)

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Backend | Spring Boot |
| Language | Java |
| Database | (추후 결정) |
| Build Tool | Gradle / Maven |
| API | REST API |

---

## 프로젝트 구조 (예정)

```
TodoCalendar/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/todocalendar/
│   │   │       ├── user/          # 사용자 관리
│   │   │       ├── todo/          # 개인 Todo
│   │   │       ├── group/         # 그룹 관리
│   │   │       └── calendar/      # 공용 캘린더
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── build.gradle
└── README.md
```

---

## 팀 구성 (3인 미니 프로젝트)

| 역할 | 담당 영역 |
|------|-----------|
| 팀원 1 | (추후 결정) |
| 팀원 2 | (추후 결정) |
| 팀원 3 | (추후 결정) |

> 베이스 코드 완성 후 기능 단위로 역할 분담 예정

---

## ERD (예정)

```
User ──< GroupMember >── Group
 │                          │
 └── PersonalTodo      GroupTodo
```

- `User`: 사용자 계정 정보
- `Group`: 그룹 정보
- `GroupMember`: 사용자-그룹 다대다 관계
- `PersonalTodo`: 개인 Todo (User FK)
- `GroupTodo`: 공용 Todo (Group FK, 담당자 FK)

---

## API 설계 (예정)

### 인증
| Method | URI | 설명 |
|--------|-----|------|
| POST | `/api/auth/register` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |

### 개인 Todo
| Method | URI | 설명 |
|--------|-----|------|
| GET | `/api/todos` | 개인 Todo 목록 조회 |
| POST | `/api/todos` | Todo 생성 |
| PUT | `/api/todos/{id}` | Todo 수정 |
| DELETE | `/api/todos/{id}` | Todo 삭제 |

### 그룹
| Method | URI | 설명 |
|--------|-----|------|
| POST | `/api/groups` | 그룹 생성 |
| GET | `/api/groups/{id}` | 그룹 조회 |
| POST | `/api/groups/{id}/invite` | 멤버 초대 |
| GET | `/api/groups/{id}/todos` | 그룹 Todo 목록 |
| POST | `/api/groups/{id}/todos` | 그룹 Todo 생성 |
| PUT | `/api/groups/{id}/todos/{todoId}` | 그룹 Todo 수정 |
| DELETE | `/api/groups/{id}/todos/{todoId}` | 그룹 Todo 삭제 |

---

## 개발 단계

1. **베이스 세팅** - Spring Boot 프로젝트 초기화, DB 연결, 공통 구조 설계
2. **사용자 기능** - 회원가입 / 로그인 / 인증
3. **개인 캘린더** - 개인 Todo CRUD
4. **그룹 기능** - 그룹 생성 / 멤버 관리
5. **공용 캘린더** - 그룹 Todo CRUD / 공유 기능
6. **통합 테스트 및 마무리**
