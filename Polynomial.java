import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;
    
    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }
	public Polynomial(double [] coeffs, int [] exps){
		int count =0;
		for(int i =0; i<coeffs.length; i++){
			if (coeffs[i] != 0) 
			{
				count++;
			}
		}
		coefficients = new double[count];
        exponents = new int[count];
		int index = 0;
        for (int i = 0; i < coeffs.length; i++) {
            if (coeffs[i] != 0) {
                coefficients[index] = coeffs[i];
                exponents[index] = exps[i];
                index++;
            }
        }
    }
	public Polynomial(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();
        int count = 1;
        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == '+' || line.charAt(i) == '-') {
                count++;
            }
        }

        coefficients = new double[count];
        exponents = new int[count];
        int position = 0;
        int start = 0;
        for (int i = 1; i <= line.length(); i++) {
            if (i == line.length() || line.charAt(i) == '+' || line.charAt(i) == '-') {
                String term = "";
                for (int j = start; j < i; j++) {
                    term += line.charAt(j);
                }
                start = i;
                int xPos = -1;
                for (int k = 0; k < term.length(); k++) {
                    if (term.charAt(k) == 'x') {
                        xPos = k;
                        break;
                    }
                }

                if (xPos == -1) {
                    coefficients[position] = Double.parseDouble(term);
                    exponents[position] = 0;
                } else {
                    String coeffStr = "";
                    for (int k = 0; k < xPos; k++) {
                        coeffStr += term.charAt(k);
                    }
                    double coeff;
                    if (coeffStr.equals("+")) {
                        coeff = 1;
                    } else if (coeffStr.equals("-")) {
                        coeff = -1;
                    } else if (coeffStr.isEmpty()) {
                        coeff = 1;
                    } else {
                        coeff = Double.parseDouble(coeffStr);
                    }

                    String expStr = "";
                    for (int k = xPos + 1; k < term.length(); k++) {
                        expStr += term.charAt(k);
                    }

                    int exp = expStr.isEmpty() ? 1 : Integer.parseInt(expStr);

                    coefficients[position] = coeff;
                    exponents[position] = exp;
                }
                position++;
            }
        }
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] > 0) {
                result += "+";
            }
            result += coefficients[i];
            if (exponents[i] > 0) {
                result += "x" + exponents[i];
            }
        }
        return result;
    }
	public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        String poly = "";
        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] > 0) {
                poly += "+";
            }
            poly += coefficients[i];
            if (exponents[i] > 0) {
                poly += "x";
                if (exponents[i] > 1) {
                    poly += exponents[i];
                }
            }
        }
        writer.write(poly);
        writer.close();
    }



	public Polynomial add(Polynomial other) {
        double[] coeffs = new double[this.coefficients.length + other.coefficients.length];
        int[] exps = new int[this.exponents.length + other.exponents.length];
        for (int i = 0; i < this.coefficients.length; i++) {
            coeffs[i] = this.coefficients[i];
            exps[i] = this.exponents[i];
        }
        for (int i = 0; i < other.coefficients.length; i++) {
            coeffs[this.coefficients.length + i] = other.coefficients[i];
            exps[this.exponents.length + i] = other.exponents[i];
        }
        for (int i = 0; i < exps.length; i++) {
            for (int j = i + 1; j < exps.length; j++) {
                if (exps[i] == exps[j]) {
                    coeffs[i] += coeffs[j];
                    coeffs[j] = 0;
                }
            }
        }
        int count = 0;
        for (int k =0; k<coeffs.length; k++) {
            if (coeffs[k] != 0) {
				count++;
			}
        }
        double[] resultCoeffs = new double[count];
        int[] resultExps = new int[count];
        int index = 0;
        for (int i = 0; i < coeffs.length; i++) {
            if (coeffs[i] != 0) {
                resultCoeffs[index] = coeffs[i];
                resultExps[index] = exps[i];
                index++;
            }
        }
        
        return new Polynomial(resultCoeffs, resultExps);
    }
	public Polynomial multiply(Polynomial other){
		double [] productCoeffs= new double[this.coefficients.length*other.coefficients.length];
		int [] productExps = new int[this.coefficients.length*other.coefficients.length];
		int index = 0;
		for (int i =0; i<this.coefficients.length; i++){
			for (int j = 0; j < other.coefficients.length; j++) {
                productCoeffs[index] = this.coefficients[i] * other.coefficients[j];
                productExps[index] = this.exponents[i] + other.exponents[j];
                index++;
            }
		}
		for (int i = 0; i < productExps.length; i++) {
            for (int j = i + 1; j < productExps.length; j++) {
                if (productExps[i] == productExps[j]) {
                    productCoeffs[i] += productCoeffs[j];
                    productCoeffs[j] = 0;
                }
            }
        }
		int nonZeroCount = 0;
        for (int k =0; k<productCoeffs.length; k++) {
            if (productCoeffs[k] != 0) {
				nonZeroCount++;
			}
        }
		double[] resultCoeffs = new double[nonZeroCount];
        int[] resultExps = new int[nonZeroCount];
        index = 0;
        for (int i = 0; i < productCoeffs.length; i++) {
            if (productCoeffs[i] != 0) {
                resultCoeffs[index] = productCoeffs[i];
                resultExps[index] = productExps[i];
                index++;
            }
        }
        
        return new Polynomial(resultCoeffs, resultExps);
    
	}
	public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }
	public boolean hasRoot(double x){
		return(evaluate(x)==0);
	}
}