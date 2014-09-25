package com.oleksiykovtun.gwtsimpleapp.shared.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Entity transmitted through RPC service.
 */
@Entity
@Index
public class Item implements Serializable {
    private static int nextId = 0;

    @Id
    public long id;

    public String name;
    public int quantity;

    public Item() {
        this.id = ++nextId;
    }

    public Item(String name, int quantity) {
        this.id = ++nextId;
        this.quantity = quantity;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
