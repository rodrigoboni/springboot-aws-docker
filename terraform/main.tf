# config terraform p/ usar aws
provider "aws" {
  version = "~> 2.36.0",
  shared_credentials_file = "~/.aws/credentials"
  profile = "terraform"
}
