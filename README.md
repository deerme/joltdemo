## Spring Boot to Jolt API Microservice

### Project Setup

1. cd into src/main/resources
2. run install.sh to add jolt.jar to local maven repo, $ ./install.sh
3. confirm jolt.jar installed to: $HOME/.m2/repository/oracle/jolt/1.0/jolt-1.0.jar

### References

* [Tuxedo Fundamentals](https://docs.oracle.com/cd/E13203_01/tuxedo/tux100/int/intbas.html)
* [Oracle Tuxedo](http://www.oracle.com/technetwork/middleware/tuxedo/overview/index.html)
* [Jolt Overview](https://docs.oracle.com/cd/E35855_01/tuxedo/docs12c/install/insjol.html)
* [Jolt Javadoc](https://docs.oracle.com/cd/E13204_01/tuxwle/jolt12/javadoc/packages.html)

### Install

* push jolt-web
* push jolt-api
* push api-gateway
* test endpoints then proceed
* cf create-route dev cfapps.haas-68.pez.pivotal.io --hostname jolt
* cf map-route api-gateway cfapps.haas-68.pez.pivotal.io --hostname jolt
* cf unmap-route api-gateway cfapps.haas-68.pez.pivotal.io --hostname api-gateway


