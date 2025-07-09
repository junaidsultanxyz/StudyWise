package com.junaid.studywise.model;

public class Note {
    private int id;
    private String title;
    private String summary;
    private String content;
    private String dateCreated;
    private String dateModified;

    // Constructor
    public Note(int id, String title, String summary, String content, String dateCreated, String dateModified) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    // Default constructor
    public Note() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                '}';
    }
}
