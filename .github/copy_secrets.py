import os
import json

jsonData = json.loads(os.environ['SECRETS'])
# jsonData = json.loads('{"CAR_RENTAL_API_KEY": "car-rental-api-key", "FLY_API_TOKEN": "fly-api-token"}')

for key in jsonData:
    valueToReplace = f'#{{{key}}}'
    newValue = jsonData[key]
    with open('../src/main/resources/application.properties', 'r') as file:
        content = file.read()
    with open('../src/main/resources/application.properties', 'w') as file:
        file.write(content.replace(valueToReplace, newValue))