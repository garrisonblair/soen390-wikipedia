#!/usr/bin/env bash

cp scripts/commit-msg .git/hooks/
cp scripts/post-commit .git/hooks/

chmod +x .git/hooks/commit-msg
chmod +x .git/hooks/post-commit
