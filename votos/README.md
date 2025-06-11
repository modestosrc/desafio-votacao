# Desafio de Programação - Votos

## Executando a aplicação

Ela não possui dependências externas. Então, para executá-la, basta usar o 
Maven Wrapper incluído no projeto.

**Esta usando a porta 8080.**
```bash
$ ./mvnw spring-boot:run
```

## Testando a aplicação
Para testar a aplicação, você pode usar o Swagger UI, que está disponível durante a execução da aplicação.

http://localhost:8080/swagger-ui/index.html

Ou usar:
```bash
$ ./mvnw test
```

## Notas sobre a aplicação

Desenvolvi uma aplicacao de votos que permite criar, listar e modificar pautas,
sessões e votos. Como também inclui um sistema para contagem dos votos.

A aplicação é construída com Spring Boot com Maven e Java 24. Javadoc e Swagger
como documentação. Como bando de dados escolhi SQLite, por não precisar de
um servidor.

Escrevi mocks para os controllers, para validar seu funcionamento.

A aplicação também foi testada com Grafana K6, com os seguintes resultados:

As requests que falharam são devido ao SQLite ([SQLITE_BUSY] The database file is locked (database is locked)).
```bash
[mateus@archlinux votos]$ k6 run testes-k6.js 

         /\      Grafana   /‾‾/  
    /\  /  \     |\  __   /  /   
   /  \/    \    | |/ /  /   ‾‾\ 
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: testes-k6.js
        output: -

     scenarios: (100.00%) 1 scenario, 100 max VUs, 1m0s max duration (incl. graceful stop):
              * default: 100 looping VUs for 30s (gracefulStop: 30s)



  █ TOTAL RESULTS 

    HTTP
    http_req_duration.......................................................: avg=19.88ms min=1ms    med=8.16ms max=427.85ms p(90)=25.01ms p(95)=45.43ms
      { expected_response:true }............................................: avg=7.26ms  min=2.88ms med=5.89ms max=121.12ms p(90)=12.22ms p(95)=14.76ms
    http_req_failed.........................................................: 45.16% 1355 out of 3000
    http_reqs...............................................................: 3000   97.431044/s

    EXECUTION
    iteration_duration......................................................: avg=1.02s   min=1s     med=1s     max=1.43s    p(90)=1.02s   p(95)=1.04s  
    iterations..............................................................: 3000   97.431044/s
    vus.....................................................................: 100    min=100          max=100
    vus_max.................................................................: 100    min=100          max=100

    NETWORK
    data_received...........................................................: 1.1 MB 35 kB/s
    data_sent...............................................................: 525 kB 17 kB/s




running (0m30.8s), 000/100 VUs, 3000 complete and 0 interrupted iterations
default ✓ [======================================] 100 VUs  30s
```
