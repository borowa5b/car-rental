import os
import json

def transform_terraform_variables(variables):
    transformed = {}
    for key, value in variables.items():
        # Remove the "TF_VAR_" prefix
        new_key = key[7:].lower()

        # If value is an array transform array values
        try:
            parsed_value = json.loads(value)
            if isinstance(parsed_value, list):
                transformed[new_key] = parsed_value
                continue
        except json.JSONDecodeError:
            pass

        # If value is not an array transform string value
        transformed[new_key] = value

    return transformed

def write_terraform_tf(variables, output_file):
    with open(output_file, 'w') as file:
        for key, value in variables.items():
            if isinstance(value, list):
                file.write(f'{key} = [\n')
                for item in value:
                    file.write(f'  "{item}",\n')
                file.write(']\n')
            else:
                file.write(f'{key} = "{value}"\n')

# Load secrets and repository variables and extract terraform variables
secrets = os.getenv('SECRETS')
vars = os.getenv('VARS')
jsonData = json.loads(secrets)
jsonData.update(json.loads(vars))
terraform_variables = {k: v for k, v in jsonData.items() if k.startswith("TF_VAR_")}

# Transform the terraform variables
transformed_variables = transform_terraform_variables(terraform_variables)

# Write the transformed variables to terraform.tf
write_terraform_tf(transformed_variables, 'terraform.tfvars')
print("terraform.tfvars file has been created.")