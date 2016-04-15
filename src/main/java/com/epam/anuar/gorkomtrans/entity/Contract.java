package com.epam.anuar.gorkomtrans.entity;

import org.joda.money.Money;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Contract extends BaseEntity {
    private User user;
    private GarbageTechSpecification gts;
    private Double contractTotalCapacity;
    private Money contractAmount;
    private Integer providingMonthNumber;
    private DateTime signDate;
    private Boolean isSanctioned = false;

    private static Logger log = LoggerFactory.getLogger(Contract.class.getName());

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
        return gts.getCapacityPerMonth() * providingMonthNumber;
    }

    public String getContractTotalCapacityString() {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        return df.format(getContractTotalCapacity());
    }

    private Money calculateContractAmount() {
        Double cost = getContractTotalCapacity() * 1411.9;
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

    public DateTime getSignDate() {
        return signDate;
    }

    public String getSignDateString() {
        return signDate.toString("dd.MM.YYYY HH:mm");
    }

    public void setSignDate(DateTime signDate) {
        this.signDate = signDate;
    }

    public String getSanctionedString() {
        if (getSanctioned() == true) {
            return "Sanctioned";
        } else {
            return "Not sanctioned";
        }
    }

    public Boolean getSanctioned() {
        return isSanctioned;
    }

    public void setSanctioned(Boolean sanctioned) {
        isSanctioned = sanctioned;
    }
}
