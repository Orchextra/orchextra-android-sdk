#!/bin/bash

cd "$(dirname $0)/../.."

# Configurables
sdkVersion="orchextra-sdk"
appFolder=`pwd`

# Build docker image if required
ci-scripts/.jenkins_library/bin/buildDockerImage.sh --sdkVersion=${sdkVersion}

# Execute instrumentation tests
docker run --privileged --rm -t \
    -v "${appFolder}/":/myApp:rw \
    -v "${appFolder}/.gradle":/root/.gradle:rw \
    -v "${appFolder}/.gem":/root/.gem:rw \
    ci-scripts:${sdkVersion} \
    /bin/bash ./ci-scripts/bin/executeInstrumentationTestWithinDocker.sh $@

# Restore permissions
docker run --rm -t -v "${appFolder}/":/myApp:rw  -v "${appFolder}/.gradle":/root/.gradle:rw -v "${appFolder}/.gem":/root/.gem:rw ci-scripts:${sdkVersion} chown -R --reference=gradlew . || exit $?
