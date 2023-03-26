import java.math.BigDecimal;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Object o = new Object();
        o = 12;
        BigDecimal a = new BigDecimal(o.toString());
        System.out.println(a);
    }
}
