GATLING_HOME=$HOME/gatling-3.9.5

WORKSPACE=/mnt/d/development/projetos_java/rinha-backend-async/stress-test

sh $GATLING_HOME/bin/gatling.sh -rm local -s RinhaBackendSimulation \
    -rd "DESCRICAO" \
    -rf $WORKSPACE/user-files/results \
    -sf $WORKSPACE/user-files/simulations \
    -rsf $WORKSPACE/user-files/resources \

<<<<<<< Updated upstream
sleep 3

curl -v "http://localhost:9999/contagem-pessoas"
=======
