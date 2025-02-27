terraform {
  required_providers {
    keycloak = {
      source  = "keycloak/keycloak"
      version = "~> 5.1.1"
    }
  }

  # Terraform state configuration on minIO storage
  backend "s3" {
    bucket = "terraform-state-bucket"
    key    = "terraform.tfstate"
    endpoints = {
      s3 = var.minio_endpoint_url
    }
    access_key = var.minio_access_key
    secret_key = var.minio_secret_key

    region                      = "main"
    skip_requesting_account_id  = true
    skip_credentials_validation = true
    skip_metadata_api_check     = true
    skip_region_validation      = true
    use_path_style              = true
  }
}

provider "keycloak" {
  url       = var.keycloak_url
  realm     = var.keycloak_realm
  client_id = var.keycloak_admin_client_id
  username  = var.keycloak_admin_username
  password  = var.keycloak_admin_password
}

# Set realm user profile settings to enable attributes editing
resource "keycloak_realm_user_profile" "realm_user_profile_settings" {
  realm_id                   = var.keycloak_realm
  unmanaged_attribute_policy = "ADMIN_EDIT"

  attribute {
    name         = "username"
    display_name = "$${username}"
    multi_valued = false
    group        = ""

    permissions {
      edit = ["user", "admin"]
      view = ["user", "admin"]
    }

    validator {
      name = "length"
      config = {
        min = 3
        max = 255
      }
    }

    validator {
      name = "username-prohibited-characters"
      config = {}
    }

    validator {
      name = "up-username-not-idn-homograph"
      config = {}
    }
  }

  attribute {
    name         = "email"
    display_name = "$${email}"
    multi_valued = false
    group        = ""

    permissions {
      edit = ["user", "admin"]
      view = ["user", "admin"]
    }

    validator {
      name = "email"
      config = {}
    }

    validator {
      name = "length"
      config = {
        max = 255
      }
    }
  }

  attribute {
    name         = "firstName"
    display_name = "$${firstName}"
    multi_valued = false
    group        = ""

    permissions {
      edit = ["user", "admin"]
      view = ["user", "admin"]
    }

    validator {
      name = "length"
      config = {
        max = 255
      }
    }

    validator {
      name = "person-name-prohibited-characters"
      config = {}
    }
  }

  attribute {
    name         = "lastName"
    display_name = "$${lastName}"
    multi_valued = false
    group        = ""

    permissions {
      edit = ["user", "admin"]
      view = ["user", "admin"]
    }

    validator {
      name = "length"
      config = {
        max = 255
      }
    }

    validator {
      name = "person-name-prohibited-characters"
      config = {}
    }
  }

  group {
    name                = "user-metadata"
    display_header      = "User metadata"
    display_description = "Attributes, which refer to user metadata"
  }
}

# Set admin user to permanent
resource "keycloak_user" "admin_user" {
  depends_on = [
    keycloak_realm_user_profile.realm_user_profile_settings
  ]

  import         = true
  realm_id       = var.keycloak_realm
  username       = var.keycloak_admin_username
  email_verified = true

  attributes = {
    is_temporary_admin = "false"
  }
}

# Create Google identity provider
resource "keycloak_oidc_google_identity_provider" "google_identity_provider" {
  client_id                     = var.keycloak_google_identify_provider_client_id
  client_secret                 = var.keycloak_google_identify_provider_client_secret
  realm                         = var.keycloak_realm
  store_token                   = false
  first_broker_login_flow_alias = ""
}

# Create a car-rental client
resource "keycloak_openid_client" "car_rental" {
  realm_id                    = var.keycloak_realm
  client_id                   = "car-rental"
  name                        = "Car rental"
  enabled                     = true
  valid_redirect_uris         = var.keycloak_car_rental_valid_redirect_uris
  web_origins                 = var.keycloak_car_rental_web_origins
  access_type                 = "PUBLIC"
  standard_flow_enabled       = true
  frontchannel_logout_enabled = true
}

# Create car-rental client roles
resource "keycloak_role" "car_rental_admin_role" {
  name      = "ADMIN"
  realm_id  = var.keycloak_realm
  client_id = keycloak_openid_client.car_rental.id
}

resource "keycloak_role" "car_rental_rentals_role" {
  name      = "RENTALS"
  realm_id  = var.keycloak_realm
  client_id = keycloak_openid_client.car_rental.id
}

resource "keycloak_role" "car_rental_cars_role" {
  name      = "CARS"
  realm_id  = var.keycloak_realm
  client_id = keycloak_openid_client.car_rental.id
}

################################
# Variables
################################
# Keycloak variables
variable "keycloak_url" {
  type        = string
  description = "URL of your Keycloak instance"
}

variable "keycloak_realm" {
  type        = string
  description = "The Keycloak realm to connect to (usually 'master')"
  default     = "master"
}

variable "keycloak_admin_client_id" {
  type        = string
  description = "Keycloak admin client ID (usually 'admin-cli')"
  default     = "admin-cli"
}

variable "keycloak_google_identify_provider_client_id" {
  type        = string
  description = "Client ID for Google Identity Provider"
  sensitive   = true
}

variable "keycloak_google_identify_provider_client_secret" {
  type        = string
  description = "Client ID for Google Identity Provider"
  sensitive   = true
}

variable "keycloak_admin_username" {
  type        = string
  description = "Username for admin user"
}

variable "keycloak_admin_password" {
  type        = string
  description = "Password for admin user"
  sensitive   = true
}

variable "keycloak_car_rental_valid_redirect_uris" {
  type = list(string)
  description = "List of valid redirect uris for car-rental client"
}

variable "keycloak_car_rental_web_origins" {
  type = list(string)
  description = "List of web origins for car-rental client"
}

# MinIO variables
variable "minio_access_key" {
  type        = string
  description = "MinIO access key"
  sensitive   = true
}

variable "minio_secret_key" {
  type        = string
  description = "MinIO secret key"
  sensitive   = true
}

variable "minio_endpoint_url" {
  type        = string
  description = "MinIO endpoint URL"
}