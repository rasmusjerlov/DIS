package opgave1;

import java.util.Scanner;

public class readThread extends Thread{
   MyString myString;
   String string;

   Scanner scanner = new Scanner(System.in);
   public readThread(MyString myString) {
      this.myString = myString;
   }
   public void readString() {
      String in = scanner.nextLine();
      myString.setString(in);


   }
   public void run() {
      while (true) {
         readString();
      }
   }
}
