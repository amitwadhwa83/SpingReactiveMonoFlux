package com.wiredbraincoffee.productapiannotation.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class ProductEvent {

    private Long eventId;
    private String eventType;

    public ProductEvent() {
    }

    public ProductEvent(Long eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public Long getEventId() {
        return eventId;
    }

    public ProductEvent setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public ProductEvent setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("eventId", eventId)
                .append("eventType", eventType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEvent that = (ProductEvent) o;
        return eventId.equals(that.eventId) && eventType.equals(that.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventType);
    }
}
