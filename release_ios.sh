#!/usr/bin/env bash

TAG=""

while getopts ":t:" option; do
   case "${option}" in
      t) # Enter a name
          TAG=${OPTARG};;
     \?) # Invalid option
          echo "Error: Invalid option"
          exit;;
   esac
done

if [ "$TAG" == "" ]; then
  BRANCH_NAME=$(git rev-parse --abbrev-ref HEAD)
  if [ $BRANCH_NAME == "main" ]; then 
    echo "On main branch."
  else 
    echo "Not on main branch. Exiting..."
    exit 1
  fi
fi

if [ -z "$(git status --porcelain)" ]; then 
  echo "Working directory is clean."
else 
  echo "Working directory is not clean. Exiting..."
  exit 1
fi

# Complete any prerequisites as defined in /bindings/ex-feature-ffi/README.md#ios
make ios

BUILD_DIR=build
REPO_PATH="${BUILD_DIR}/matrix-ex-feature-swift"
EX_FEATURE_PATH="platforms/ios/lib/ExFeature/"
rm -rf $BUILD_DIR
mkdir $BUILD_DIR
git clone https://github.com/matrix-org/matrix-ex-feature-swift.git $REPO_PATH
git fetch
git checkout main
rsync -a --delete --exclude=".git" --exclude=".github" --exclude="README.md" $EX_FEATURE_PATH $REPO_PATH
last_commit=$(git rev-parse --short HEAD);
RELEASE_BRANCH="release_$last_commit"
cd $REPO_PATH
git checkout -b $RELEASE_BRANCH
git add .
git commit -m "release $last_commit"
if [ "$TAG" != "" ]; then
  echo "found a tag $TAG"]
  REMOTE_BRANCH_NAME="release_v_$TAG"
else
  echo "tag not found"
  REMOTE_BRANCH_NAME=${RELEASE_BRANCH}
fi
git push origin $RELEASE_BRANCH:$REMOTE_BRANCH_NAME
