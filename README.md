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

# Conceitos docker
* sistema de virtualização de máquinas / containers utilizando / compartilhando recursos da máquina host
* diferente de máquina virtual, que simula todo o hardware de uma máquina real
* o docker utiliza o so da máquina host para subir os containers, compartilhando recursos de hardware
* os containers são isolados entre si (hardware e software)
* comparação p/ facilitar entendimento - orientação a objetos: imagem = classe e container = instância da classe
* Repositório de imagens docker - docker hub

# Imagens docker
* criar arquivo "dockerfile" para indicar como criar a imagem
* FROM - imagem base
* ENTRYPOINT - o que vai rodar
* ADD - apps, arquivos etc para adicionar a imagem
* docker build -t repositorio/nome_imagem:versao . - cria imagem docker a partir do dockerfile - o param t define a tag da 
imagem, o parametro seguinte (.) indica para usar a pasta atual (onde deve estar o dockerfile)
* docker rmi contexto/nome-imagem - remover imagem

# Criar container docker
* docker run -p 5432:5432 --name beerdb -e POSTGRES_USER=beerstore -e POSTGRES_PASSWORD=beerstore -e POSTGRES_DB=beerstore -d postgres:11.1-alpine
  * run - criar container (apesar do comando ser run ele cria um container) e em seguida iniciar (start) o container
  * -p porta local:porta container - mapeamento de portas
  * --name nome do container
  * -e var ambiente - nome=valor
  * -d executar em segundo plano / detached
  * como último param informar o nome da imagem docker (docker hub) - se for indicar versão add : e a versão (obter no docker hub)
  * os parâmetros passados na criação do container são persistidos, sendo assim na próxima vez que for utilizar a imagem basta 
executar docker start nome_imagem

# Comandos docker
* docker info - exibir informações gerais docker
* docker search "linux mint" - pesquisar por imagem no docker hub
* docker ps -a - listar containers docker, rodando ou não - remover -a p/ ver o que está em execução somente
* docker rm nome - remover container
* docker stop id - parar container (obter id do comando ps)
* docker images - listar imagens
* docker start nome - iniciar container criado anteriormente, usando os mesmos parâmetros
* docker logs nome_da_imagem -f - ver logs da imagem
* docker exec -i -t <nome da imagem> /bin/bash - executa comando p/ abrir terminal no container indicado
* docker inspect <nome do container> - detalhes de um container
* docker login - fazer login na conta docker hub
* docker push - enviar imagem para o docker hub (tem que logar na sua conta antes) - imagem pública - ou p/ repositorio privado 
(ver seção registry)

# Docker compose
* gerenciar grupo de containers / automatizar criação de containers, parametros et
* arquivo docker-compose.yml
* docker-compose up

# Registry (config)
* Repositório para imagens docker
* Semelhante ao nexus para dependêcias de projeto
* https://docs.docker.com/registry/
* Iniciar o registry: docker run -d -p 5000:5000 --name registry registry:2
  * --name registry (nome da imagem no docker local)
  * registry:2 nome da imagem do registry no docker hub, versão 2

# Registry (uso)
* add ip atribuido no hosts para facilitar o uso do registry (opcional), podendo indicar nome da maquina em vez de ip
* ao executar build das imagens indicar o registry: docker build -t nome_do_registry:5000/projeto .
  * nome_do_registry é o ip da maquina (ou nome) onde o registry está
* configurar para nao utilizar https - liberar alterando o arquivo /etc/docker/daemon.json (ou criar o arquivo)
  * adicionar o nome do registry
  * exemplo: { "insecure-registries":["nome_do_registry:porta",...]
* reiniciar docker após config - sudo systemctl restart docker.service (iniciar imagens após restart)
* definir autenticacao - https://github.com/rodrigoboni/workshop-kubernetes/tree/master/registry
  * utilizar htpasswd (dentro do container) p/ criar arquivo com user + pass criptografada (armazenado local na maquina onde roda
   o docker)
  * no comando run do registry especificar os parametros do htpasswd, incluindo o path p/ arquivo de usuario e senha - obs param 
  -v para direcionar pasta do container para volume local
* fazer login no registry - docker login nome_do_registry:porta
* enviar imagem para o repositorio: docker push nome_do_registry:5000/projeto

# Jenkins
* Ferramenta de CI (Continuous Delivery)
* Instalar jenkins como container docker (ver doc workshop)
* Mapear configuração fora do container, p/ caso de excluir container e não perder as configs
* docker run -d -p 8080:8080 -p 50000:50000 -v ~/jenkins_home:/var/jenkins_home --name local-jenkins jenkins/jenkins:lts
* usar script modelo na doc do workshop p/ criar pipeline
* configurar jenkins (jdk etc)
* acessar container do jenkins para instalar jdk (acessar o bash e fazer instalação com wget, apt-get etc)
* configurar jdk no jenkins - configuração de nós
* utilizar pipeline armazenado no github p/ permitir alteração no pipeline sem ter que reconfigurar jenkins
* instalar docker dentro do container jenkins (que já está em docker) para que o jenkins gere imagem docker nos pipelines

# Kubernetes
* Ferramenta para orquestração de containers

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
