# config aws vpc (virtual private cloud)
resource "aws_vpc" "beerstore" {
  cidr_block       = "192.168.0.0/16"

  # Valores com nomes iguais aos atributos na aws (coluna Name do serviço vpc por ex)
  tags = {
    Name = "beerstore"
  }
}

# criar subnets dentro da vpc (uma subnet para cada availability zone, todas dentro da mesma vpc)
resource "aws_subnet" "beerstore_subnet" {
  # contador p/ gerar loop e criar este bloco n vezes, um loop
  count = 3

  #expressao p/ obter o id do resource aws_vpc (atributo de saída do objeto / resource aws_vpc)
  vpc_id = "${aws_vpc.beerstore.id}"

  #aplicar funcao para calcular cidr (range ip, new bits / add, net num / identificador rede)
  #de 192.168.0.0/16 retorna 192.168.10.0/24 para count=0
  cidr_block = "${cidrsubnet(aws_vpc.beerstore.cidr_block, 8, count.index + 10)}"

  # usar var definida no arq variables.tf
  availability_zone = "${var.availability_zones[count.index]}"

  tags {
    # expressao para ler o contador acima e interpolar na string
    Name = "beerstore_subnet_${count.index}"
  }
}
