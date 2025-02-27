terraform {

  # Terraform state configuration on minIO storage
  backend "s3" {
    bucket                      = "terraform-state-bucket"
    key                         = "terraform.tfstate"
    region                      = "main"
    skip_requesting_account_id  = true
    skip_credentials_validation = true
    skip_metadata_api_check     = true
    skip_region_validation      = true
    use_path_style              = true
  }
}