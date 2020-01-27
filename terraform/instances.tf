# Configurar instâncias EC2 / outros
# https://www.terraform.io/docs/providers/aws/r/instance.html
resource "aws_instance" "instances" {
  count = 3

  ami = "ami-062f7200baf2fa504" // OBS SELECIONAR APENAS AMI ELEGÍVEL AO FREE-TIER (SENÃO GERA COBRANÇA IMEDIATA)
  instance_type = "t2.micro" // OBS SELECIONAR APENAS TIPO ELEGÍVEL AO FREE-TIER (SENÃO GERA COBRANÇA IMEDIATA)

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"

  tags = {
    Name = "beerstore_instances"
  }
}
