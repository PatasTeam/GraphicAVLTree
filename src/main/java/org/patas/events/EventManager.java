package org.patas.events;

public interface EventManager<T> {
    void handleEvent(Event event, T element);
}
