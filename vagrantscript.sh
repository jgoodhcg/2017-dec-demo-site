#!/bin/bash
sudo apt-get update
sudo apt-get install -y git g++ automake libtool
sudo apt-get install -y default-jdk

curl https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein -o /usr/local/bin/lein
chmod a+x /usr/local/bin/lein

