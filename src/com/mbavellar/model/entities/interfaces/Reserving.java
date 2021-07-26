package com.mbavellar.model.entities.interfaces;

import java.util.Date;

public interface Reserving {
  Long stayDuration();
  void saveReservation(int roomNumber, Date checkIn, Date checkOut);
  void updateDates(Date checkIn, Date checkOut);
  String toShorterString();
  @Override
  String toString();
  @Override
  boolean equals(Object o);
  @Override
  int hashCode();
}
