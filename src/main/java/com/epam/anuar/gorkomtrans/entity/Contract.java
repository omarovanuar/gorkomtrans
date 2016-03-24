package com.epam.anuar.gorkomtrans.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contract extends BaseEntity {
    private Customer customer;
    private String customerName;
    private double totalGarbageCapacity;
    private Money contractAmount;
    private DateTime providingTerm;
    private DateTime signDate;
    private String customerBank;

    private static Logger log = LoggerFactory.getLogger(Customer.class.getName());

    public Contract() {
    }

    public Contract(Integer id, Customer customer, Money contractAmount, DateTime providingTerm, DateTime signDate) {
        super(id);
        this.customer = customer;
        this.customerName = customer.getFullName();
        this.totalGarbageCapacity = customer.getCustomerGarbageCapacity();
        this.contractAmount = contractAmount;
        this.providingTerm = providingTerm;
        this.signDate = signDate;
        this.customerBank = customer.getBankDetails();
    }

    public String getCustomerName() {
        if (customerName == null) {
            return customer.getFullName();
        }
        return customerName;
    }

    public Customer getCustomer() {
        if (customer == null) {
            log.debug("No customer object for Contract object");
            throw new NullPointerException();
        }
        return customer;
    }

    public double getTotalGarbageCapacity() {
        if (totalGarbageCapacity == 0) {
            return customer.getCustomerGarbageCapacity();
        }
        return totalGarbageCapacity;
    }

    public String getCustomerBank() {
        if (customerBank == null) {
            return customer.getBankDetails();
        }
        return customerBank;
    }

    public void setTotalGarbageCapacity(double totalGarbageCapacity) {
        this.totalGarbageCapacity = totalGarbageCapacity;
    }

    public Money getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Money contractAmount) {
        this.contractAmount = contractAmount;
    }

    public DateTime getProvidingTerm() {
        return providingTerm;
    }

    public void setProvidingTerm(DateTime providingTerm) {
        this.providingTerm = providingTerm;
    }

    public DateTime getSignDate() {
        return signDate;
    }

    public void setSignDate(DateTime signDate) {
        this.signDate = signDate;
    }
}
