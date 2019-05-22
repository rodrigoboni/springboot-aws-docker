# armazenar estado da infra no s3 (default local)
terraform {
  backend "s3" {
    bucket = "rmbbeerstore"
    key = "beerstore"
    region = "us-east-1"
    profile = "terraform"
  }
}