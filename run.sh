echo "Executando testes de integração..."
./mvnw clean verify
if [ $? -eq 0 ]; then
    echo "Testes passaram com sucesso! Iniciando a aplicação..."
    docker-compose down
    docker-compose up -d
    sleep 5
    ./mvnw quarkus:dev
else
    echo "Os testes falharam! Corrija os erros antes de continuar."
    exit 1
fi