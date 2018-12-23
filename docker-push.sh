#!/bin/bash

BUILD_TAG="test1001"
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
    docker tag ag/ace-config:latest registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-config:test1001
    docker push registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-config:test1001
    docker rmi ag/ace-config:latest
    docker rmi registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-config:test1001

    # ace-auth
    docker tag ag/ace-auth:latest registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-auth:test1001
    docker push registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-auth:test1001
    docker rmi ag/ace-auth:latest
    docker rmi registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-auth:test1001

    # ace-gate
    docker tag ag/ace-gate:latest registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-gate:test1001
    docker push registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-gate:test1001
    docker rmi ag/ace-gate:latest
    docker rmi registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-gate:test1001

    # ace-admin
    docker tag ag/ace-admin:latest registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-admin:test1001
    docker push registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-admin:test1001
    docker rmi ag/ace-admin:latest
    docker rmi registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-admin:test1001

    # ace-center
    docker tag ag/ace-center:latest registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-center:test1001
    docker push registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-center:test1001
    docker rmi ag/ace-center:latest
    docker rmi registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-center:test1001

    # ace-dict
    docker tag ag/ace-dict:latest registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-dict:test1001
    docker push registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-dict:test1001
    docker rmi ag/ace-dict:latest
    docker rmi registry.cn-shenzhen.aliyuncs.com/jk_basic/ace-dict:test1001
}

if [ "$1" = "test" ];
    then
    # login
#    REGISTRY_URL="172.172.172.201:5000"
#    NAME_SPACE="ace"
#    docker login --username=123 $REGISTRY_URL --password=123
#    push_tag
    elif [ "$1" = "prod" ];
    then
    REGISTRY_URL="registry.cn-shenzhen.aliyuncs.com"
    NAME_SPACE="jk_basic"
    docker login --username=408709561@qq.com $REGISTRY_URL --password=Champion11
    push_tag
    else
    echo '测试环境参考命令:dpush.sh test test1001'
    echo '生产环境参考命令:dpush.sh prod prod1001'
fi
