package com.mbavellar.model.services;

import com.mbavellar.model.entities.Reservation;
import com.mbavellar.model.entities.abstracts.Reservable;
import com.mbavellar.model.entities.enums.Operation;
import com.mbavellar.model.exceptions.InvalidDateArgumentException;
import com.mbavellar.view.Menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public abstract class ReservationDAO {
  
  private static final String filePathName = "Reservations";
  private static final File file = new File(filePathName);
  
  public static void create(final Scanner scanner) {
    try {
      Reservation reservation = new Reservation(
          Validator.readInt(scanner, Message.ROOM_NUMBER),
          Validator.readDate(scanner, Message.CHECK_IN_DATE),
          Validator.readDate(scanner, Message.CHECK_OUT_DATE));
      save(reservation);
      Reservable.getReservations().add(reservation);
    } catch (InvalidDateArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
  
  public static void readAll() {
    try {
      if (file.exists())
        Reservable.initReservations(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void update(final Scanner scanner) {
    Menu.showReservations();
    System.out.println(Message.ROOM_TO_UPDATE);
    var reservable = Reservable.getReservation(Validator.readInt(scanner, Message.ROOM_NUMBER));
    if (reservable != null) {
      reservable.updateDates(Validator.readDate(scanner, Message.CHECK_IN_DATE),
          Validator.readDate(scanner, Message.CHECK_OUT_DATE));
      write(reservable, Operation.Update);
      System.out.println(Message.SUCCESSFULLY_UPDATED);
    } else {
      System.out.println(Message.NOT_FOUNDED);
    }
  }
  
  public static void delete(final Scanner scanner) {
    Menu.showReservations();
    System.out.println(Message.ROOM_TO_REMOVE);
    var reservable = Reservable.getReservation(Validator.readInt(scanner, Message.ROOM_NUMBER));
    if (reservable != null) {
      write(reservable, Operation.Delete);
      Reservable.getReservations().remove(reservable);
      System.out.println(Message.SUCCESSFULLY_DELETED);
    } else {
      System.out.println(Message.NOT_FOUNDED);
    }
  }
  
  public static void save(Reservation reservation) {
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true);
      fileWriter.append(reservation.toShorterString());
      fileWriter.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
  }
  
  public static void write(Reservable reservable, Operation operation) {
    FileWriter fileWriter = null;
    try {
      if (file.exists()) {
        List<String> reservationList = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
      
        for (var line : reservationList) {
          String[] data = line.split(",");
          if (data[0].equals(reservable.getRoomNumber().toString())) {
            if (operation.equals(Operation.Delete)) {
              continue;
            } else if (operation.equals(Operation.Update)) {
              line = reservable.toShorterString();
            }
          }
          fileWriter.append(line);
          fileWriter.append("\n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
  }
}