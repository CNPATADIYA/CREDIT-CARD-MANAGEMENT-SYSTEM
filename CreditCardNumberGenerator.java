import java.util.Random;
import java.util.*;

/**
 * A credit card number generator.
 *
 * @author Josef Galea
 */
public class CreditCardNumberGenerator {

    private Random random = new Random(System.currentTimeMillis());
    public String generate(String bin, int length) {

        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        // Do the Luhn algorithm to generate the check digit.
        int checkDigit = this.getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
    }
    private int getCheckDigit(String number) {

        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        // The check digit is the number required to make the sum a multiple of
        // 10.
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }
	private int random(int max , int min){
		 double x = (Math.random()*((max-min)+1))+min;
		 return (int)x;
	}
	
	public static void main(String args[]){
		String bin;
		CreditCardNumberGenerator o=new CreditCardNumberGenerator();
		System.out.println("Enter Bin:");
		Scanner sc = new Scanner(System.in);
		bin=sc.nextLine();
		bin=o.generate(bin,16);
		System.out.println(bin);
		System.out.println(o.random(12,1)+" | "+o.random(2019,2030)+" | "+o.random(100,999));
		if(o.getCheckDigit(bin)==0)
			System.out.println("valid!!!1");
		else
			System.out.println(" NOT    !!!valid!!!1");
	}
}
