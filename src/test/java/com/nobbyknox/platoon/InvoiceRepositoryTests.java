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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceRepositoryTests {

    @Autowired
    private InvoiceRepository repo;

	@Test
	public void shouldAddInvoiceWithNoItems() {
	    Invoice inv = new Invoice("First", 15, new Date());
        Invoice newInvoice = repo.save(inv);
        assertNotEquals(0, newInvoice.id);
	}

	@Test
	public void shouldAddInvoiceWithOneItem() {
	    Invoice inv = new Invoice("Second", 15, new Date());
        inv.lineItems = Arrays.asList(new LineItem("Long Plank", 12, new BigDecimalWrapper(147.5D).getValue()));

        Invoice newInvoice = repo.save(inv);

        assertNotEquals(0, newInvoice.id);

        Optional<Invoice> checkInvoice = repo.findById(newInvoice.id);

        assertNotNull(checkInvoice);
        assertTrue(checkInvoice.isPresent());
        assertEquals(1, checkInvoice.get().lineItems.size());
	}

	@Test
	public void shouldAddInvoiceWithMultipleItems() {
	    Invoice inv = new Invoice("Third", 15, new Date());
	    inv.lineItems = new ArrayList<>();

	    inv.lineItems.add(new LineItem("Long Plank", 12, new BigDecimalWrapper(147.5D).getValue()));
	    inv.lineItems.add(new LineItem("Medium Plank", 12, new BigDecimalWrapper(113.95D).getValue()));
	    inv.lineItems.add(new LineItem("Bar One", 1, new BigDecimalWrapper(8.95D).getValue()));

        Invoice newInvoice = repo.save(inv);

        assertNotEquals(0, newInvoice.id);

        Optional<Invoice> checkInvoice = repo.findById(newInvoice.id);

        assertNotNull(checkInvoice);
        assertTrue(checkInvoice.isPresent());
        assertEquals(3, checkInvoice.get().lineItems.size());
	}

}
