FROM gradle:6.6.1

RUN mkdir /app
WORKdir /app

ADD build.gradle .
RUN gradle stage

ADD src ./src
RUN gradle stage

CMD build/install/wiki/bin/wiki
