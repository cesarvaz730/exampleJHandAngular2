package com.mx.anivip.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Pass.
 */

@Document(collection = "pass")
public class Pass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("price")
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Pass description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Pass price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pass pass = (Pass) o;
        if (pass.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pass{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
