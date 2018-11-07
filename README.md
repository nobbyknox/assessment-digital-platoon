# README

This project serves as my assessment submission to EOH Digital Platoon, as requested on 5 November 2018.

## How to Use

### Using The Provided Script

**Note:** The script makes use of `curl` and `jq`. It is advisable that you have these installed when making use of the `app.sh` script. Also, this is a bash script, so you will be out of luck on a vanilla Windows PC. A Mac or Linux PC is recommended. However, if you are running Windows without the Windows Subsystem for Linux, you might want to stick to the regular maven commands.

The script wraps all commands you might want to use. Running `./app.sh` on the command line prints some handy help instructions. Here is a sample:

```bash
$ ./app.sh
Usage: ./app.sh run | compile | package | clean | add | list | get <id> | test
```

The various commands are briefly described:

* `run` - runs the application - similar to `mvn spring-boot:run`
* `compile` - compiles the project - similar to `mvn compile`
* `package` - packages the project - similar to `mvn package`
* `clean` - cleans the project - similar to `mvn clean`
* `add` - adds a simple invoice from the `invoiceData.json` file by performing a POST operation with the `curl` command and pipes the output through `jq`
* `list` - lists all invoices by performing a GET request with the `curl` command and piping the output through `jq`
* `get <id>` - gets the specified invoice by performing a GET request with the `curl` command and piping the output through `jq`
* `test` - runs the unit tests - similar to `mvn test`

### Using Plain Maven Commands

To run the application, type:

```bash
$ mvn spring-boot:run
```

To add an invoice using the `curl` command:

```bash
$ curl -X POST --silent --header "Content-Type: application/json" --data @invoiceData.json "http://127.0.0.1:8080/invoices"
```

You may edit the `invoiceData.json` file to change the invoice details that will be submitted.

To list all invoices:

```bash
$ curl -X GET --silent --header "Content-Type: application/json" "http://127.0.0.1:8080/invoices"
```

To view a specific invoice:

```bash
$ curl -X GET --silent --header "Content-Type: application/json" "http://127.0.0.1:8080/invoices/1"
```

where 1 above refers to the invoice ID.

### H2 Console

An H2 console has also been provided. Point your browser to http://127.0.0.1:8080/console and sign in with the username `sa` and a blank password. The driver should be `org.h2.Driver` and your JDBC URL should be `jdbc:h2:mem:platoon`.

## How This Problem Was Solved

1. I started at `https://start.spring.io` and generate a skeleton app
1. Configured database in `application.properties`
1. Created REST controllers
1. Created entities and their unit tests
1. Added validation constraints and their unit tests
1. Added error handler to format responses when validation failures occurred

## Limitations

* No consideration was given to threading

## Parting Thoughts

I was a little rusty with my Spring magic and had to brush up some in order to complete this assessment. I'm happy to report that after some time most of my previous knowledge was awakened and I completed the assessment glad to have had the opportunity to refresh my memory.

I trust that this body of work meets with your approval.
