package com.paulost.testapplibrareless.presentation.base;

import androidx.annotation.Nullable;

public class Event<T> {
    private boolean consumed;
    private final T content;

    public Event(T content) {
        this.content = content;
    }

    public final boolean getConsumed() {
        return this.consumed;
    }

    public final T consume() {
        if (this.consumed) {
            return null;
        } else {
            this.consumed = true;
            return this.content;
        }
    }

    public final T peek() {
        return this.content;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if ((Event) this == other) {
            return true;
        }
        if (other == null) return false;
        if (this.getClass() != other.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;
        if (this.content != otherEvent.content) {
            return false;
        }
        return this.consumed == ((Event) other).consumed;
    }

    public int hashCode() {
        T content = this.content;
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + Boolean.valueOf(this.consumed).hashCode();
        return result;
    }

}
