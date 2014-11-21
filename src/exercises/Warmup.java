package exercises;

import java.util.Random;
import java.util.Scanner;

public class Warmup {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.print("N: ");
		Random rand = new Random(13579);
		int N = input.nextInt(); // N random numbers
		int inside = 0;
		int outside = 0;

		for (int i = N; i > 0; i--) {

			int X = rand.nextInt(1001) - 500; // 2R = 1000
			int Y = rand.nextInt(1001) - 500;
			// System.out.println(X + ", " + Y);
			if (X * X + Y * Y > 500 * 500) {
				outside++;
			} else {
				inside++;
			}

		}

		System.out.println("inside: " + inside);
		System.out.println("outside: " + outside);

		double pi = (4.0 * inside) / (inside + outside);
		System.out.println("pi = " + pi);

	}

}
