package com.mbavellar.view.application;

import com.mbavellar.model.services.ReservationDAO;
import com.mbavellar.view.Menu;
import java.util.Scanner;

public class Program {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        ReservationDAO.readAll();
        Menu.showMenuOption(scanner);
    }
}