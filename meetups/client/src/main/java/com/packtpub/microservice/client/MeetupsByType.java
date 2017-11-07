package com.packtpub.microservice.client;

import java.util.Arrays;

/**
 * @author khaled
 */
public class MeetupsByType {
    public final String[] meetups;

    public MeetupsByType() {
        this(new String[] {});
    }

    public MeetupsByType(String[] meetups) {
        this.meetups = meetups;
    }

    @Override
    public String toString() {
        return Arrays.toString(meetups);
    }

}
