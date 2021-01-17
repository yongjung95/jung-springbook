http://ec2-13-124-125-239.ap-northeast-2.compute.amazonaws.com/

AWS 이용한 배포


###무중단 배포를 진핼할 스크립트

- stop.sh : 기존 엔진엑스에 연결되어 있진 않지만, 실행 중이던 스프링 부트 종료

- start.sh : 배포할 신규 버전 스프링 부트 프로젝트를 stop.sh로 종료한 'profile' 로 실행

- health.sh : 'start.sh' 로 실행시킨 프로젝트가 정상적으로 실행됐는지 체크

- switch.sh : 엔진엑스가 바라보는 스프링 부트를 최신 버전으로 변경

- profile.sh : 앞선 4개 스크립트 파일에서 공용으로 사용할 'profile' 과 포트 체크 로직

#### profile.sh
```
if [ ${RESPONSE_CODE} -ge 400 ]
then
CURRENT_PROFILE=real2
else
CURRENT_PROFILE=$(curl -s http://localhost/profile)
fi
```
이슈가 있을 경우 real1에 배포하기 위해서 강제로 현재 포트를 real2로 두는 정책 
 
