package it.algos.base24.backend.entity;


import org.springframework.data.annotation.*;

import java.io.*;

public abstract class AbstractEntity implements Serializable {

    @Id
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    //    @SequenceGenerator(name = "idgenerator", initialValue = 1)
    public String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity that)) {
            return false; // null or not an AbstractEntity class
        }
        if (getId() != null) {
            return getId().equals(that.getId());
        }
        return super.equals(that);
    }

}
