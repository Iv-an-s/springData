package com.geekbrains.isemenov.spring.web.core.listener;

public interface Listenable {
    void registerListener(Listener listener);

    void unregisterListener(Listener listener);
}
