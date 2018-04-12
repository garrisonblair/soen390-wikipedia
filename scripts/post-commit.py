import subprocess

data = ""
with open('scripts/sonarqubeToken', 'r') as tokenFile:
    data=tokenFile.read().replace('\n', '')


subprocess.Popen(["./gradlew", "sonarqube", "-Dsonar.host.url=http://localhost:9000", "-Dsonar.login=" + data, "-Dsonar.tests=app/src/test"], close_fds=True)