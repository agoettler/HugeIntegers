
/*
 * Introduction to Software Design Fall 2014
 * Author: Andrew Goettler
 * Problem: 8.16 Huge Integer Class (pg. 357)
 * Problem description: Create a Huge Integer class capable of storing
 * 		integers as large as 40 digits. Provide methods for parsing
 * 		integers from strings, printing integers as strings, adding 
 * 		and subtracting integers, and comparing integers.
 */

public class HugeIntegerTest
{

	public static void main(String[] args)
	{
		String nines = "";
		String ones = "";
		HugeInteger allNines;
		HugeInteger allOnes;
		HugeInteger allZeroes = new HugeInteger();
		HugeInteger testInt = new HugeInteger();
		testInt.parse("1234567890");
		
		for(int i = 0; i < HugeInteger.MAX_INT_SIZE; i++)
		{
			nines += "9";
			ones += "1";
		}
		
		allNines = new HugeInteger(nines); // create a HugeInteger of all 9's
		allOnes = new HugeInteger(ones); // create a HugeInteger of all 0's
		
		// demonstrate the toString method
		System.out.println("allNines: " + allNines.toString());
		System.out.println("allOnes: " + allOnes.toString());
		
		// demonstrate the toShortString method
		System.out.println("testInt: " + testInt.toString());
		System.out.println("testInt, short: " + testInt.toShortString());
		
		// demonstrate subtraction
		allNines.subtract(allOnes);
		System.out.println("allNines, after subtraction: "+ allNines.toString());
		
		//demonstrate addition
		allNines.add(allOnes);
		System.out.println("allNines, after addition: "+ allNines.toString());
		// demonstrate the overflow behavior
		allNines.add(allOnes);
		System.out.println("allNines, after overflow: "+ allNines.toString());
		
		// demonstrate the isZero method
		System.out.println("allZeroes: " + allZeroes.toString());
		System.out.println("allZeroes, short: " + allZeroes.toShortString());
		System.out.println("allZeroes is zero: "+ allZeroes.isZero());
		
		// try to create a HugeInteger with a letter in it
		HugeInteger wrongInt = new HugeInteger("1234567890a");
		System.out.println("value of wrongInt: " + wrongInt.toString());
		
		// demonstrate comparison 
		HugeInteger bigInt = new HugeInteger("10000");
		HugeInteger smallInt = new HugeInteger("100");
		
		System.out.println("bigInt > smallInt? " + bigInt.isGreaterThan(smallInt));
		System.out.println("bigInt >= smallInt? " + bigInt.isGreaterThanOrEqualTo(smallInt));
		
		System.out.println("big Int < smallInt? " + bigInt.isLessThan(smallInt));
		System.out.println("big Int <= smallInt? " + bigInt.isLessThanOrEqualTo(smallInt));
		
		HugeInteger anotherBigInt = new HugeInteger(bigInt.toString());
		System.out.println("bigInt == anotherBigInt? " + bigInt.isEqualTo(anotherBigInt));
		System.out.println("bigInt != testInt? " + bigInt.isNotEqualTo(testInt));
		
		// demonstrate subtraction with a negative result
		smallInt.subtract(bigInt);
		System.out.println("smallInt, after subtraction attempt: " + smallInt.toString());
		
	}
}
