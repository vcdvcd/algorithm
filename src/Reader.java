import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Reader {
    static StringTokenizer token = new StringTokenizer("");
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static String nextLine() throws IOException {
        return reader.readLine();
    }
    public static String next() throws IOException {
        while(!token.hasMoreTokens()){
            token = new StringTokenizer(reader.readLine());
        }
        return token.nextToken();
    }
    public static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}