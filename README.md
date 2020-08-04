Q&A:

Q: Shortly explain your solution and your assumptions.

A: This solution makes use of a RabbitMQ docker image to handle messages between the importer and the consumer.
The importer receives messages through a RESTController and sends them to the RabbitMQ Queue. 
Here they wait until the consumer listens to the message and it is handled. 

Assumption:
- Importers that receive a message immediately put the message in the Queue and any calculations being done in the importer service do not alter the order of the messages that 
are received.

Q: Do you see any problem with this setup?

A: In this simple case I don't see any problems. Multiple importers can run concurrentely and all messages that are received will be handled by the consumer in the order of which
they entered the Queue. With the Queue being durable and RabbitMQ saving the messages in a database, no messages will get lost until the consumer handles them.

Q: What kind of data does the message by the sender NEED to contain to ensure they are imported in the correct order?

A: After a message is send to the Queue, RabbitMQ handles ordering and messages that arrived first in the Queue will be handled first. So if there is only one consumer and 
messages are immediately put in the Queue after reaching the importer, no additional data is needed for the consumer to handle the messages in the same order as which they are 
received.

Q: Describe what should be taken care of when having multiple consumers.

A: There should be good resource locking in place so that messages regarding the same reservation or room or such can not be handled at in parallel.

Q: Are there any optimisations you see but didn't implement?

A: In this simple case I don't immediately see optimisations.
