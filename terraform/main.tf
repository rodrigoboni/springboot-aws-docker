provider "aws" {
  version = "~> 2.7.0",
  shared_credentials_file = "~/.aws/credentials"
  profile = "terraform"
}
