package com.mbavellar.model.entities.abstracts;

import com.mbavellar.model.entities.Reservation;
import com.mbavellar.model.entities.interfaces.Reserving;
import com.mbavellar.model.exceptions.InvalidDateArgumentException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class Reservable implements Reserving {
  
  protected static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
  protected static final List<Reservable> reservations = new ArrayList<>();
  protected static final String INVALID_CHECK_IN_DATE = "CheckIn CANNOT be after CheckOut!";
  protected static final String INVALID_CHECK_IN_CHECK_OUT_MESSAGE = "CheckIn and CheckOut must NOT be before today!";
  
  protected Integer roomNumber;
  protected Date checkIn;
  protected Date checkOut;
  
  protected Reservable(int roomNumber, Date checkIn, Date checkOut) {
    if (isCheckInAndOrCheckOutAfterPresentDate(checkIn, checkOut))
      throw new InvalidDateArgumentException(INVALID_CHECK_IN_CHECK_OUT_MESSAGE);
    if (isCheckInBefore(checkIn, checkOut))
      throw new InvalidDateArgumentException(INVALID_CHECK_IN_DATE);
    
    this.roomNumber = roomNumber;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
  }
  
  public static Reservable getReservation(int roomNumber) {
    return reservations.stream().filter(p -> p.getRoomNumber() == roomNumber).findFirst().orElse(null);
  }
  
  public Integer getRoomNumber() {
    return roomNumber;
  }
  
  protected void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }
  
  public Date getCheckIn() {
    return checkIn;
  }
  
  public Date getCheckOut() {
    return checkOut;
  }
  
  public static List<Reservable> getReservations() {
    return reservations;
  }
  
  /**
   *
   * @param checkIn the Check In date.
   * @param checkOut the Check Out date.
   * @return true if checkIn & checkOut are after present date.
   */
  protected Boolean isCheckInAndOrCheckOutAfterPresentDate(Date checkIn, Date checkOut) {
    Date dateNow = new Date();
    if (checkOut == null)
      return checkIn.before(dateNow);
    else
      return checkIn.before(dateNow) || checkOut.before(dateNow);
  }
  
  protected Boolean isCheckInBefore(final Date checkIn, final Date checkOut) {
    return checkIn.after(checkOut);
  }
  
  public static void initReservations(List<String> linesOfTheText) throws ParseException {
    for (String line : linesOfTheText) {
      String[] data = line.split(",");
      reservations.add(new Reservation(Integer.parseInt(data[0]),
          simpleDateFormat.parse(data[1]),
          simpleDateFormat.parse(data[2])));
    }
  }
  
  public static Date getParsedDate(String parsableDate) throws ParseException {
    return simpleDateFormat.parse(parsableDate);
  }
  
  @Override
  public String toString() {
    return "Reservation ( " +
        "Room Number: " + roomNumber +
        ", Check In: " + simpleDateFormat.format(checkIn) +
        ", Check Out: " + simpleDateFormat.format(checkOut) +
        ", Duration: " + stayDuration() + " nights";
  }
  
  public String toShorterString() {
    return roomNumber + ", " + simpleDateFormat.format(checkIn) + ", " + simpleDateFormat.format(checkOut);
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reservation that = (Reservation) o;
    return roomNumber.equals(that.roomNumber);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(roomNumber);
  }
}