// ***** ***** *****
// TESTEZ SI UN NOMBRE EST PREMIER - TEST IF A NUMBER IS PRIME 

// *****
// Avec n_test > 11. Doit-être supérieur a 11 comme chiffre de départ
// With n_test > 11. Must be greater than 11 as the starting digit.

// *****
// Veuillez noter - Important :
// Cette class fonctionne bien pour les longs, les BigInteger raisonnables, voire avec les nombres de 19 à 20 chiffres.
// Elle devient difficile de 20 à 30 chiffres.
// IMPOSSIBLE POUR DESNOMBRES SUPPERIEUR à 30 CHIFFRES :
// Elle est impossible à utiliser pour des nombres comme 2¹³⁶²⁷⁹⁸⁴¹ − 1.

// *****
// Please note - Important:
// This class works well for long numbers, reasonable BigIntegers, and even numbers with 19 to 20 digits.
// It becomes difficult with 20 to 30 digits.
// IMPOSSIBLE FOR NUMBERS LARGER THAN 30 DIGITS:
// It is impossible to use for numbers like 2¹³⁶²⁷⁹⁸⁴¹ − 1.

// ***** ***** *****
// Ajout d’un filtre supplémentaire Filtre de forme pour test direct (n_test ± 1) / 6
// Utilisation : uniquement dans les tests de primalité d’un nombre.
// Principe : Tout nombre premier > 3 est de la forme 6k ± 1.
// Méthode, pour un entier n_test > 3, on vérifie :
// (n_test − 1) / 6
// (n_test + 1) / 6
// Si aucun des deux résultats n’est un entier, alors n_test ne peut pas être premier.
// Sinon, il est potentiellement premier (faux positif) et doit être vérifié par le crible dynamique (diviseurs ≤ √n).

// *****
// Addition of an additional filter: Shape filter for direct testing (n_test ± 1) / 6
// Use: only in primality tests for a number.
// Principle: Any prime number > 3 is of the form 6k ± 1.
// Method: For an integer n_test > 3, we check:
// (n_test − 1) / 6
// (n_test + 1) / 6
// If neither result is an integer, then n_test cannot be prime.
// Otherwise, it is potentially prime (false positive) and must be checked by the dynamic sieve (divisors ≤ √n).

// ***** ***** *****
// JAVA 8
// ***** ***** *****

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

		boolean isFalsePositive = false;

		// Adapter ces variables long ou BigInteger en fonction des grands nombres :
		// ≤ 9 223 372 036 854 775 807 (limite de long)
		// ≤ 2 147 483 647 (limite de Integer)
		// Adapt these long or BigInteger variables according to large numbers:
		// ≤ 9,223,372,036,854,775,807 (long limit)
		// ≤ 2 147 483 647 (Integer limit)
		
		// ATTENTION - WARNING
		// n_test > 11 - Doit-être supérieur a 11.
		// n_test > 11 - Must be greater than 11.
		
		// Cette class a été testée avec les nombres ci-dessous :
		// This class has been tested with the numbers below:
		//BigInteger n_test = BigInteger.valueOf(6857); //Nombre premier - Is prime number

		BigInteger n_test = BigInteger.valueOf(291167); //Nombre premier - Is prime number
		
		//BigInteger n_test = BigInteger.valueOf(2147483643); //Nombre non premier - Is not prime number
        //BigInteger n_test = BigInteger.valueOf(2147483647); //Nombre premier - Is prime number
        // Valeur maximum atteinte en Integer : ≤ 2 147 483 647
		// Maximum value reached in Integer: ≤ 2,147,483,647
        
		//BigInteger n_test = BigInteger.valueOf(2147483648L); //Nombre non premier - Is not prime number
        //BigInteger n_test = BigInteger.valueOf(18000000005693L); //Nombre premier - Is prime number
        //BigInteger n_test = BigInteger.valueOf(29996224275833L); //Nombre premier - Is prime number
        //BigInteger n_test = BigInteger.valueOf(9223372036854775807L); //Nombre non premier - Is not prime number
        // Valeur maximum atteinte en long : ≤ 9 223 372 036 854 775 807
		// Maximum value reached in long: ≤ 9 223 372 036 854 775 807
        
		//BigInteger n_test = new BigInteger("9223372036854775808"); //Nombre non premier - Is not prime number
        //BigInteger n_test = new BigInteger("9223372036854775809"); //Nombre non premier - Is not prime number
        //BigInteger n_test = new BigInteger("9223372036854775833"); //Nombre non premier - Is not prime number
        
		// Pour information (Intel Core i5-1135G7), le traitement de ce nombre peut prendre entre 2 à 10 secondes : 9223372036854775907 (Nombre premier).
		// Sur le site (https://www.jdoodle.com/online-java-compiler), le traitement peut prendre entre 90 à 120 secondes.
		// For your information (Intel Core i5-1135G7), processing this number can take between 2 and 10 seconds: 9223372036854775907 (Prime number).
		// On the website (https://www.jdoodle.com/online-java-compiler), processing can take between 90 and 120 seconds.
		//BigInteger n_test = new BigInteger("9223372036854775907"); //Nombre premier - Is prime number

		//if (n_test % 2 == 0) {
		if (n_test.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) { //Tous les nombres pairs finissant par 0, 2, 4, 6, 8 - All even numbers ending in 0, 2, 4, 6, 8
			isFalsePositive = true;
		//} else if (sumDigits(n_test) % 3 == 0) {
		} else if (BigInteger.valueOf(sumDigits(n_test)).mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) { //Tous les multiples de 3, 6, 9, somme des chiffres - All multiples of 3, 6, 9, sum of digits
			isFalsePositive = true;
		//} else if (n_test % 10 == 5) {
		} else if (n_test.mod(BigInteger.TEN).equals(BigInteger.valueOf(5))) { //Tous les nombres finissant par 5 - All numbers ending in 5
			isFalsePositive = true;
		} else if (isMultipleOf7(n_test)) { //Tous les multiples de 7 (×2, méthode soustractive) - All multiples of 7 (×2, subtractive method)
			isFalsePositive = true;
		} else if (isMultipleOf11(n_test)) { //Tous les multiples de 11 (somme alternée des chiffres et des signes de - et +) - All multiples of 11 (alternating sum of digits from - and + signs)
			isFalsePositive = true;
		//} else if (((n_test - 1) % 6 != 0) && ((n_test + 1) % 6 != 0)){ //Filtre de forme pour test direct (n±1)/6 - Shape filter for direct test (n±1)/6
		} else if (!n_test.subtract(BigInteger.ONE).mod(BigInteger.valueOf(6)).equals(BigInteger.ZERO) && !n_test.add(BigInteger.ONE).mod(BigInteger.valueOf(6)).equals(BigInteger.ZERO)) {
			isFalsePositive = true;
		} else if (isCompositeByCrible(n_test)) { //Tous les autres multiples ≥13, Crible dynamique avec diviseurs ≤ √n - All other multiples ≥13, Dynamic sieve with divisors ≤ √n
			//Pour information : L’approche dynamique (crible avec diviseurs ≤ √n) est utilisée que pour les nombres premiers ≥ 13, pour éviter des calculs inutiles (les filtres précédents sont des raccourcis pour accélérer le calcul et d’optimisation)
			//For information: The dynamic approach (sieve with divisors ≤ √n) is only used for prime numbers ≥ 13, to avoid unnecessary calculations (the previous filters are shortcuts to speed up the calculation and optimization)
			isFalsePositive = true;
		} else {
		}

		if (!isFalsePositive) {// Exécuter si la condition est fausse - To be executed if the condition is false
			System.out.println("True, " + n_test + " : Prime number"); //Vrai : Nombre premier
		} else {
			System.out.println("False, " + n_test + " : Is not prime number"); //Faux : N’est pas un nombre premier
		}

    }
		
	// Somme des chiffres - Sum of digits :
	public static long sumDigits(long n) {
		long sum = 0;
		while (n != 0) {
			sum += n % 10;
			n /= 10;
		}
		return sum;
	}

	// Méthode soustractive - Subtractive method :
	public static boolean isMultipleOf7(long n) {
		while (n > 70) {
			long lastDigit = n % 10;
			long rest = n / 10;
			n = rest - 2 * lastDigit;
		}
		return n % 7 == 0;
	}

	// Somme alternée des chiffres (de - et +) - Alternating sum of digits (from - and + signs):
	public static boolean isMultipleOf11(long n) {
		boolean add = true;
		int altSum = 0;

		while (n > 0) {
			int digit = (int)(n % 10);
			altSum += add ? digit : -digit;
			add = !add;
			n /= 10;
		}

		return altSum % 11 == 0;
	}

	// Test diviseurs ≤ √n - Test divisors ≤ √n:
	public static boolean isCompositeByCrible(long n) {
		if (n < 2) return true;
		long sqrt = (long)Math.sqrt(n);

		for (long i = 13; i <= sqrt; i += 2) {
			if (n % i == 0) return true;
		}

		return false;
	}
	
	// ***** BigInteger

	// Somme des chiffres - Sum of digits :
	public static long sumDigits(BigInteger n) {
		long sum = 0;
		String digits = n.toString();
		for (char c : digits.toCharArray()) {
			sum += Character.getNumericValue(c);
		}
		return sum;
	}

	// Méthode soustractive - Subtractive method :
	public static boolean isMultipleOf7(BigInteger n) {
		BigInteger seven = BigInteger.valueOf(7);
		BigInteger ten = BigInteger.TEN;
		BigInteger multiplier = BigInteger.valueOf(2);

		while (n.compareTo(seven) > 0) {
			BigInteger lastDigit = n.mod(ten);
			BigInteger rest = n.divide(ten);
			n = rest.subtract(lastDigit.multiply(multiplier));
		}

		return n.mod(seven).equals(BigInteger.ZERO);
	}

	// Somme alternée des chiffres (de - et +) - Alternating sum of digits (from - and + signs):
	public static boolean isMultipleOf11(BigInteger n) {
		String digits = n.toString();
		int altSum = 0;

		for (int i = 0; i < digits.length(); i++) {
			int digit = Character.getNumericValue(digits.charAt(i));
			altSum += (i % 2 == 0) ? digit : -digit;
		}

		return altSum % 11 == 0;
	}

	// Test diviseurs ≤ √n - Test divisors ≤ √n :
	public static boolean isCompositeByCrible(BigInteger n) {
		BigInteger two = BigInteger.valueOf(2);
		if (n.compareTo(two) < 0) return true;

		BigInteger i = BigInteger.valueOf(13);
		BigInteger sqrtN = bigIntSqrt(n);
		
		while (i.compareTo(sqrtN) <= 0) {
			if (n.mod(i).equals(BigInteger.ZERO)) return true;
			i = i.add(two); // test : 13, 15, 17, ...
		}

		return false;
	}

	// Méthode de calcul de la racine carrée entière de BigInteger :
	// Method for calculating the integer square root of BigInteger:
	public static BigInteger bigIntSqrt(BigInteger n) {
		BigInteger r = BigInteger.ZERO;
		BigInteger bit = BigInteger.ONE.shiftLeft(n.bitLength() / 2);

		while (bit.compareTo(BigInteger.ZERO) > 0) {
			BigInteger temp = r.add(bit);
			if (temp.multiply(temp).compareTo(n) <= 0) {
				r = temp;
			}
			bit = bit.shiftRight(1);
		}
		return r;
	}

}