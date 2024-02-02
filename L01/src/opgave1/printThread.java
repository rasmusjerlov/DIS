package opgave1;

public class printThread extends Thread{
    private MyString myString;

    public printThread(MyString myString) {
        this.myString = myString;
    }

    public void run() {
        while (true) {
            System.out.println(myString.getString());
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
