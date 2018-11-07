package com.nobbyknox.platoon.entities;

import com.nobbyknox.platoon.util.BigDecimalWrapper;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @NotNull
    @Size(min = 2, message = "Client name should have at least 2 characters")
    @Size(max = 255, message = "You serious? That is a lot for a client name.")
    public String client;

    @NotNull
    @Min(value = 1, message = "VAT rate must be greater than 0")
    @Max(value = 20, message = "VAT rate is too high")
    public long vatRate;

    public Date invoiceDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    public List<LineItem> lineItems;

    public Invoice() {
    }

    public Invoice(String client, long vatRate, Date invoiceDate) {
        this.client = client;
        this.vatRate = vatRate;
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimalWrapper(0D).getValue();

        if (this.lineItems != null && this.lineItems.size() > 0) {
            for (LineItem item : this.lineItems) {
                subTotal = subTotal.add(item.getLineItemTotal());
            }
        }

        return subTotal;
    }

    public BigDecimal getVat() {
        return new BigDecimalWrapper(this.vatRate).getValue().divide(this.getSubTotal(), RoundingMode.HALF_UP).multiply(new BigDecimalWrapper(100D).getValue());
    }

    public BigDecimal getTotal() {
        return getSubTotal().add(getVat()).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + id +
            ", client='" + client + '\'' +
            ", vatRate=" + vatRate +
            ", invoiceDate=" + invoiceDate +
            '}';
    }
}
