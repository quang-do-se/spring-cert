```shell
docker run -d \
  --name=prometheus \
  --add-host host.docker.internal:host-gateway \
  -p 9090:9090 \
  -v /home/q/Desktop/spring-cert/pivotal-certified-pro-spring-dev-exam-02/chapter09/prometheus-boot-app/src/main/resources/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```
- OR

```shell
docker run -d \
  --name=prometheus \
  --network="host" \
  -p 9090:9090 \
  -v /home/q/Desktop/spring-cert/pivotal-certified-pro-spring-dev-exam-02/chapter09/prometheus-boot-app/src/main/resources/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```