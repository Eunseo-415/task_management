# 테스크 관리 프로그램
간단한 테스크를 개인/팀으로 관리할 수 있는 프로그램 입니다.

### 사용 기술
Spring + Jpa + mail + security, JWT, Redis, AWS, Docker

레디스를 이용해 동시성 제어와 캐싱을 구현했습니다.
Docker 와 AWS(EC2/RDS)를 이용해 배포했습니다.

Swagger UI를 적용했습니다.

배포 링크: ec2-52-63-127-184.ap-southeast-2.compute.amazonaws.com


## 회원
- [x] 회원가입
- [x] 로그인 (토큰 발행)
- [x] 팀 생성
- [x] 팀원 초대 (초대 코드를 팀원의 이메일로 발행)
- [x] 테스크 CRUD
- [ ] 테스크 정렬 (완료 안됌, deadline, 생성일, 중요도)

## 팀
- [x] 테스크 CRUD
- [ ] 동시성 제어 (진행중)
- [ ] 테스크 정렬 (완료 안됌, deadline, 생성일, 중요도)
