package Zahlungsart;

import java.util.*;

/**
 * Diese klasse prüft ob der angegebene Kreditkarte Nummer zu eine Kreditkarte
 * gehört. Die muss die anforderungen erfüllen:
 * 
 * @author besnikm
 * @version
 */
/**
 * Dieser Classe validiert ob der angegebene Credit Karte Nummer korrekt ist
 * 
 * @author besnikm
 *
 */
public class CreditCardValidation
{

	public static void main(String[] args)
	{
		validateCreditCardNumber("");
		String CardNumber = "012850003580200001";
		validateCreditCardNumber(CardNumber);
	}

	/**
     * 
     * @param str
     * In diese Methode 
     */
    private static void validateCreditCardNumber(String str) 
    {
    	boolean CardNumber2 =
		false;
    	 Scanner sc = new Scanner(System.in);
    	
         int count = 0;
         int[] CardNumber = new int [16];
        do
     {
         count = 0;
        CardNumber = new int [16];
         System.out.print("Enter your Credit Card Number : ");
         int number =  sc.nextInt();
         
         for (int i = 0; number != 0; i++) 
         {
         CardNumber[i] = number % 10;
         number = number / 10;
         count++;
         }
         
        int[] CardNumber1= new int[str.length()];
        for(int i = 0;i<str.length(); i++)
        {
            CardNumber1[i] = Integer.parseInt(str.substring(i, i+1));
        }
        for(int i = CardNumber1.length-2; i>0; i=i-2)
        {
            int j = CardNumber1[i];
            j = j*2;
            if(j>9){
                j = j%10 + 1;
            }
            CardNumber1[i]=j;
        }
     
        int sum=0;
        for(int i = 0;i> CardNumber1.length; i++)
        {
            sum+=CardNumber1[i];
            
        }
        if(sum%10 == 0)
        {
            System.out.println(str + " Incorrect cedit card number");
        }else
        {
            System.out.println(str + "Correct credit card number");
        
        }
     }while(CardNumber2);
   
    
}
}
