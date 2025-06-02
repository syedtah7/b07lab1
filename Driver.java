import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        try {
            Polynomial p = new Polynomial();
            System.out.println(p.evaluate(3));

            double[] coeff1 = {6, 0, 0, 5};
            int[] exp1 = {0, 1, 2, 3};
            Polynomial p1 = new Polynomial(coeff1, exp1);

            double[] coeff2 = {0, -2, 0, 0, -9};
            int[] exp2 = {0, 1, 2, 3, 4};
            Polynomial p2 = new Polynomial(coeff2, exp2);

            Polynomial s = p1.add(p2);
            System.out.println(s.evaluate(0.1));

            if(s.hasRoot(1))
                System.out.println("1 is a root of s");
            else
                System.out.println("1 is not a root of s");

            Polynomial mult_poly = p2.multiply(p1);
            System.out.println(mult_poly.evaluate(2));
            mult_poly.saveToFile("poly_test.txt");
            Polynomial fromFile = new Polynomial(new File("poly_test.txt"));
            System.out.println(fromFile.evaluate(2));
            
        } catch (IOException e) {
            System.out.println("File error");
        }
    }
}