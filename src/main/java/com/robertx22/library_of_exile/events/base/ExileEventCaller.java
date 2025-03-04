package com.robertx22.library_of_exile.events.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExileEventCaller<T extends ExileEvent> {

    List<EventConsumer<T>> events = new ArrayList<>();

    boolean ordered = false;

    public ExileEventCaller() {
    }

    public T callEvents(T event) {

        if (!ordered) {
            ordered = true;
            events.sort(Comparator.comparingInt(x -> x.callOrder()));
        }

        events.forEach(x -> {
            if (!event.canceled) {
                try {
                    x.accept(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return event;
    }

    public void register(EventConsumer<T> t) {
        this.events.add(t);
        this.ordered = false;
    }

}
