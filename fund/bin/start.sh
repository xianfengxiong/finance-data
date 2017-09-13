#!/usr/bin/env bash

lsof -i:8000 | grep java | awk '{print $2}' | xargs kill -9
nohup java -jar target/finance-fund-1.0.jar >> /home/xxf/log/fund/fund.out