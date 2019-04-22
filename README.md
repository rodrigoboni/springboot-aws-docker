# springboot-aws-docker
Projeto do curso Spring boot com Gradle, TDD, AWS e docker

# Instalação docker
* https://docs.docker.com/install/linux/docker-ce/ubuntu/
* sudo apt install -y apt-transport-https ca-certificates curl \
   software-properties-common
* curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
      sudo apt-key add -
* export LSB_ETC_LSB_RELEASE=/etc/upstream-release/lsb-release
* V=$(lsb_release -cs)
* sudo add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/ubuntu ${V} stable"
* sudo apt update -y
* sudo apt install -y docker-ce
* Add user to docker group. Added user can run docker command without sudo command - sudo gpasswd -a "${USER}" docker
* reboot

# Executar container docker
* docker run -p 5432:5432 --name beerdb -e POSTGRES_USER=beerstore -e POSTGRES_PASSWORD=beerstore -e POSTGRES_DB=beerstore -d postgres:11.1-alpine
* run - executar container
* -p porta local:porta container - mapeamento de portas
* --name nome do container
* -e var ambiente - nome=valor
* -d executar em segundo plano / detached
* como último param informar o nome da imagem docker (docker hub) - se for indicar versão add : e a versão (obter no docker hub)

# Comandos docker
* docker info - exibir informações gerais docker
* docker search "linux mint" - pesquisar por imagem no docker hub
* docker ps -a - listar containers docker, rodando ou não - remover -a p/ ver o que está em execução somente
* docker rm grave_jones - remover container
* docker stop id - parar container (obter id do comando ps)
* docker images - listar imagens
* docker start nome - iniciar container

# Migração BD
* executar ./gradlew flywayMigrate -i p/ executar migrações pendentes

# Plugins Intellij à instalar:
* Lombok
* Ativar o plugin
* Marcar processamento de anotações (nas opções do compilador)

# Tratamento de erros
* baseado no esquema de mensagens por recurso - https://alidg.me/blog/2016/9/24/rest-api-error-handling

# AWS
* Criar alarmes de billing p/ ser avisado se houver cobrança

# Terraform (instalação)
* Baixar do site (arquivo único / executável)
* Colocar em pasta (dentro de home por ex)
* Dar permissão de execução
* Criar link simbólico em /usr/local/bin p/ acessar o terraform de qualquer pasta

# AWS CLI (Instalação)
* sudo apt-get update && apt-get install -y python-pip libpython-dev
* sudo apt install awscli

# AWS CLI
* Configurar profile - aws configure --profile terraform

# Terraform
* Inicializar (inicial e qdo alterar alguma config) - terraform init
* Aplicar mudanças - terraform apply
* Mostrar plano de execução / prever o que será alterado na proxima exec de apply - terraform plan
