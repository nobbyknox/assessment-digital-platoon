package com.nobbyknox.platoon;

import com.nobbyknox.platoon.entities.Invoice;
import com.nobbyknox.platoon.entities.LineItem;
import com.nobbyknox.platoon.repositories.InvoiceRepository;
import com.nobbyknox.platoon.util.BigDecimalWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemTests {

    @Autowired
    private InvoiceRepository repo;

    @Test
    public void shouldCalcItemTotal() {
        LineItem item = new LineItem("Coffee", 2, new BigDecimalWrapper(99.99D).getValue());
        BigDecimal target = new BigDecimalWrapper(199.98D).getValue();
        assertEquals(0, target.compareTo(item.getLineItemTotal()));
    }

    @Test
    public void shouldFailOnQuantityTooFew() {
        Invoice invoice = new Invoice("ABC", 15, new Date());
        invoice.lineItems = Arrays.asList(new LineItem("Long Plank", 0, new BigDecimalWrapper(10D).getValue()));

        try {
            repo.save(invoice);
            fail("Expected validation error on zero quantity");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void shouldFailOnQuantityTooHigh() {
        Invoice invoice = new Invoice("ABC", 15, new Date());
        invoice.lineItems = Arrays.asList(new LineItem("Long Plank", 10001, new BigDecimalWrapper(10D).getValue()));

        try {
            repo.save(invoice);
            fail("Expected validation error on humongous quantity");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void shouldFailOnDescriptionTooShort() {
        Invoice invoice = new Invoice("ABC", 15, new Date());
        invoice.lineItems = Arrays.asList(new LineItem("", 1, new BigDecimalWrapper(10D).getValue()));

        try {
            repo.save(invoice);
            fail("Expected validation error on empty description");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void shouldFailOnDescriptionTooHigh() {
        Invoice invoice = new Invoice("ABC", 15, new Date());
        invoice.lineItems = Arrays.asList(new LineItem(makeLongDescription(), 1, new BigDecimalWrapper(10D).getValue()));

        try {
            repo.save(invoice);
            fail("Expected validation error on too long description");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void shouldFailOnUnitPriceTooLow() {
        Invoice invoice = new Invoice("ABC", 15, new Date());
        invoice.lineItems = Arrays.asList(new LineItem("Some screws", 1, new BigDecimalWrapper(0D).getValue()));

        try {
            repo.save(invoice);
            fail("Expected validation error on too low unit price");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    // -------------------------------------------------------------------------
    // Private functions
    // -------------------------------------------------------------------------

    private String makeLongDescription() {
        StringBuilder builder = new StringBuilder();
        String base = "abcdefghij";
        int repeat = 26;

        for (int i = 0; i < repeat; i++) {
            builder.append(base);
        }

        return builder.toString();
    }

}
