from subprocess import call

data = ""
with open('sonarqubeToken', 'r') as tokenFile:
    data=tokenFile.read().replace('\n', '')


call([f"./gradlew sonarqube -Dsonar.host.url=http://localhost:9000   -Dsonar.login={value}"])