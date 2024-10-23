import os
import json

secrets = os.getenv('SECRETS')
jsonData = json.loads(secrets)

for key in jsonData:
    valueToReplace = f'#{{{key}}}'
    newValue = jsonData[key]
    with open('src/main/resources/application.properties', 'r') as file:
        content = file.read()
    with open('src/main/resources/application.properties', 'w') as file:
        file.write(content.replace(valueToReplace, newValue))