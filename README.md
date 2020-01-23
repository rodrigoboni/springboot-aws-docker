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

# Migração BD
* executar ./gradlew flywayMigrate -i p/ executar migrações pendentes

# Plugins Intellij à instalar:
* Lombok
* Ativar o plugin
* Marcar processamento de anotações (nas opções do compilador)
* Terraform

# Tratamento de erros
* baseado no esquema de mensagens por recurso - https://alidg.me/blog/2016/9/24/rest-api-error-handling

# AWS
* Criar alarmes de billing p/ ser avisado se houver cobrança
* Criar usuário e associar com perfil para liberar acesso programático p/ o terraform definir a infra
* salvar arquivo csv com credenciais
* ATENÇÃO - NÃO ADD CREDENCIAIS EM REP GITHUB OU QQ OUTRO LOCAL ACESSÍVEL
* COM AS CHAVES É POSSÍVEL CRIAR INSTÂNCIAS / ATIVAR SERVIÇOS ETC
* O QUE VAI GERAR CUSTOS NA AWS

# Terraform (instalação)
* Baixar do site (arquivo único / executável)
* Colocar em pasta (dentro de home por ex)
* Dar permissão de execução
* Criar link simbólico em /usr/local/bin p/ acessar o terraform de qualquer pasta
    * ln ~/terraform/terraform /usr/local/bin/terraform

# AWS CLI (Instalação)
* sudo apt install awscli

# AWS CLI
* Configurar profile - aws configure --profile terraform
* cria pasta .aws, com arquivo config e credentials
* arq config com o profile com região default etc
* arq credentials com access key e secret key

# Terraform
* Criar pasta terraform dentro do projeto para o qual o terraform será utilizado
* Inicializar - terraform init ((inicial e qdo alterar alguma config), baixa arquivos e grava configs em pasta .terraform)
* Aplicar mudanças - terraform apply
* Mostrar plano de execução / prever o que será alterado na proxima exec de apply - terraform plan
* Consultar documentação do provedor escolhido (nas docs do terraform) p/ detalhes
* É aconselhado guardar o estado do terraform em um bucket s3 em vez de arquivo local, p/ não expor dados de infra no github, por ex
* Arquivos e detalhes:
    * main.tf - config do provedor terraform e credenciais - pegar versão do provedor no github do terraform (ver tags da branch master)
    * terraform.tf - config do bucket s3 p/ terraform guardar seu estado 
        * bucket = nome do bucket
        * key = identificador dos arquivos no bucket
    * network.tf
        * resource = configura recurso (ver documentação terraform / provider)    
