package com.nobbyknox.platoon.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @NotNull
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 10000, message = "We cannot fulfil such large orders")
    public long quantity;

    @NotNull
    @Size(min = 1, message = "Description required")
    @Size(max = 255, message = "Description should be shorter than 255 character")
    public String description;

    @NotNull
    @DecimalMin(value = "1", message = "Unit price must be greater or equal than 1")
    public BigDecimal unitPrice;

    public LineItem() {
    }

    public LineItem(String description, long quantity, BigDecimal unitPrice) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineItemTotal() {
        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    @Override
    public String toString() {
        return "LineItem{" +
            "id=" + id +
            ", quantity=" + quantity +
            ", description='" + description + '\'' +
            ", unitPrice=" + unitPrice +
            '}';
    }
}
