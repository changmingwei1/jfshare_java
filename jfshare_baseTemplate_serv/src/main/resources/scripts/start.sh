#!/bin/bash
source /etc/profile

cd $(dirname $0)

exec java -Xmx512M -Xms256M   -cp conf:$(echo $(ls lib/*) | sed 's/ /:/g') com.jfshare.baseTemplate.server.BaseTemplateStart
