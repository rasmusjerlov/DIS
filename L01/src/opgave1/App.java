package opgave1;

public class App {
    public static void main(String[] args) {
        MyString myString = new MyString();
        readThread rt = new readThread(myString);
        printThread pt = new printThread(myString);

        rt.start();
        pt.start();
    }
}
