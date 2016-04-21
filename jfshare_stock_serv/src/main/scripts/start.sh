#!/bin/bash
source /etc/profile

cd $(dirname $0)

cd ../
exec java -Xmx512M -Xms256M -Dlog.level=WARN -Dnewrelic.environment=production -cp conf:$(echo $(ls lib/*) | sed 's/ /:/g') com.jfshare.stock.controller.StockRunServer
