package com.nobbyknox.platoon.controllers;

import com.nobbyknox.platoon.entities.Invoice;
import com.nobbyknox.platoon.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository repo;

    @RequestMapping(value = "/invoices", method = RequestMethod.POST, produces = "application/json")
    public Invoice addInvoice(@Valid @RequestBody final Invoice invoice) {

        // Assign the current date/time to the invoice if invoiceDate is null from the incoming invoice
        if (invoice.invoiceDate == null) {
            invoice.invoiceDate = new Date();
        }

        return repo.save(invoice);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET, produces = "application/json")
    public List<Invoice> viewAllInvoices() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET, produces = "application/json")
    public Optional<Invoice> viewInvoice(@PathVariable long id) {
        return repo.findById(id);
    }
}
