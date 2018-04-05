package com.example.chikara.seviceandthread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chikara on 3/12/18.
 */

public class NotificationId {
    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }

}
