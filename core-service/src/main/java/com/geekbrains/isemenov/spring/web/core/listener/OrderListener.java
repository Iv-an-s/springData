package com.geekbrains.isemenov.spring.web.core.listener;

public class OrderListener implements Listener {
    @Override
    public void onEventReceived(Event event) {
        System.out.println(event);
    }
}
