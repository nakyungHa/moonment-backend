# Moonment Backend

UNIS 실전창업프로젝트 Moonment 팀 백엔드 개발 레포지터리

Moonment는 하루의 질문에 답하며 기록을 쌓아가는 서비스입니다. 이 레포지터리는 그 서비스의 REST API 서버(Spring Boot)입니다.

## Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 4.1 (Web MVC, Data JPA, Security, Actuator)
- **Database**: PostgreSQL ([Supabase](https://supabase.com))
- **Auth**: JWT (jjwt) + Google OAuth ID Token 로그인
- **Build Tool**: Gradle
- **External API**: FastAPI 기반 인사이트(Insight) 서버 연동 (RestTemplate)

## Getting Started

### Prerequisites

- JDK 17
- Supabase 프로젝트 (PostgreSQL 데이터베이스)

### Environment Variables

DB 접속 정보 및 시크릿 값은 코드에 포함하지 않고 환경 변수로 주입합니다. 로컬에서 실행 전 아래 값을 설정하세요.

| 변수명 | 설명 |
| --- | --- |
| `DB_URL` | Supabase PostgreSQL JDBC URL (예: `jdbc:postgresql://<host>:5432/postgres`) |
| `DB_USERNAME` | Supabase DB 사용자명 |
| `DB_PASSWORD` | Supabase DB 비밀번호 |
| `JWT_SECRET` | JWT 서명에 사용할 시크릿 키 |
| `FASTAPI_BASE_URL` | 인사이트 생성용 FastAPI 서버 base URL |
| `FASTAPI_CONNECT_TIMEOUT_MS` | (선택) FastAPI 연결 타임아웃, 기본값 5000 |
| `FASTAPI_READ_TIMEOUT_MS` | (선택) FastAPI 응답 타임아웃, 기본값 120000 |
| `PORT` | (선택) 서버 포트, 기본값 8080 |

DB 접속 정보는 Supabase 프로젝트의 **Settings → Database → Connection string**에서 확인할 수 있습니다.

### Run Locally

```bash
export DB_URL=jdbc:postgresql://<host>:5432/postgres
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
export FASTAPI_BASE_URL=http://localhost:8000

./gradlew bootRun
```

### Test

```bash
./gradlew test
```

## API Overview

| Method | Endpoint | 설명 |
| --- | --- | --- |
| GET | `/api/auth/check-id` | 아이디 중복 확인 |
| POST | `/api/auth/signup` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |
| GET | `/api/users/me` | 내 프로필 조회 |
| PATCH | `/api/users/me` | 내 프로필 수정 |
| GET | `/api/home` | 홈 화면 데이터 조회 |
| GET | `/api/questions/today` | 오늘의 질문 조회 |
| GET | `/api/questions/{id}` | 질문 상세 조회 |
| POST | `/api/answers` | 답변 저장 |
| GET | `/api/answers/{recordDate}` | 날짜별 답변 조회 |
| GET | `/api/archive/calendar` | 아카이브 캘린더 조회 |
| GET | `/api/archive/{date}` | 날짜별 아카이브 상세 조회 |
| GET | `/api/archive/insight/{weekStart}` | 주간 인사이트 조회 |

`/api/auth/check-id`, `/api/auth/signup`, `/api/auth/login`을 제외한 모든 엔드포인트는 JWT 인증이 필요합니다.

## Project Structure

```
src/main/java/com/moonment
├── config      # Security, JWT, RestTemplate 설정
├── controller  # REST API 엔드포인트
├── dto         # 요청/응답 DTO
├── entity      # JPA 엔티티
├── enums       # 도메인 enum
├── repository  # Spring Data JPA 리포지토리
└── service     # 비즈니스 로직
```
