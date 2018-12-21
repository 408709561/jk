#!/bin/bash

BUILD_TAG="test"
REGISTRY_URL="registry.cn-shenzhen.aliyuncs.com"
NAME_SPACE="jk_basic"

if [ "$2" != "" ];
    then
    BUILD_TAG="$2"
fi

# build_push_tag
push_tag()
{
    set -e
    # ace-config
    docker tag ag/ace-config:latest $REGISTRY_URL/$NAME_SPACE/ace-config:$BUILD_TAG
    docker push $REGISTRY_URL/$NAME_SPACE/ace-config:$BUILD_TAG
    docker rmi ag/ace-config:latest
    docker rmi $REGISTRY_URL/$NAME_SPACE/ace-config:$BUILD_TAG

    # ace-auth
    docker tag ag/ace-auth:latest $REGISTRY_URL/$NAME_SPACE/ace-auth:$BUILD_TAG
    docker push $REGISTRY_URL/$NAME_SPACE/ace-auth:$BUILD_TAG
    docker rmi ag/ace-auth:latest
    docker rmi $REGISTRY_URL/$NAME_SPACE/ace-auth:$BUILD_TAG

    # ace-gate
    docker tag ag/ace-gate:latest $REGISTRY_URL/$NAME_SPACE/ace-gate:$BUILD_TAG
    docker push $REGISTRY_URL/$NAME_SPACE/ace-gate:$BUILD_TAG
    docker rmi ag/ace-gate:latest
    docker rmi $REGISTRY_URL/$NAME_SPACE/ace-gate:$BUILD_TAG

    # ace-admin
    docker tag ag/ace-admin:latest $REGISTRY_URL/$NAME_SPACE/ace-admin:$BUILD_TAG
    docker push $REGISTRY_URL/$NAME_SPACE/ace-admin:$BUILD_TAG
    docker rmi ag/ace-admin:latest
    docker rmi $REGISTRY_URL/$NAME_SPACE/ace-admin:$BUILD_TAG

    # ace-center
    docker tag ag/ace-center:latest $REGISTRY_URL/$NAME_SPACE/ace-center:$BUILD_TAG
    docker push $REGISTRY_URL/$NAME_SPACE/ace-center:$BUILD_TAG
    docker rmi ag/ace-center:latest
    docker rmi $REGISTRY_URL/$NAME_SPACE/ace-center:$BUILD_TAG

    # ace-dict
    docker tag ag/ace-dict:latest $REGISTRY_URL/$NAME_SPACE/ace-dict:$BUILD_TAG
    docker push $REGISTRY_URL/$NAME_SPACE/ace-dict:$BUILD_TAG
    docker rmi ag/ace-dict:latest
    docker rmi $REGISTRY_URL/$NAME_SPACE/ace-dict:$BUILD_TAG
}

if [ "$1" = "test" ];
    then
    # login
    REGISTRY_URL="47.107.44.141:5000"
    NAME_SPACE="ace"
    docker login --username=408709561@qq.com $REGISTRY_URL --password=Champion11
    push_tag
    elif [ "$1" = "prod" ];
    then
    REGISTRY_URL="registry.cn-hangzhou.aliyuncs.com"
    NAME_SPACE="jk_basic"
    docker login --username=408709561@qq.com $REGISTRY_URL --password=Champion11
    push_tag
    else
    echo '测试环境参考命令:dpush.sh test test1001'
    echo '生产环境参考命令:dpush.sh prod prod1001'
fi