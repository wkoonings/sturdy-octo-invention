Q&A:

Q: Shortly explain your solution and your assumptions.

A: This solution makes use of a RabbitMQ docker image to handle messages between the importer and the consumer. The importer receives messages through a RESTController and sends them to the RabbitMQ Queue. Here they wait until the consumer listens to the message and it is handled. 

Assumption:
  - Importers that receive a message immediately put the message in the Queue and any calculations being done in the importer service do not alter the order of the messages that 
are received.

Edit:
A: The importer now holds the message for a maximum of 10 seconds to see if other messages were sent earlier. A timestamp is used in the message which is set to the time the message was sent. When the message arrives at the importer, the importer starts a TimerTask that queues the message 10 seconds after the inital send time.
by the client. 

The 10 seconds basically allow for messages with a delay to still be handled before any messages that arrived in time but were send later.

Assumptions:
  - If a message arrives 10 seconds late or more, the connection has timed out and the message can be discarded.

Q: Do you see any problem with this setup?

A: In this simple case I don't see any problems. Multiple importers can run concurrentely and all messages that are received will be handled by the consumer in the order of which they entered the Queue. With the Queue being durable and RabbitMQ saving the messages in a database, no messages will get lost until the consumer handles them.

Edit:
A: 
  - The current solution does not take into consideration timezones. When a message is sent from a different timezone than the SystemDefault, the 10 seconds difference will be completely off.
  - If a message is delayed more than 10 seconds the message is discarded. This is definetely not a perfect solution. 

Q: What kind of data does the message by the sender NEED to contain to ensure they are imported in the correct order?

A: After a message is send to the Queue, RabbitMQ handles ordering and messages that arrived first in the Queue will be handled first. So if there is only one consumer and 
messages are immediately put in the Queue after reaching the importer, no additional data is needed for the consumer to handle the messages in the same order as which they are 
received.

Edit:
A: The one thing needed for this setup is a timestamp. In this solution a timestamp is used to calculate which message is sent first.

Q: Describe what should be taken care of when having multiple consumers.

A: There should be good resource locking in place so that messages regarding the same reservation or room or such can not be handled at in parallel.

Edit:
A: 

Q: Are there any optimisations you see but didn't implement?

A: In this simple case I don't immediately see optimisations.

Edit:
A: As of now there is a standard delay of 10 seconds before any message can be processed by the consumer. I have chosen the number 10 random, but with a good internet connection this should be able to be a lot smaller.
