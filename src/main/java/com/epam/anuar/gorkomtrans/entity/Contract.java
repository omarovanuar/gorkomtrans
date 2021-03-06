package com.epam.anuar.gorkomtrans.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Contract extends BaseEntity {
    private static Logger log = LoggerFactory.getLogger(Contract.class.getName());

    private User user;
    private GarbageTechSpecification gts;
    private Double contractTotalCapacity;
    private Money contractAmount;
    private Integer providingMonthNumber;
    private DateTime signDate;
    private Status status;

    public Contract() {
    }

    public Contract(Integer id, User user, GarbageTechSpecification gts, Integer providingMonthNumber) {
        super(id);
        this.user = user;
        this.providingMonthNumber = providingMonthNumber;
        this.gts = gts;
        this.contractAmount = calculateContractAmount();
    }

    public Contract(Integer id, User user, GarbageTechSpecification gts, Money contractAmount, Integer providingMonthNumber, DateTime signDate) {
        super(id);
        this.user = user;
        this.contractAmount = contractAmount;
        this.providingMonthNumber = providingMonthNumber;
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

    public void setUser(User user) {
        this.user = user;
    }

    public GarbageTechSpecification getGarbageTechSpecification() {
        if (gts == null) {
            log.debug("No tech specification object for contract object");
            throw new NullPointerException();
        }
        return gts;
    }

    public void setGts(GarbageTechSpecification gts) {
        this.gts = gts;
    }

    public void setContractTotalCapacity(Double contractTotalCapacity) {
        this.contractTotalCapacity = contractTotalCapacity;
    }

    public Double getContractTotalCapacity() {
        return contractTotalCapacity;
    }

    public Double getTotalCapacity() {
        return gts.getCapacityPerMonth() * providingMonthNumber;
    }

    public String getContractTotalCapacityString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###.##", symbols);
        return df.format(getTotalCapacity());
    }

    private Money calculateContractAmount() {
        Double cost = getTotalCapacity() * 1411.9;
        return Money.parse("KZT " + Math.round(cost));
    }

    public Money getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Money contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getProvidingMonthNumber() {
        return providingMonthNumber;
    }

    public void setProvidingMonthNumber(Integer providingMonthNumber) {
        this.providingMonthNumber = providingMonthNumber;
    }

    public String getSignDateString() {
        if (signDate == null) {
            return "";
        }
        return signDate.toString("dd.MM.YYYY HH:mm");
    }

    public void setSignDate(DateTime signDate) {
        this.signDate = signDate;
    }

    public Status getStatus() {
        if (status == null) {
            return Status.NEW;
        }
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
