package com.gg.proj.model;

public class TopicModel {

    private Integer id;

    private String name;

    public TopicModel() {
    }

    public TopicModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
