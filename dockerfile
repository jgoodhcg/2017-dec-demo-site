FROM clojure
WORKDIR /demo-site/app
COPY /app/project.clj /demo-site/app
RUN lein deps