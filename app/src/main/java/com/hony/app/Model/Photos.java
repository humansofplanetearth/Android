package com.hony.app.Model;

import java.util.List;

public class Photos {
    private List alt_sizes;
    private String caption;
    private Original_size original_size;

    public Photos() {
    }

    public List getAlt_sizes() {
        return this.alt_sizes;
    }

    public void setAlt_sizes(List alt_sizes) {
        this.alt_sizes = alt_sizes;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Original_size getOriginal_size() {
        return this.original_size;
    }

    public void setOriginal_size(Original_size original_size) {
        this.original_size = original_size;
    }
}