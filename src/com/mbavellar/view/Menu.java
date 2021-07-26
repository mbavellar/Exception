package com.mbavellar.view;

import com.mbavellar.model.entities.abstracts.Reservable;
import com.mbavellar.model.services.Message;
import com.mbavellar.model.services.ReservationDAO;
import com.mbavellar.model.services.Validator;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Menu {
  
  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Message.DATE_PATTERN);
  
  public static void showOptions() {
    System.out.printf("| %-25s | %1s |%n", "Make a Reservation!", "((1))");
    System.out.printf("| %-25s | %1s |%n", "Update a Reservation!", "((2))");
    System.out.printf("| %-25s | %1s |%n", "Delete a Reservation!", "((3))");
    System.out.printf("| %-25s | %1s |%n", "Show all Reservations!", "((4))");
    System.out.println();
    System.out.printf("| %-25s | %1s |%n", "Exit!", "((0))");
  }
  
  public static void showReservations() {
    System.out.println();
    System.out.println("\t\t\t\t\tRESERVATIONS");
    System.out.printf("| %-5s | %-11s | %11s |%n", "Nº", "CheckIn", "CheckOut");
    System.out.println();
    for (Reservable reservable : Reservable.getReservations())
      System.out.printf("| %-5s | %-11s | %11s |%n",
          reservable.getRoomNumber(),
          simpleDateFormat.format(reservable.getCheckIn()),
          simpleDateFormat.format(reservable.getCheckOut()));
    System.out.println();
  }
  
  public static void showMenuOption(final Scanner scanner) {
    int option = -1;
    while (option != 0) {
      Menu.showOptions();
      switch (option = Validator.readInt(scanner, Message.WHAT_DO_YOU_WANT_TO_DO)) {
        case 1:
          ReservationDAO.create(scanner);
          break;
        case 2:
          ReservationDAO.update(scanner);
          break;
        case 3:
          ReservationDAO.delete(scanner);
          break;
        case 4:
          Menu.showReservations();
          break;
        case 0:
          break;
        default:
          System.out.println(Message.PLEASE_ENTER_A_VALID_OPTION);
          break;
      }
    }
  }
}
