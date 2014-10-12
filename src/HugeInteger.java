
/*
 * Introduction to Software Design Fall 2014
 * Author: Andrew Goettler
 * Problem: 8.16 Huge Integer Class (pg. 357)
 * Problem description: Create a Huge Integer class capable of storing
 * 		integers as large as 40 digits. Provide methods for parsing
 * 		integers from strings, printing integers as strings, adding 
 * 		and subtracting integers, and comparing integers.
 */

public class HugeInteger
{
	
	private int digitArray[]; // integer array for storing the digits of the huge integer
	final static int MAX_INT_SIZE = 40; // constant for the maximum number of digits
	
	/**
	 * No-argument constructor creates a new HugeInteger object initialize to zero.
	 */
	// default no-argument constructor
	public HugeInteger()
	{
		// digitArray will be automatically initialized to contain only 0's
		 digitArray = new int[MAX_INT_SIZE];
	}
	
	/**
	 * This single-argument constructor creates a new HugeInteger object and
	 * initializes it to the specified value.
	 * 
	 * @param value a String representing a huge integer
	 */
	// single-argument constructor initializes HugeInteger to the specified value
	public HugeInteger(String value)
	{
		this();
		this.parse(value);
	}
	
	/**
	 * This method returns a digit from the the HugeInteger. Digits are labeled
	 * from 1 to MAX_INT_SIZE, with digit 1 being the least-significant digit.
	 * 
	 * @param digit the digit to be returned
	 * @return the value of the specified digit
	 */
	/* instance method for returning a particular digit from the array
	 * digits are one-indexed from least-significant to most-significant
	 * i.e., the "1's place" is digit 1, the "10's place" is digit 2
	 */
	public int getDigit(int digit)
	{
		return this.getIndex(MAX_INT_SIZE - digit);
	}
	
	/**
	 * This method returns the digit contained in a specific index of the digitArray.
	 * The digits are indexed from 0 to MAX_INT_SIZE-1, 
	 * with 0 being the most-significant digit.
	 * 
	 * @param index the array index to be returned
	 * @return the value of digit at the specified array index
	 */
	/* instance method for returning the digit from a particular index of the array
	 * accesses the array directly, so it is zero-indexed 
	 * from most-significant to least-significant
	 */
	public int getIndex(int index)
	{
		if( (index < MAX_INT_SIZE) && (index >= 0))
		{
			return this.digitArray[index];
		}
		
		else
		{
			// Alert the user that the index is out of bounds
			System.out.println("getIndex failed: index out of bounds");

			return -1;
		}
	}
	
	/**
	 * This method sets the value of a specified digit. This method is private
	 * as allowing external actors to directly modify individual digits is
	 * highly undesirable. Digits are labeled from 1 to MAX_INT_SIZE, 
	 * with digit 1 being the least-significant digit.
	 * 
	 * @param digit the digit to be modified
	 * @param value the value digit should be set to
	 */
	/*
	 * internal instance method to set a particular digit in the array 
	 * 		to the specified value
	 * digits are one-indexed from least-significant to most-significant
	 * i.e., the "1's place" is digit 1, the "10's place" is digit 2
	 */
	private void setDigit(int digit, int value)
	{
		this.setIndex((MAX_INT_SIZE-digit), value);
	}
	
	/**
	 * This method modifies the value of the digit at the specified index.
	 * This is method is private as allowing external actors to directly
	 * modify the digitArray is highly undesirable. The digits are indexed
	 * from 0 to MAX_INT_SIZE-1, with 0 being the most-significant digit.
	 * 
	 * @param index the array index to be modified
	 * @param value the value the digit at the index is to set to 
	 */
	/*
	 * internal instance method to set the value of a digit at a particular
	 * 		index in the array
	 * accesses the array directly, so it is zero-indexed
	 * from most-significant to least-significant
	 */
	private void setIndex(int index, int value)
	{
		if( (index < MAX_INT_SIZE) && (index >= 0))
		{
			if( (value < 10) && (value >= 0))
			{
				this.digitArray[index] = value;
			}
			
			else
			{
				// Alert the user if an incorrect value was specified
				System.out.println("setIndex failed: value should be an integer in the range 0-9.");
			}
		}
		
		else
		{
			// Alert the user if the index would have been out of bounds
			System.out.println("setIndex failed: index out of bounds.");
		}
	}
	
	/**
	 * This method converts a string representation of a huge integer into
	 * the HugeInteger format.
	 * 
	 * @param value a String representing a huge integer
	 */
	// instance method for parsing a HugeInteger value from a string
	public void parse(String value)
	{
		if( (value.length() <= MAX_INT_SIZE) && (value.length() > 0))
		{	
			for(int counter = 1; counter <= value.length(); counter++)
			{
				// charDigit starts at the highest index of the string and decreases
				int charDigit = value.length() - counter;
				
				// check that each character is a numeral
				if( Character.isDigit( value.charAt( charDigit ) ) )
				{
					/*
					 * iterate from the rightmost end of both the array and the string,
					 * as though reading the number numerically
					 */
					this.setDigit(counter, 
						Character.getNumericValue(value.charAt(charDigit)));
				}
				
				else
				{
					// Alert the user if the string contained non-numeric characters
					System.out.println("parse failed: value must consist of numeric characters only.");
				}
			}
		}
		
		else
		{
			// Alert the user if the string was too large or too small
			System.out.printf("parse failed: value must contain %d or fewer characters", MAX_INT_SIZE);
		}
	}
	
	/**
	 * This mutator method adds the value of another HugeInteger to the calling
	 * HugeInteger object. If the addition results in an overflow, an error
	 * message will be printed to the terminal, but the value will not be correct.
	 * 
	 * @param addend a HugeInteger to be added
	 */
	// instance method for adding a HugeInteger to this HugeInteger;
	// acts as a mutator method
	/*
	 *  Addition algorithm implemented from pseudocode described in this very
	 *  helpful and informative paper attributed to a Dr. Francis J. Wright.
	 *  Found here: 
	 *  http://www.cs.berkeley.edu/~fateman/282/F%20Wright%20notes/week4.pdf
	 */
	public void add(HugeInteger addend)
	{
		int sum = 0;
		int carry = 0;
		
		for(int counter = 1; counter <= MAX_INT_SIZE; counter++)
		{
			sum =  this.getDigit(counter) + addend.getDigit(counter) + carry;
			
			if(sum < 10)
			{
				this.setDigit(counter, sum);
				carry = 0;
			}
			
			else
			{
				this.setDigit(counter, (sum-10));
				carry = 1;
			}
		}
		
		// if addition is finished and the carry is not 0, an overflow has occurred
		if(carry != 0)
		{
			System.out.println("addition result is incorrect: an overflow occurred");
		}
	}
	
	/**
	 * This mutator method subtracts the value of another HugeInteger object from the
	 * calling HugeInteger object. If the subtraction would yield a negative number,
	 * the operation is canceled and an error message is printed to the terminal.
	 * 
	 * @param subtrahend a HugeInteger to be subtracted
	 */
	// instance method for subtracting a HugeInteger from this HugeInteger;
	// acts as a mutator method
	/*
	 *  Subtraction algorithm implemented from pseudocode described in this very
	 *  helpful and informative paper attributed to a Dr. Francis J. Wright.
	 *  Found here: 
	 *  http://www.cs.berkeley.edu/~fateman/282/F%20Wright%20notes/week4.pdf
	 */
	public void subtract(HugeInteger subtrahend)
	{	
		int difference = 0;
		int borrow = 0;
		
		// check if the result will be positive and if so, proceed with subtraction
		if(this.isGreaterThanOrEqualTo(subtrahend))
		{
			for(int counter = 1; counter <= MAX_INT_SIZE; counter++)
			{
				difference = this.getDigit(counter) - subtrahend.getDigit(counter) - borrow;
				
				if(difference >= 0)
				{
					this.setDigit(counter, difference);
					borrow = 0;
				}
				
				else
				{
					this.setDigit(counter, (difference+10));
					borrow = 1;
				}
			}
		}
		
		/*
		 *  if this HugeInteger was not greater than the subtrahend, then the result
		 *  would have been negative
		 */
		else
		{
			// Alert the user that the result would have been negative
			System.out.println("subtraction failed: result would be negative");
		}
	}
		
		
	/**
	 * This method checks if the calling HugeInteger object has a value of zero.
	 * 
	 * @return true if this HugeInteger has a value of zero, otherwise returns false
	 */
	// instance method to check if this HugeInteger has a value of zero
	public boolean isZero()
	{
		for(int counter = 0; counter < MAX_INT_SIZE; counter++)
		{
			// if any digit in the array is not zero, return false immediately
			if(this.getIndex(counter) != 0)
			{
				return false;
			}
		}
		
		// if all of the digits in the array were zero, return true
		return true;
	}
	
	/**
	 * This method checks if another HugeInteger object is equivalent
	 * to the calling HugeInteger object.
	 * 
	 * @param argument HugeInteger to be compared
	 * @return true if values are equal, otherwise returns false
	 */
	// instance method to check if this HugeInteger is equal to another HugeInteger
	public boolean isEqualTo(HugeInteger argument)
	{	
		for(int counter = 0; counter <MAX_INT_SIZE; counter++)
		{
			if(this.getIndex(counter) != argument.getIndex(counter))
			{
				// if any digits in the arrays do not match, return false immediately
				return false;
			}
		}
		
		// if all of the digits in the array matched, return true
		return true;
	}
	
	/**
	 * This method checks if another HugeInteger object is not equivalent
	 * to the calling HugeInteger object.
	 * 
	 * @param argument HugeInteger to be compared
	 * @return true if values are not equal, otherwise returns false
	 */
	// instance method to check if this HugeInteger is not equal to another HugeInteger
	public boolean isNotEqualTo(HugeInteger argument)
	{
		// return the negation of isEqualTo
		return !(isEqualTo(argument));
	}
	
	/**
	 * This method checks if the value of the calling HugeInteger is greater than
	 * the value of another.
	 * 
	 * @param argument HugeInteger to be compared
	 * @return true if this HugeInteger is greater, otherwise returns false
	 */
	// instance method to check if this HugeInteger is greater than another HugeInteger
	/*
	 *  Comparision algorithm inspired by pseudocode described in this very
	 *  helpful and informative paper attributed to a Dr. Francis J. Wright.
	 *  Found here: 
	 *  http://www.cs.berkeley.edu/~fateman/282/F%20Wright%20notes/week4.pdf
	 */
	public boolean isGreaterThan(HugeInteger argument)
	{
		int counter = 0;
		while( (this.getIndex(counter) == argument.getIndex(counter)) && (counter < MAX_INT_SIZE) )
		{
			counter++;
		}
		
		if(counter == MAX_INT_SIZE)
		{
			return false;
		}
		
		else
		{
			return (this.getIndex(counter) > argument.getIndex(counter));
		}
	}
	
	/**
	 * This method checks if the value of the calling HugeInteger object is
	 * greater than or equal to the value of another.
	 * 
	 * @param argument HugeInteger to be compared
	 * @return true if this HugeInteger is greater than or equal to, otherwise returns false
	 */
	// instance method to check if this HugeInteger is greater than or equal to another HugeInteger
	public boolean isGreaterThanOrEqualTo(HugeInteger argument)
	{
		// use preexisting methods to check the two possibilities
		return (this.isEqualTo(argument) || this.isGreaterThan(argument));
	}
	
	/**
	 * This method checks if the value of the calling HugeInteger object is
	 * less than the value of another.
	 * 
	 * @param argument HugeInteger to be compared
	 * @return true if this HugeInteger is less than, otherwise returns false
	 */
	// instance method to check if this HugeInteger is less than another HugeInteger
	/*
	 *  Comparision algorithm inspired by pseudocode described in this very
	 *  helpful and informative paper attributed to a Dr. Francis J. Wright.
	 *  Found here: 
	 *  http://www.cs.berkeley.edu/~fateman/282/F%20Wright%20notes/week4.pdf
	 */
	public boolean isLessThan(HugeInteger argument)
	{
		int counter = 0;
		while( (this.getIndex(counter) == argument.getIndex(counter)) && (counter < MAX_INT_SIZE) )
		{
			counter++;
		}
		
		if(counter == MAX_INT_SIZE)
		{
			return false;
		}
		
		else
		{
			return (this.getIndex(counter) < argument.getIndex(counter));
		}
	}
	
	/**
	 * This method checks if the value of the calling HugeInteger object is
	 * less than or equal to the value of another.
	 * 
	 * @param argument HugeInteger to be compared
	 * @return true if this HugeInteger is less than or equal to, returns false otherwise
	 */
	// instance method to check if this HugeInteger is less than or equal to another HugeInteger
	public boolean isLessThanOrEqualTo(HugeInteger argument)
	{
		// use preexisting methods to check the two possibilities
		return (this.isEqualTo(argument) || this.isLessThan(argument));
	}
	
	/**
	 * This method returns a string representation of the value of this HugeInteger.
	 * 
	 * @return a String representation of this HugeInteger value
	 */
	// instance method to return a string representing the HugeInteger value
	@Override public String toString()
	{
		/*
		 *  Using a StringBuffer object allows characters to be assembled
		 *  into a string without creating a myriad of immutable String 
		 *  objects which must be eventually garbage collected, improving performance.
		 */
		
		// create a new StringBuffer object with initial capacity equal to the digitArray capacity
		StringBuffer stringValue = new StringBuffer(MAX_INT_SIZE);
		
		// iterate from left to right through the array, as though listing digits
		for (int counter = 0; counter < MAX_INT_SIZE; counter++)
		{
			// append the integer to the StringBuffer
			// the append method converts the integer to its string representation
			stringValue.append(this.getIndex(counter));
		}
		
		// return the string generated by the StringBuffer's toString method
		return stringValue.toString();
	}
	
	/**
	 * This method returns a shortened string representation of the value of
	 * this HugeInteger. When possible, extraneous leading zeroes are not
	 * included in the string. If the value is zero, the returned string
	 * consists of a single zero. 
	 * 
	 * @return a String representation of this HugeInteger value
	 */
	// instance method to return a string representing the HugeInteger value,
	// but without any extraneous leading zeros
	public String toShortString()
	{
		// create a StringBuffer object with initial capacity equal to the digitArray capacity
		StringBuffer stringValue = new StringBuffer(MAX_INT_SIZE);
		
		// if the value of the HugeInteger is zero, return a single zero
		if( this.isZero() )
		{
			stringValue.append(0);
		}
		
		// otherwise commence printing
		else
		{
			// iterate from left to right through any extraneous leading zeros
			int counter = 0;
			
			while( (this.getIndex(counter) == 0) && (counter < MAX_INT_SIZE) ) 
			{
				counter++;
			}
			
			// when a value that is not  zero is reached, ordinary printing can begin
			for( ; counter < MAX_INT_SIZE; counter++)
			{
				stringValue.append(this.getIndex(counter));
			}
		}
		
		// return the string generated by the StringBuffer's toString method
		return stringValue.toString();
	}
}
