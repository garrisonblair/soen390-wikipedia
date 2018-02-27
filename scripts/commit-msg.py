import re
import sys

COMMIT_REGEX = "#[0-9]+"


def validate_commit_message():
    """ Make sure commit message has is linked to an issue. """

    try:
        script, commit_msg = sys.argv

        match = re.search(COMMIT_REGEX, commit_msg)

        if match is not None:
            return 0
    except Exception as e:
        print("ERROR")
        print(e)

    print("Commit message does not link to an issue")
    return 1


if __name__ == '__main__':
    sys.exit(validate_commit_message())
