package com.mbavellar.model.entities;

import com.mbavellar.model.entities.abstracts.Reservable;
import com.mbavellar.model.exceptions.InvalidDateArgumentException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation extends Reservable {
    
    public Reservation(int roomNumber, Date checkIn, Date checkOut) {
        super(roomNumber, checkIn, checkOut);
    }
    
    public Long stayDuration() {
        long duration = checkOut.getTime() - checkIn.getTime();
        return TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS);
    }
    
    public void saveReservation(int roomNumber, Date checkIn, Date checkOut) {
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public void updateDates(Date checkIn, Date checkOut) {
        if (isCheckInAndOrCheckOutAfterPresentDate(checkIn, checkOut))
            throw new InvalidDateArgumentException(INVALID_CHECK_IN_CHECK_OUT_MESSAGE);
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}