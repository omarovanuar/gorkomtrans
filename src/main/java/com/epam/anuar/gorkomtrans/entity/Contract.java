package com.epam.anuar.gorkomtrans.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contract extends BaseEntity {
    private Customer customer;
    private GarbageTechSpecification gts;
    private Money contractAmount;
    private DateTime providingTerm;
    private DateTime signDate;

    private static Logger log = LoggerFactory.getLogger(Customer.class.getName());

    public Contract() {
    }

    public Contract(Integer id, Customer customer, GarbageTechSpecification gts, Money contractAmount, DateTime providingTerm, DateTime signDate) {
        super(id);
        this.customer = customer;
        this.contractAmount = contractAmount;
        this.providingTerm = providingTerm;
        this.signDate = signDate;
        this.gts = gts;
    }

    public Customer getCustomer() {
        if (customer == null) {
            log.debug("No customer object for contract object");
            throw new NullPointerException();
        }
        return customer;
    }

    public GarbageTechSpecification getGarbageTechSpecification() {
        if (gts == null) {
            log.debug("No tech specification object for contract object");
            throw new NullPointerException();
        }
        return gts;
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
