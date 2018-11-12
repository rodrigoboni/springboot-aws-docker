# springboot-aws-docker
Projeto do curso Spring boot com Gradle, TDD, AWS e docker

# Instalação docker
* http://linuxbsdos.com/2016/12/13/how-to-install-docker-and-run-docker-containers-on-linux-mint-1818-1/
* https://docs.docker.com/install/linux/docker-ce/ubuntu/
* add user ao grupo docker p/ executar comandos sem sudo - sudo usermod -a -G docker $USER (reiniciar máquina após)

# Executar container docker
* docker run -p 5432:5432 --name beerdb -e POSTGRES_USER=beerstore -e POSTGRES_PASSWORD=beerstore -e POSTGRES_DB=beerstore -d postgres:11.1-alpine
* run - executar container
* -p porta local:porta container - mapeamento de portas
* --name nome do container
* -e var ambiente - nome=valor
* -d executar em segundo plano / daemon
* como último param informar o nome da imagem docker (docker hub) - se for indicar versão add : e a versão (obter no docker hub)

# Comandos docker
* docker info - exibir informações gerais docker
* docker search "linux mint" - pesquisar por imagem no docker hub
* docker ps -a - listar containers docker, rodando ou não - remover -a p/ ver o que está em execução somente
* docker rm grave_jones - remover container
* docker stop id - parar container (obter id do comando ps)
* docker images - listar imagens
* docker start nome - iniciar container
