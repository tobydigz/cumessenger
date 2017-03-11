package com.digzdigital.cumessenger.eventbus;

/**
 * Created by Digz on 09/03/2017.
 */

public class FirebaseEvent {
    public final EventType type;

    public FirebaseEvent(EventType type){
        this.type = type;
    }
}
