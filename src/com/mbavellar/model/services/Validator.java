package com.mbavellar.model.services;

import com.mbavellar.model.entities.abstracts.Reservable;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Validator {
  
  public static Date readDate(final Scanner scanner, final String msg) {
    Date date;
    System.out.print(msg);
    while (null == (date = dateTryParse(scanner))) {
      System.out.print("Please enter a valid formatted date dd/MM/yyyy: ");
    }
    return date;
  }
  
  private static Date dateTryParse(final Scanner scanner) {
    try {
      return Reservable.getParsedDate(scanner.nextLine());
    } catch (ParseException pe) {
      System.out.println(pe.getMessage());
      return null;
    }
  }
  
  public static Integer readInt(final Scanner scanner, final String msg) {
    Integer value;
    do {
      System.out.print(msg);
    } while (null == (value = intTryParse(scanner)));
    return value;
  }
  
  private static Integer intTryParse(final Scanner scanner) {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException nfe) {
        System.out.println(nfe.getMessage());
      return null;
    }
  }
}
