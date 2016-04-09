package com.epam.anuar.gorkomtrans.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contract extends BaseEntity {
    private User user;
    private GarbageTechSpecification gts;
    private Money contractAmount;
    private DateTime providingTerm;
    private DateTime signDate;

    private static Logger log = LoggerFactory.getLogger(Contract.class.getName());

    public Contract() {
    }

    public Contract(Integer id, User user, GarbageTechSpecification gts, Money contractAmount, DateTime providingTerm, DateTime signDate) {
        super(id);
        this.user = user;
        this.contractAmount = contractAmount;
        this.providingTerm = providingTerm;
        this.signDate = signDate;
        this.gts = gts;
    }

    public User getUser() {
        if (user == null) {
            log.debug("No user object for contract object");
            throw new NullPointerException();
        }
        return user;
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
