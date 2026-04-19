package com.example.mindmines.models.game;

import java.time.OffsetDateTime;

public class Expedition {
    private Integer expeditionId;
    private String title;
    private String type;
    private Integer level;
    private OffsetDateTime start;
    private OffsetDateTime finish;
    private boolean isFinished;

    public Integer getExpeditionId() {
        return expeditionId;
    }

    public void setExpeditionId(Integer expeditionId) {
        this.expeditionId = expeditionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    public OffsetDateTime getFinish() {
        return finish;
    }

    public void setFinish(OffsetDateTime finish) {
        this.finish = finish;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Expedition(Integer expeditionId,
                      String title,
                      String type,
                      Integer level,
                      OffsetDateTime start,
                      OffsetDateTime finish,
                      boolean isFinished) {
        this.expeditionId = expeditionId;
        this.title = title;
        this.type = type;
        this.level = level;
        this.start = start;
        this.finish = finish;
        this.isFinished = isFinished;
    }
}
