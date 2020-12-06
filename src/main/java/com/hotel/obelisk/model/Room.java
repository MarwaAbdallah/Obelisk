package com.hotel.obelisk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue
    @JsonProperty("roomNo")
    private long roomNo;
    //@JsonProperty("isBooked")
    private boolean isBooked;
    @JsonProperty("price")
    private double price;

    public Room() {
    }

    public Room(boolean isBooked, double price) {
        this.isBooked = isBooked;
        BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        this.price = bd.doubleValue();
    }


    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(long roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNo=" + roomNo +
                ", isBooked=" + isBooked +
                ", price=" + price +
                '}';
    }
}
