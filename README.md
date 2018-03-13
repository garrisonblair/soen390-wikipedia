[![Build Status](https://travis-ci.com/SteveLocke/SOEN390-wiki.svg?token=VdHBssbr7jcta4rPxyAp&branch=master)](https://travis-ci.com/SteveLocke/SOEN390-wiki)

## Wikipedia Android app

This repository contains the source code for the official Wikipedia Android app.

## Documentation

All documentation is kept on [our wiki](https://www.mediawiki.org/wiki/Wikimedia_Apps/Team/Wikipedia_Android_app_hacking). Check it out!

## Dev Setup

#### Enforcing Issue linking
To make sure every commit links to an issue, there is a commit-msg hook that will check for the presence of a link.  The script needs to be setup by running 

`sh scripts/setup_hooks.sh`

#### Android Tests
Please test and write tests that pass on a Nexus S, SDK 24 emulator

Afterwards every commit message that doesn't contain the regex '#[0-9]+' will be rejected.

## Conventions

#### Branch naming:
camelCase

#### Resource file naming (@string, xml layouts etc.)
lowercase_and_underscores

