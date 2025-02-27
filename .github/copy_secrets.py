import os
import json

secrets = os.getenv('SECRETS')
vars = os.getenv('VARS')
jsonData = json.loads(secrets)
jsonData.update(json.loads(vars))

for key in jsonData:
    valueToReplace = f'#{{{key}}}'
    newValue = jsonData[key]
    with open('src/main/resources/application.properties', 'r') as file:
        content = file.read()
    with open('src/main/resources/application.properties', 'w') as file:
        file.write(content.replace(valueToReplace, newValue))