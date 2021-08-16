package com.fastcampus.jpa.bookmanager.domain.listener;

import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class MyEntityListener {
    @PrePersist
    public void prePersist(Object o) {
        if(o instanceof Auditable) {
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PostUpdate
    public void postUpdate(Object o) {
        if(o instanceof Auditable) {
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

}
