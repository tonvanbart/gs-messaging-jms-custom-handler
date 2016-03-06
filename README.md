## Sending arbitrary types with Spring JMS

This project illustrates sending and receiving custom types as JSON encoded text messages.
This is a modified version of the gs-messaging-jms project (see http://spring.io/guides/gs/messaging-jms/).

The most important thing to note is setting the type id property name on the message converter:

        converter.setTypeIdPropertyName("object-type");

Reading the Spring code, without this property set encoding will work, but decoding will fail, at 
least when not using a `@MessageListener` annotated method. The setting specifies the name of a 
custom message header, which in this case will contain the full class name of the encoded object. It is
also possible to configure a mapping from header value to serialized type.

For clarity, all beans are created explicitly in the `Application` class; it's also possible to 
annotate them as `@Component` and use autowiring.

The `application.properties` file assumes a Dockered ActiveMQ container running at 192.168.99.100, to 
use an embedded container, delete or comment this section.