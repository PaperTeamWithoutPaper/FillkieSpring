REPOSITORY=/home/ubuntu/app
cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

sudo lsof -t -i tcp:80 -s tcp:listen | sudo xargs kill

echo "> $JAR_PATH 배포" #3
sudo nohup java -jar $REPOSITORY/build/libs/fillkie-0.0.1-SNAPSHOT.jar --spring.config.location=/home/ubuntu/spring-app.yml > /home/ubuntu/log 2> /home/ubuntu/error &
