после установки docker выполять комманды в терминале intellij idea или в PowerShell запуск prometheus на сервере
localhost:9090 где prometheus.yml документ в проетке

docker run -d -p 9090:9090 -v prometheus.yml:/prometheus/prometheus.yml prom/prometheus

запуск grafana на сервере localhost:3000 логин и пароль: admin

docker run -p 3000:3000 grafana/grafana

импорт готовых dashboards производиться путем добавления в Grafan (create->import-> upload JSON file) файлов формата
json в папке resources/dashboards так же следует подключить prometheus (Configuration -> Data Sources -> Prometheus)
добавив url: http://localhost:9090, Access:Browser
