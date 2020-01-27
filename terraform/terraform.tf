# armazenar estado da infra no s3 (default local)
# https://www.terraform.io/docs/backends/types/s3.html
terraform {
  backend "s3" {
    bucket = "rmbbeerstorenovo"
    key = "beerstore"
    region = "us-east-1"
    profile = "terraform"
  }
}
