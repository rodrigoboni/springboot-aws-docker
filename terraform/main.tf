# config terraform p/ usar aws
# https://www.terraform.io/docs/providers/aws/index.html
provider "aws" {
  version = "~> 2.46.0"
  shared_credentials_file = "~/.aws/credentials"
  profile = "terraform"
}
