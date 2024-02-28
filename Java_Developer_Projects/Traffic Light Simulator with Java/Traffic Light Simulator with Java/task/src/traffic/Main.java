package traffic;

import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    new MainMenuController(new Scanner(System.in), new ConsolePrinter()).run();
  }
}