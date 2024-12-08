ARG JAVA_VERSION=21
FROM mcr.microsoft.com/devcontainers/java:${JAVA_VERSION}-bullseye

ARG GRADLE_VERSION=8.5

# Merge RUN commands and add cleanup
RUN --mount=type=cache,target=/var/cache/apt \
    apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
    curl \
    git \
    vim \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle ${GRADLE_VERSION}" \
    && mkdir -p /home/vscode/.gradle \
    && chown -R vscode:vscode /home/vscode/.gradle

WORKDIR /workspaces/springboot-security-open-template

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1
