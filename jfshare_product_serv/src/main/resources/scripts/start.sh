#!/bin/bash
source /etc/profile

cd $(dirname $0)

exec java -Xmx1024M -Xms512M   -cp conf:$(echo $(ls lib/*) | sed 's/ /:/g') com.jfshare.product.server.ProductStart
