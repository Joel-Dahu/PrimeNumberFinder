**GENERATE A LIST OF PRIME NUMBERS AND ELIMINATE FALSE POSITIVES**
&nbsp;<br>  
This method introduces nothing new; it relies on well-known principles (notably the 6n - 1 and 6n + 1 forms), combined with a filtering system to eliminate false positives. It is structured with a focus on calculation optimization.

**Objective:**
Generate a list of prime numbers starting from any number greater than 2, while filtering out false positives. This is a structured, simple, and optimized method that identifies prime numbers with 100% reliability—provided all steps are rigorously followed.

**Method used: Form 6n ± 1** 
- No prime number will be missed, as all prime numbers greater than 3 must be of the form 6n ± 1.  
- This method therefore includes all potential candidates, although some will be false positives to be filtered out.

&nbsp;<br>
**I - FILTERS USED (order to respect):**<br>  
Based on progressive filtering of all integers to exclude false positives, aiming to optimize operations (time saving).

**1. ✅ Elimination of all even numbers (except 2):**<br>  
All numbers ending with 0, 2, 4, 6, 8 are even, thus not prime, except 2 itself. This concerns all multiples of 2: 4, 6, 8, 10, 12, 14, etc.<br>  
Note: Only odd numbers are kept, i.e., those ending with 1, 3, 5, 7, 9.

**2. ✅ Elimination of multiples of 3 (except 3):**<br>  
Any number whose sum of digits is a multiple of 3 (e.g., 3, 6, 9, 21, etc.) is itself divisible by 3, thus not prime.

Example: 6921<br>  
6 + 9 + 2 + 1 = 18 → 1 + 8 = 9 → divisible by 3, therefore not prime ❌<br>  
Note: This filter applies regardless of the number of digits.

**3. ✅ Elimination of multiples of 5 (except 5):**<br>  
All numbers ending with 5 or 0, starting from 10, are multiples of 5 and therefore not prime, except 5 itself.<br>  
Note: Remaining odd numbers ending with 1, 3, 7, 9 are kept, except 5.

**4. ✅ Elimination of multiples of 7 (except 7 itself):**<br>  
Subtractive method by 2, steps:  
1. Take the last digit of the number (units digit).  
2. Multiply it by 2.
3. Subtract this result from the rest of the number (without the last digit).  
4. Repeat the steps with the new number obtained.  
5. If the final result is a multiple of 7 (0, 7, 14, 21, etc. or their negative opposites: -7, -14...), then the original number is divisible by 7.

Example: 69097<br>  
→ Last digit = 7 → 7 × 2 = 14  
→ 6909 - 14 = 6895  
→ Last digit = 5 → 5 × 2 = 10  
→ 689 - 10 = 679  
→ Last digit = 9 → 9 × 2 = 18  
→ 67 - 18 = 49 ✅ is a multiple of 7 (7 × 7), so 69097 is divisible by 7 ✔️

Example: 49<br>  
→ Last digit = 9 → 9 × 2 = 18  
→ 4 - 18 = -14 → also divisible by 7 ✔️

Another example: 217<br>  
→ Last digit = 7 → 7 × 2 = 14<br>  
→ 21 - 14 = 7 → also divisible by 7 ✔️

**5. ✅ Elimination of multiples of 11 (except 11 itself):**<br>  
Method: alternating sum of digits (signs - and +). A number is divisible by 11 if the alternating sum of its digits, applying - and + alternately, results in 0 or a multiple of 11 (positive or negative). Always start with the negative sign from left to right. This rule quickly identifies whether a number is divisible by 11 without performing division.

Example: 209<br>  
→ 2 - 0 + 9 = 11 → ✔️ divisible by 11

Example: 1694<br>  
→ 1 - 6 + 9 - 4 = 0 → ✔️ divisible by 11

Example: 49709<br>  
→ 4 - 9 + 7 - 0 + 9 = 11 → ✔️ divisible by 11
  
**6. ✅ Dynamic sieve with divisors ≤ √n (≥ 13):**<br>  
After the previous filters (2, 3, 5, 7, 11), a dynamic sieve is applied by testing the divisibility of the number by small prime numbers ≥ 13, up to √n.  
This is neither a Sieve of Eratosthenes nor a probabilistic test, but an optimized, simple, and reliable trial division method for moderately sized numbers.

Objectives:  
- ✅ Eliminate all remaining false positives.  
- ✅ Miss no prime numbers.

Efficiency:  
- Fast and effective for integers up to 10⁶ to 10⁷, and even up to ~10¹⁰ with the previous filters.  
- For testing a single number, it can work up to 10¹², or even 10¹⁴ in some cases.  
- Beyond 10¹², performance drops sharply because √n becomes too large → the number of divisions explodes.

&nbsp;<br>
**II - PERFORMANCE ESTIMATION (1M to 10M) - Context:**<br>  
In JAVA code: The long type can store integers from -9,223,372,036,854,775,808 to +9,223,372,036,854,775,807 (about ±9.2 × 10¹⁸). The dynamic sieve works with long up to 9 × 10¹⁸, but computation time increases sharply beyond 10⁹ to 10¹².

Generating all prime numbers from 1,000,000 to 10,000,000 (i.e. 9 million numbers to test) with filters + dynamic sieve on a standard computer (Intel i5, 4 to 8 GB RAM). Steps:  
- Fast filters (even, %3, %5, %7, %11) → Eliminate about 90% of numbers very quickly.  
- Dynamic sieve (division ≤ √n) → Applied only to the remaining ~10%, i.e. ~900,000 cases.

Estimated time per complete test (filters + divisions):  
- Around 0.1 ms to 5 ms depending on n size and code optimization.  
- 900,000 × 1 ms (average) = 900,000 ms = 900 sec ≈ 15 minutes  
- Realistic range: 10 to 15 minutes on an i5 to scan the entire range with this method.

JAVA Optimization Tip with Multi-threading (Multi-core CPU), for an 8-core processor:  
- Split the range [1,000,000 → 10,000,000] into 8 equal sub-intervals.  
- Launch 8 parallel threads, each tasked with testing prime numbers in its portion.  
- Expected result: Up to 8× faster ideally. In practice, around ≈ 6 to 7× speedup depending on thread and resource management.

Best practices:  
- Use thread-safe structures to collect results (e.g., ConcurrentLinkedQueue, CopyOnWriteArrayList).  
- Merge results from the 8 threads at the end to get the final prime numbers list.  
- Properly manage synchronization and avoid deadlocks or CPU overload.

&nbsp;<br>
**III - REMARK ON UNUSED FILTERS (e.g., 13, 17, 19, …):**<br>  
These filters are not applied because they do not provide a significant performance gain.  
- Their rules are more complex or less intuitive.  
- Their detection frequency (multiples encountered) is relatively lower.  
- They are naturally filtered by the dynamic sieve via divisions up to √n.

Therefore, applying them upfront is unnecessary, as their addition:  
- Does not sufficiently reduce the computation load,  
- And adds unnecessary processing overhead.

➤ **Filter for multiples of 13 (×4 + remainder) - Method:** 
1. Take the last digit of the number.  
2. Multiply it by 4.  
3. Add it to the rest of the number (without the last digit).  
4. Repeat until obtaining an obvious multiple of 13 (13, 26, 39, …).

Examples:<br>  
With 143: 3×4 = 12 → 14 + 12 = 26 ⇒ divisible by 13 ✔️<br>

With 2197: 7×4 = 28 → 219 + 28 = 247<br>  
→ 7×4 = 28 → 24 + 28 = 52 ⇒ (52 = 26 × 2 or 13 × 4), so 2197 is divisible by 13 ✔️<br>  
→ 2×4 = 8 → 5 + 8 = 13 ⇒ (13 = 13 × 1), so 2197 is divisible by 13 ✔️

With 7007: 7×4 = 28 → 700 + 28 = 728<br>  
→ 8×4 = 32 → 72 + 32 = 104<br>  
→ 4×4 = 16 → 10 + 16 = 26 ⇒ (26 = 13 × 2), so 7007 is divisible by 13 ✔️

➤ **Multiple of 17 (×5, subtractive method) - Principle:**
1. Take the last digit of the number.  
2. Multiply it by 5.  
3. Subtract this result from the rest of the number (without the last digit).  
4. Repeat the process with the new number obtained.

Two success cases:  
- If the final result is 0 (method 1), the number is divisible by 17. ✔️  
- If the result reaches ±17, ±34, ±51, … (method 2), the number is also divisible by 17. ✔️

Method 1 – With 21063:<br>  
→ 3×5 = 15 → 2106 − 15 = 2091  
→ 1×5 = 5 → 209 − 5 = 204  
→ 4×5 = 20 → 20 − 20 = 0 ⇒ divisible by 17 ✔️

Method 2 – With 21199:<br>  
→ 9×5 = 45 → 2119 − 45 = 2074  
→ 4×5 = 20 → 207 − 20 = 187  
→ 7×5 = 35 → 18 − 35 = −17 ⇒ Remainder ≠ 0, but -17 (17 x -1) is divisible by 17 ✔️

Conclusion:  
- Final result = 0 → divisible by 17 ✔️  
- Final result = ±17, ±34, … → divisible by 17 ✔️

➤ **Multiple of 19 (×2, additive method) - Principle:**
1. Take the last digit of the number.  
2. Multiply it by 2.  
3. Add this result to the rest of the number (without the last digit).  
4. Repeat until you get a number easy to test.  
If the result is a multiple of 19 (19, 38, 57, …), then the original number is divisible by 19.

Example: 133  
→ 3 × 2 = 6 → 13 + 6 = 19 ⇒ divisible by 19 ✔️

Example: 361  
→ 1 × 2 = 2 → 36 + 2 = 38 ⇒ divisible by 19 ✔️  
→ 8 × 2 = 16 → 3 + 16 = 19 ⇒ divisible by 19 ✔️

Example: 110523  
→ 3 × 2 = 6 → 11052 + 6 = 11058  
→ 8 × 2 = 16 → 1105 + 16 = 1121  
→ 1 × 2 = 2 → 112 + 2 = 114  
→ 4 × 2 = 8 → 11 + 8 = 19 ⇒ divisible by 19 ✔️

- If the result = 19, 38, 57, … ⇒ divisible ✔️  
- This method is fast and reliable for manual or algorithmic checks.

&nbsp;<br>
**IV - TEST IF A NUMBER IS PRIME:**<br>  
Please note a specific feature in the file (***Main_Prime_Number_Test.java***) regarding prime number testing.  
To avoid unnecessary calculations on numbers that cannot mathematically be prime—and for better performance when using direct tests like (n±1)/6—this filter has been added.

✅ **Form Filter for Direct Testing (n±1)/6:**<br>  
Usage: Only in individual prime number tests.  
Principle: Every prime number > 3 follows the form 6k ± 1.<br>
Method: For an integer n > 3, check:<br>
→ (n − 1)/6<br>
→ (n + 1)/6

If neither of the two results is an integer, then n cannot be a prime number.  
Otherwise, it is potentially prime and must be verified using the dynamic sieve (divisors ≤ √n).

Example: n = 291167  
- (291167 − 1)/6 = 291166/6 = 48527.666 ❌  
- (291167 + 1)/6 = 291168/6 = 48528 ✅ → The test is validated ⇒ n may be prime or a false positive.

If n passes this filter, it is either a potential prime or a false positive (e.g., 119 or 133).  
Therefore, the dynamic sieve (divisors ≤ √n) must be applied to confirm or reject its primality.

&nbsp;<br>
**V - IMPLEMENTATION AND USAGE – See files below:**<br>
- **GENERATE A LIST OF PRIME NUMBERS** (between a given start and end value) **AND ELIMINATE FALSE POSITIVES:** ***Main_List_Of_Prime_Number.java***

- **TEST IF A NUMBER IS PRIME:** ***Main_Prime_Number_Test.java***<br>
For reference, using an Intel Core i5-1135G7, processing the following number may take between 2 to 10 seconds: 9223372036854775907 (Prime number).

&nbsp;<br>
**VI - INFORMATION – MATHEMATICS / COMPUTER SCIENCE:**<br>
For large-scale or very large tests (cryptography), BigInteger is used:
- Can handle integers of arbitrary size  
- But becomes very slow with BigInteger.sqrt() and repeated divisions

For very large numbers (≥ 10¹²), it's recommended to use:
- Miller-Rabin (a fast and reliable probabilistic test for most applications)  
- In BigInteger, or algorithms like AKS, Baillie–PSW, etc. (more complex, but deterministic).  
For this kind of numbers, specialized algorithms and very powerful infrastructure are required (such as GIMPS + NVIDIA H100 GPU).

Time Limit and Complexity:  
To process a number with **41,024,320 decimal digits (M₁₃₆₂₇₉₈₄₁ = 2¹³⁶²⁷⁹⁸⁴¹ − 1)**, the largest known to date and part of the **Mersenne primes family**, of the form **Mₚ = 2ᵖ − 1**, discovered by **Luke Durant on October 21, 2024, via the GIMPS project** (Great Internet Mersenne Prime Search) with the help of a NVIDIA H100 GPU.  
A true mathematical and computational achievement — to give perspective:  
Printing this number in standard font on A4 paper would require over **10,000 pages**!

Testing such a number (over 40 million digits) would require checking all possible divisors up to its square root — approximately **10²⁰⁶⁰¹⁵⁹ tests**.  
That is unimaginably slow — even for the fastest supercomputers.

Memory & Storage:  
Storing such a number exceeds the standard capacity of a regular PC. Every operation using BigInteger becomes extremely heavy in terms of processing and memory.

Advanced Primality Testing Required:
- For such large numbers, **probabilistic tests like Miller–Rabin**, or **specialized algorithms like Lucas–Lehmer** (specific to Mersenne primes) are used.
- These algorithms are **much faster** in extreme cases, **but don’t always guarantee primality with 100% certainty**, except under certain mathematical conditions.
- **Euclidean division is a fundamental arithmetic tool**, but it is not a primality test.

Note – What This Is Not (Though It May Seem Similar):
- **Sieve of Eratosthenes:** Generates all primes up to a given number n by eliminating multiples — classic but not suitable for very large ranges.
- **Sieve of Atkin:** A more modern, faster sieve than Eratosthenes for very large numbers.
- **Quadratic / Algebraic Sieve:** Advanced **factorization algorithms**, not basic primality tests.
- **Sieve of Sundaram:** Another method for generating small prime numbers.

 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

<table>
  <tr>
    <td align="center">
      <b>LIST OF PRIME NUMBERS</b>
    </td>
    <td align="center">
      <b>IS THIS NUMBER PRIME?</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="Schema_Logique_Prime_Numbers_List.jpg" width="512"/><br>
    </td>
    <td align="center">
      <img src="Schema_Logique_Test_Number.jpg" width="512"/><br>
    </td>
  </tr>
</table>
