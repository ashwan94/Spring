FROM ubuntu:22.04
LABEL authors="std121"

# 환경변수 설정(선택 사항)
ENV LC_ALL C.UTF-8
ENV TZ Asia/Seoul

# 실행시킬 내용 작성
# -qq 는 콘솔 창에 log 출력 X
# -y 는 질문에 대해 자동으로 y 처리
# && 는 and(다음 행동)
# tzdata 는 TimeZone data
RUN apt-get update -qq && \
    apt-get upgrade -y && \
    apt-get install -y tzdata && \
    apt-get install -y openjdk-17-jre

# 빌드된 war 파일을 Docker 의 폴더 아래로 복사
ADD build/libs/SpringBoot-0.0.1.war /root

# 작업할 디렉토리로 이동
WORKDIR /root

# 우분투의 Port 를 개방시켜줌(컨테이너의 Port 가 아니다)
EXPOSE 8080

# 컨테이너가 시작될때 실행되는 명령어
# java -jar SpringBoot-0.0.1.war &
CMD ["java", "-jar", "SpringBoot-0.0.1.war", "&"]