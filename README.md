[![Build Status](https://travis-ci.com/SteveLocke/SOEN390-wiki.svg?token=VdHBssbr7jcta4rPxyAp&branch=master)](https://travis-ci.com/SteveLocke/SOEN390-wiki)

## Wikipedia Android app

This repository contains the source code for the official Wikipedia Android app.

## Documentation

All documentation is kept on [our wiki](https://www.mediawiki.org/wiki/Wikimedia_Apps/Team/Wikipedia_Android_app_hacking). Check it out!

## Dev Setup

#### Enforcing Issue linking
To make sure every commit links to an issue, there is a commit-msg hook that will check for the presence of a link.  The script needs to be setup by running 

`sh scripts/setup_hooks.sh`

#### Running SonarQube on post-commit

The above script to setup the hooks will also add a post-commit hook that will attempt to run SonarQube.  For this to work SonarQube must be installed on the development machine and some configuration needs to be made.

First download it from [here] (https://www.sonarqube.org/downloads/)
Unzip the file and execute 
`./sonarqube.sh start`
in the directory that corresponds to your OS.

Wait a minute and navigate to `localhost:9000` in your browser.  You will be prompted for credentials.  The username and password are both "admin".

You will be run through a quick tutorial and be prompted to generate a token.  Store this token in a file called `sonarqubeToken` within the scripts directory of the project.  If you loose this token you can generate a new one in the account settings.

Now whenever a commit as executed, sonarqube will run in the background and after a couple of minutes a new report will be available at `localhost:9000`

#### Android Tests
Please test and write tests that pass on a Nexus S, API 24 emulator

Afterwards every commit message that doesn't contain the regex '#[0-9]+' will be rejected.

## Conventions

#### Branch naming:
camelCase

#### Resource file naming (@string, xml layouts etc.)
lowercase_and_underscores

