package ch.walkingfish.walkingfish.service;

public interface ProducerService {
    /**
     * Send a message to the queue
     * @param message the message to send
     */
    void send(Object message);
}
