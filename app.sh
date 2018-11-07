#!/bin/bash

help()
{
    echo "Usage: $0 run | compile | package | clean | add | list | get <id> | test"
    return $?
}

# Check that 1 argument was given
if [ $# -lt 1 ]
then
    help
    exit 1
fi

case $1 in

    run)
        mvn spring-boot:run
        exit $?
        ;;

    compile)
        mvn compile
        exit $?
        ;;

    package)
        mvn package
        exit $?
        ;;

    clean)
        mvn clean
        exit $?
        ;;

    add)
        curl -X POST --silent --header "Content-Type: application/json" --data @invoiceData.json "http://127.0.0.1:8080/invoices" | jq
        exit $?
        ;;

    list)
        curl -X GET --silent --header "Content-Type: application/json" "http://127.0.0.1:8080/invoices" | jq
        exit $?
        ;;

    get)
        if [ -z "$2" ]
        then
            echo "Please specify the invoice ID"
            exit 1
        fi

        curl -X GET --silent --header "Content-Type: application/json" "http://127.0.0.1:8080/invoices/$2" | jq
        exit $?
        ;;

    test)
        mvn test
        exit $?
        ;;

    *)
        echo "\"$1\" not supported"
        exit 1
        ;;

esac
