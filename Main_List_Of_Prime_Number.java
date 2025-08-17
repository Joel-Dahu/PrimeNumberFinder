// ***** ***** *****
// ***** GENERER UNE LISTE DE NOMBRES PREMIERS (entre un début et une fin donnée) ET ELIMINATION DES FAUX POSITIFS *****
// *****
// ***** GENERATE A LIST OF PRIME NUMBERS (between a given start and end point) AND ELIMINATE FALSE POSITIVES *****

// *****
// En utilisant la forme optimisée 6n ± 1, les filtres, et le crible dynamique.
// Elimination des faux positifs.

// - Boucle sur n et génère uniquement des nombres n_test = 6n-1 et 6n+1
// - Applique tous les filtres (divisible par 2, 3, 5, 7, 11)
// - Puis crible dynamique jusqu’à √n
// - Affiche tous les nombres premiers dans l’intervalle [n à n_stop]

// En exécutant ce code (n = 3; à n_stop = 17;), nous obtenons le résultat suivant :
//17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103
// Elimination des faux positifs : 35, 49, 55, 65, 77, 85, 91, 95

// En exécutant ce code (n = 312345678901200L; à n_stop = 312345678901301L;), nous obtenons le résultat suivant :
//1874074073407219, 1874074073407229, 1874074073407271, 1874074073407279, 1874074073407441, 
//1874074073407483, 1874074073407517, 1874074073407537, 1874074073407541, 1874074073407567, 
//1874074073407579, 1874074073407583, 1874074073407639, 1874074073407751, 1874074073407771, 
//1874074073407787, 

// *****
// Using the optimized 6n ± 1 form, filters, and dynamic sieve.
// Eliminate false positives.

// - Loop through n and generate only numbers n_test = 6n-1 and 6n+1
// - Apply all filters (divisible by 2, 3, 5, 7, 11)
// - Then dynamically sift up to √n
// - Display all prime numbers in the interval [n to n_stop]

// Running this code (n = 3; at n_stop = 17), we get the following result:
// 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103
// Eliminate false positives: 35, 49, 55, 65, 77, 85, 91, 95

// Running this code (n = 312345678901200L; at n_stop = 312345678901301L;), we get the following result:
//1874074073407219, 1874074073407229, 1874074073407271, 1874074073407279, 1874074073407441,
//1874074073407483, 1874074073407517, 1874074073407537, 1874074073407541, 1874074073407567,
//1874074073407579, 1874074073407583, 1874074073407639, 1874074073407751, 1874074073407771,
//1874074073407787,

// ***** ***** *****
// JAVA 8
// ***** ***** *****

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

		boolean isFalsePositive = false;
		
		// Adapter ces variables long ou BigInteger en fonction des grands nombres : ≤ 9 223 372 036 854 775 807 (limite de long)
		// Adapt these long or BigInteger variables according to large numbers: ≤ 9,223,372,036,854,775,807 (long limit)
		long n_test = 0L;
		
		// Attention, si vous commencez à n = 1, il faut modifier la condition : if (n_test % 10 == 5) { par : if ((n_test % 10 == 5) && n_test != 5) a cause du chiffre 5 qui est nombre premier
		// Et cette condition : if (isMultipleOf7(n_test)) { par : if (isMultipleOf7(n_test) && n_test != 7) { a cause du chiffre 7 qui est nombre premier
		
		// Be careful, if you start at n = 1, you must modify the condition: if (n_test % 10 == 5) { by: if ((n_test % 10 == 5) && n_test != 5) because of the number 5 which is prime number
		// And this condition: if (isMultipleOf7(n_test)) { by: if (isMultipleOf7(n_test) && n_test != 7) { because of the number 7 which is prime number
		
		// Attention, si vous commencez à n = 2, il faut modifier la condition : if (isMultipleOf11(n_test)) { par : if (isMultipleOf11(n_test) && n_test != 11) { a cause du chiffre 11 qui est nombre premier
		// Be careful, if you start at n = 2, you must modify the condition: if (isMultipleOf11(n_test)) { by: if (isMultipleOf11(n_test) && n_test != 11) { because of the number 11 which is prime number
		// Dans une version avec BigInteger : !n_test.equals(BigInteger.valueOf(11))
		// In a version with BigInteger: !n_test.equals(BigInteger.valueOf(11))
		
		// Exemple avec - Example with : n = 3; à/to n_stop = 17;
        long n = 3; // >2
		long n_stop = 103;
        // Résultat - Result : 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, etc ...

        // Exemple avec - Example with : n = 312345678901200L; à n_stop = 312345678901301L;
        //long n = 312345678901200L; // >2
		//long n_stop = 312345678901301L;
        // Résultat - Result : 1874074073407219, 1874074073407229, 1874074073407271, 1874074073407279, 1874074073407441, 
        //1874074073407483, 1874074073407517, 1874074073407537, 1874074073407541, 1874074073407567, 
        //1874074073407579, 1874074073407583, 1874074073407639, 1874074073407751, 1874074073407771, 
        //1874074073407787, 

		do {
			n_test = n * 6 - 1; //Vérification - To check : n_test
			if (n_test % 2 == 0) { //Tous les nombres pairs finissant par 0, 2, 4, 6, 8 - All even numbers ending in 0, 2, 4, 6, 8
				isFalsePositive = true;
			} else if (sumDigits(n_test) % 3 == 0) { //Tous les multiples de 3, 6, 9, somme des chiffres - All multiples of 3, 6, 9, sum of digits
				isFalsePositive = true;
			} else if (n_test % 10 == 5) { //Tous les nombres finissant par 5 - All numbers ending in 5
				isFalsePositive = true;
			} else if (isMultipleOf7(n_test)) { //Tous les multiples de 7 (×2, méthode soustractive) - All multiples of 7 (×2, subtractive method)
				isFalsePositive = true;
			} else if (isMultipleOf11(n_test)) { //Tous les multiples de 11 (somme alternée des chiffres et des signes de - et +) - All multiples of 11 (alternating sum of digits from - and + signs)
				isFalsePositive = true;
			} else if (isCompositeByCrible(n_test)) { //Tous les autres multiples ≥13, Crible dynamique avec diviseurs ≤ √n - All other multiples ≥13, Dynamic sieve with divisors ≤ √n
				//Pour information : L’approche dynamique (crible avec diviseurs ≤ √n) est utilisée que pour les nombres premiers ≥ 13, pour éviter des calculs inutiles (les filtres précédents sont des raccourcis pour accélérer le calcul et d’optimisation)
				//For information: The dynamic approach (sieve with divisors ≤ √n) is only used for prime numbers ≥ 13, to avoid unnecessary calculations (the previous filters are shortcuts to speed up the calculation and optimization)
				isFalsePositive = true;
			} else {
			}

			if (!isFalsePositive) { //Exécuter si la condition est fausse - To be executed if the condition is false
				System.out.println(n_test + ", "); //Note : n_test sera sur la liste - n_test will be on the list
			} else {
				isFalsePositive=false;
			}

			n_test = n * 6 + 1; //Vérification - To check : n_test
			if (n_test % 2 == 0) {
				isFalsePositive = true;
			} else if (sumDigits(n_test) % 3 == 0) {
				isFalsePositive = true;
			} else if (n_test % 10 == 5) {
				isFalsePositive = true;			
			} else if (isMultipleOf7(n_test)) {
				isFalsePositive = true;
			} else if (isMultipleOf11(n_test)) {
				isFalsePositive = true;
			} else if (isCompositeByCrible(n_test)) {
				isFalsePositive = true;
			} else {
			}

			if (!isFalsePositive) {
				System.out.println(n_test + ", ");
			} else {
				isFalsePositive=false;
			}

			n++;
		} while (n < n_stop+1);
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