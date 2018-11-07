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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceTests {

    @Autowired
    private InvoiceRepository repo;

    @Test
    public void shouldCalcSubTotal() {
        Invoice invoice = new Invoice("First", 15, new Date());
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("Coffee", 2, new BigDecimalWrapper(87.56D).getValue()));
        items.add(new LineItem("Milk", 3, new BigDecimalWrapper(14.33D).getValue()));
        invoice.lineItems = items;

        BigDecimal target = new BigDecimalWrapper(218.11D).getValue();

        assertEquals(0, target.compareTo(invoice.getSubTotal()));
    }

    @Test
    public void shouldCalcVat() {
        Invoice invoice = new Invoice("Second", 15, new Date());
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("Coffee", 2, new BigDecimalWrapper(45.0D).getValue()));
        items.add(new LineItem("Milk", 1, new BigDecimalWrapper(10.0D).getValue()));
        invoice.lineItems = items;

        BigDecimal target = new BigDecimalWrapper(15.0D).getValue();

        assertEquals(0, target.compareTo(invoice.getVat()));
    }

    @Test
    public void shouldCalcTotal() {
        Invoice invoice = new Invoice("Second", 15, new Date());
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("Coffee", 2, new BigDecimalWrapper(45.0D).getValue()));
        items.add(new LineItem("Milk", 1, new BigDecimalWrapper(10.0D).getValue()));
        invoice.lineItems = items;

        BigDecimal target = new BigDecimalWrapper(115.0D).getValue();

        assertEquals(0, target.compareTo(invoice.getTotal()));
    }

    @Test
    public void shouldFailOnShortClientName() {
        Invoice invoice = new Invoice("A", 15, new Date());

        try {
            repo.save(invoice);
            fail("Expected validation error on client name being too short");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void shouldFailOnLongClientName() {
        Invoice invoice = new Invoice(makeLongName(), 15, new Date());

        try {
            repo.save(invoice);
            fail("Expected validation error on client name being too long");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void vatRateShouldFailOnZero() {
        Invoice invoice = new Invoice("ABC", 0, new Date());

        try {
            repo.save(invoice);
            fail("Expected validation error on zero VAT rate");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    @Test
    public void vatRateShouldFailOnTooHighValue() {
        Invoice invoice = new Invoice("ABC", 21, new Date());

        try {
            repo.save(invoice);
            fail("Expected validation error on too high VAT rate");
        } catch (TransactionSystemException exc) {
            // nothing
        }
    }

    // -------------------------------------------------------------------------
    // Private functions
    // -------------------------------------------------------------------------

    private String makeLongName() {
        StringBuilder builder = new StringBuilder();
        String base = "0123456789";
        int repeat = 26;

        for (int i = 0; i < repeat; i++) {
            builder.append(base);
        }

        return builder.toString();
    }
}
