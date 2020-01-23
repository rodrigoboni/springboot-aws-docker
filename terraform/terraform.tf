# armazenar estado da infra no s3 (default local)
terraform {
  backend "s3" {
    bucket = "rmbbeerstorenovo"
    key = "beerstore"
    region = "us-east-1"
    profile = "terraform"
  }
}
