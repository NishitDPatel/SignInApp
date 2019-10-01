FLAG=$1
FIREBASE_TOKEN=$2
if [ "$FLAG" = "false" ]; then
firebase deploy --token "$FIREBASE_TOKEN"
else
  echo "no further operations for Pull Request"
fi
