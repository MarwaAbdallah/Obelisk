package com.hotel.obelisk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue
    @JsonProperty("resId")
    private long resId;
    @JsonProperty("fromDate")
    private LocalDate fromDate;
    @JsonProperty("toDate")
    private LocalDate toDate;
    @JsonProperty("customerEmail")
    private String customerEmail;
    @OneToOne
    private User resCustodian; // User who made the reservation. Goal is RBAC for service accounts
    @OneToOne
    private Room bedding;
    @JsonProperty("priceTotal")
    private double priceTotal;

    public Reservation(){}
    public Reservation(LocalDate fromDate, LocalDate toDate,
                       User resCustodian, Room bedding,
                       String customerEmail) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.customerEmail = customerEmail;
        this.resCustodian = resCustodian;
        this.setBedding(bedding);
        this.setPriceTotal(); // sets reservation price depending on
                              // number of days * Room price per night

    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal() {
        long noOfDaysBetween = DAYS.between(fromDate, toDate);
        this.priceTotal =noOfDaysBetween* bedding.getPrice();
        System.out.println("There are "+noOfDaysBetween+" days between" +
                "\n"+ fromDate +" -and- "+ toDate+"\nPrice per night of room: "+bedding.getPrice()+
                "and the price to is : "+priceTotal+"\n");
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public LocalDate getFromStayDate() {
        return fromDate;
    }

    public void setFromStayDate(LocalDate fromStayDate) {
        this.fromDate = fromStayDate;
    }

    public LocalDate getToStayDate() {
        return toDate;
    }

    public void setToStayDate(LocalDate toStayDate) {
        this.toDate = toStayDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public User getResCustodian() {
        return resCustodian;
    }

    public void setResCustodian(User resCustodian) {
        this.resCustodian = resCustodian;
    }

    public Room getBedding() {
        return bedding;
    }

    public void setBedding(Room bedding) {

        this.bedding = bedding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return resId == that.resId &&
                Double.compare(that.priceTotal, priceTotal) == 0 &&
                fromDate.equals(that.fromDate) &&
                Objects.equals(toDate, that.toDate) &&
                customerEmail.equals(that.customerEmail) &&
                resCustodian.equals(that.resCustodian) &&
                bedding.equals(that.bedding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId, fromDate, toDate, customerEmail, resCustodian, bedding, priceTotal);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "resId=" + resId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", customerEmail='" + customerEmail + '\'' +
                ", resCustodian=" + resCustodian +
                ", bedding=" + bedding +
                ", priceTotal=" + priceTotal +
                '}';
    }
}
