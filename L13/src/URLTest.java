import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class URLTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.valutakurser.dk");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String line;
        boolean found = false;
        while ((line = br.readLine()) != null && !found) {
            String[] a = line.split("/");
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i].equals("amerikanske-dollar")) {
                    found = true;
                }
            }
            br.readLine();
            if (found) {
                String s = line.substring(278, 284);
                System.out.println("Kursen for den amerikanske dollar er: " + s);
            }
//            String str = line.substring(278, 284);
//            System.out.println(str);
//            if (line.contains("Amerikanske dollar")) {
//                String[] lineArray = line.split("</div>");
//                System.out.println(Arrays.toString(lineArray));
//                System.out.println(lineArray[2]);
//                System.out.println(lineArray[2]);
//                String s = lineArray[2];
//                String[] sSplit = s.split(">");
//                String str = sSplit[1];
//                String[] sSplit2 = str.split("<");
//                String str2 = sSplit2[0];
//                System.out.println(str2);
//                System.out.println("Line: " + line);
//            }
        }
    }
}
