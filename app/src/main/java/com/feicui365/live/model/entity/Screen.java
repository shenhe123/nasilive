package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Screen implements Serializable {
    String first_word;
    Matchevent content;
    String check_type;

    public String getCheck_type() {
        return check_type;
    }

    public void setCheck_type(String check_type) {
        this.check_type = check_type;
    }

    public Screen(String first_word, Matchevent content, String check_type) {
        this.first_word = first_word;
        this.content = content;
        this.check_type = check_type;
    }

    public Screen() {
    }

    public Screen(String first_word, Matchevent content) {
        this.first_word = first_word;
        this.content = content;
    }

    public String getFirst_word() {
        return first_word;
    }

    public void setFirst_word(String first_word) {
        this.first_word = first_word;
    }

    public Matchevent getContent() {
        return content;
    }

    public void setContent(Matchevent content) {
        this.content = content;
    }


}
