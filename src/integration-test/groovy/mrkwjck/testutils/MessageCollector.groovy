package mrkwjck.testutils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.messaging.Message

class MessageCollector {

    public static final String ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME = "ACCOUNT_EVENT_OUTPUT"

    private final OutputDestination outputDestination
    private final ObjectMapper objectMapper

    MessageCollector(OutputDestination outputDestination, ObjectMapper objectMapper) {
        this.outputDestination = outputDestination
        this.objectMapper = objectMapper
    }

    <T> T receive(String topicName, Class<T> messageClass) {
        Message<byte[]> message = outputDestination.receive(500L, topicName)
        if (message == null || message.getPayload() == null) {
            return null
        }
        objectMapper.readValue(message.getPayload(), messageClass)
    }

}
