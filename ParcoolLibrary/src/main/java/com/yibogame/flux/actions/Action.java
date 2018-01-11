package com.yibogame.flux.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lgvalle on 22/07/15.
 */
public class Action {
    private String type;
    private Map<String, Object> data;
    private Throwable throwable;

    public Action(String type, Map<String, Object> data) {
        this.type = type;
        this.data = data;
    }

    public Action(String type){
        this.type = type;
    }

    public static Builder type(String type) {
        return new Builder().with(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Map getData() {
        return data;
    }


    public static class Builder {

        private String type;
        private HashMap<String, Object> data;

        Builder with(String type) {
            if (type == null) {
                throw new IllegalArgumentException("Type may not be null.");
            }
            this.type = type;
            this.data = new HashMap<>();
            return this;
        }

        public Builder bundle(String key, Object value) {
            if (key == null) {
                throw new IllegalArgumentException("Key may not be null.");
            }

            if (value == null) {
                throw new IllegalArgumentException("Value may not be null.");
            }
            data.put(key, value);
            return this;
        }

        public Action build() {
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("At least one key is required.");
            }
            return new Action(type, data);
        }
    }


}
