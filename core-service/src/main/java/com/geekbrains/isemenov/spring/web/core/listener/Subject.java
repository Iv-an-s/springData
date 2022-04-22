package com.geekbrains.isemenov.spring.web.core.listener;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Subject implements Listenable {
    private Listener listener;
    private final ConcurrentLinkedDeque<Event> events;

    public Subject() {
        events = new ConcurrentLinkedDeque<>();
        start();
    }

    public void publishEvent(Event event) {
        events.add(event);
    }

    public void start() {
        Thread loop = new Thread(this::process);
        loop.setDaemon(true);
        loop.start();
    }

    private void process() {
        while (true) {
            Event event = events.poll();
            if (event != null) {
                listener.onEventReceived(event);
            }
        }
    }

    public boolean isListenerEmpty() {
        return this.listener == null;
    }

    @Override
    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void unregisterListener(Listener listener) {
    }
}
