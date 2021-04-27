package edu.cuny.csi.csc330.lab6;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

import edu.cuny.csi.csc330.util.Randomizer;

public class QuickPicker {

	// constants  specific to current game - BUT NOT ALL GAMES 
	public final static int DEFAULT_GAME_COUNT = 1; 
	private static int SELECTION_POOL_SIZE = 59; 
	private static int SELECTION_POOL_SIZE_2 = 0; 
	private static int SELECTION_COUNT = 6; 
	private static int SELECTION_COUNT_2 = 0;
	private static String GAME_NAME = "Lotto"; 
	private static String propName;
	private static String vendor = "Mo's Migthy Deli";
	
	private int[][] LottoArr1;
	private int[][] LottoArr2;
	
	
	
	private static void initPropertise() throws QuickPickerException {
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(propName);
		
		if (bundle.containsKey("GameName")) {
			GAME_NAME = bundle.getString("GameName");
		}
		else
			throw new QuickPickerException("Error... ", 4);

		if (bundle.containsKey("PoolSelection1")) {
			SELECTION_COUNT = Integer.parseInt(bundle.getString("PoolSelection1"));
		}
		else
			throw new QuickPickerException("Error... ", 5);

		if (bundle.containsKey("PoolSize1")) {
			SELECTION_POOL_SIZE = Integer.parseInt(bundle.getString("PoolSize1"));
		}
		else
			throw new QuickPickerException("Error... ", 6);

		if (bundle.containsKey("PoolSelection2")) {
			SELECTION_COUNT_2 = Integer.parseInt(bundle.getString("PoolSelection2"));
		}
		else
			throw new QuickPickerException("Error... ", 7);

		if (bundle.containsKey("PoolSize2")) {
			SELECTION_POOL_SIZE_2 = Integer.parseInt(bundle.getString("PoolSize2"));
		}
		else
			throw new QuickPickerException("Error... ", 8);

		if (bundle.containsKey("Vendor")) {
			vendor = bundle.getString("Vendor");
		}
		else
			throw new QuickPickerException("Error... ", 9);

		} catch (MissingResourceException  e) {
			throw new QuickPickerException("Error...",QuickPickerException.INVALID_FILENAME);
		}
		
	}
	
	public QuickPicker() {
		init(DEFAULT_GAME_COUNT); 
	}
	
	public QuickPicker(int games) {
		init(games); 
	}
	
	/*
	 * gameArr1
	 * This Array method initializes a 2-D array for the first
	 * selection count in the QuickPicker game
	 */
	private int[][] gameArr1(int games){
		int[][] arr1 = new int [games][SELECTION_COUNT];
		int numPick1 = 0;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				numPick1 = Randomizer.generateInt(1,SELECTION_POOL_SIZE);
				if (checkDuplicates(numPick1, arr1[i]) == false) {
					arr1[i][j] = numPick1; 
				}
				else j--;
			}
			Arrays.sort(arr1[i]);
		}
		return arr1;
	}
	
	/*
	 * gameArr2
	 * This Array method initializes a 2-D array for the second
	 * selection count in the QuickPicker game
	 */
	private int[][] gameArr2(int games) {
		int[][] arr2 = new int [games][SELECTION_COUNT_2];
		int numPick2 = 0;
		for (int i = 0; i < arr2.length; i++) {
			for (int j = 0; j < arr2[i].length; j++) {
				numPick2 = Randomizer.generateInt(1, SELECTION_POOL_SIZE_2);
				if (checkDuplicates(numPick2, arr2[i]) == false) {
					arr2[i][j] = numPick2; 
				}
				else j--;
			}
			Arrays.sort(arr2[i]);
		}
		return arr2;
	}
	
	/*
	 * boolean method checks for duplicate case in an Array
	 */
	private static boolean checkDuplicates(int num, int[] numArr) {
		for (int i = 0; i < numArr.length; i ++) {
			if(num == numArr[i] )
				return true;
		}
		return false;
	}
	
	/*
	 * initializtion method to load the values of both 
	 * gameArr1 and gameArr2 into the LottoArr1 and LottoArr2.  
	 */
	private void init(int games) {
		LottoArr1 = new int [games][SELECTION_COUNT];
		LottoArr2 = new int [games][SELECTION_COUNT_2];
		
		LottoArr1 = Arrays.copyOf(gameArr1(games), LottoArr1.length);
		LottoArr2 = Arrays.copyOf(gameArr2(games), LottoArr2.length);
	}
	
	/*
	 * Method displays the arrays generated in gameNumPrint()
	 * as well as the heading and footer methods 
	 */
	public void displayTicket() {
		// display ticket heading 
		displayHeading(); 
	
		// Display selected numbers 
		gameNumPrint();

		// display ticket footer 
		displayFooter(); 
		
		return;
	}
	
	/*
	 * Print method to print out the generated arrays of LottoArr(1 and 2)
	 */
	protected void gameNumPrint() {
		for(int i = 0; i < LottoArr1.length; i ++) {
			System.out.printf("(%2d) ", i+1);
			for (int j = 0; j < LottoArr1[i].length; j++) {
				System.out.printf("%02d ", LottoArr1[i][j]);
			}
			if (SELECTION_COUNT_2 > 0) {
				System.out.printf("    (( ");
				for (int k = 0; k < LottoArr2[i].length; k++) {
					System.out.printf("%02d ", LottoArr2[i][k]);
				}
				System.out.printf("))");
			}
			System.out.println("");
		}
	}
	
	protected void displayHeading() {
		
		Date lottoDate = new Date();
		
		System.out.println("--------------------------------------------");
		System.out.println("--------------- " + GAME_NAME.toUpperCase() + "----------------");
		System.out.println("------- " + lottoDate.toString() + " -------\n");
		
	}
	
	protected void displayFooter() {
		
		System.out.println();
		System.out.printf("%-10s % ,10d \n", "Odds of Winning: 1 in", calculateOdds());
		System.out.println("------- (c) " + vendor + " -------");
		System.out.println("--------------------------------------------");
		
	}
	
	/**
	 * 
	 * @return
	 */
	private BigInteger calculateOdds() {
 
		BigInteger top = factorial(SELECTION_POOL_SIZE);
		BigInteger leftBottom = factorial(SELECTION_COUNT);
		BigInteger rightBottom = factorial((SELECTION_POOL_SIZE - SELECTION_COUNT));
		BigInteger bottom = leftBottom.multiply(rightBottom);
		BigInteger odds = top.divide(bottom);
		
		if (SELECTION_COUNT_2 > 0 ) {
			BigInteger top2 = factorial(SELECTION_POOL_SIZE_2);
			BigInteger leftBottom2 = factorial(SELECTION_COUNT_2);
			BigInteger rightBottom2 = factorial((SELECTION_POOL_SIZE_2 - SELECTION_COUNT_2));
			BigInteger bottom2 = leftBottom2.multiply(rightBottom2);
			BigInteger odds2 = top2.divide(bottom2);
			odds = odds.multiply(odds2);
		}
		
		return odds;
		
	}
  
	// Implement factorial method
	
	private BigInteger factorial(int num) {
		
		BigInteger fact = new BigInteger ("1");
		
		for (int i = 2; i <= num; i++) {
			fact = fact.multiply(BigInteger.valueOf(i));
		}
		return fact;
		
	}
	
	public static void main(String[] args) {
		// takes an optional command line parameter specifying number of QP games to be generated
		//  By default, generate 1  
		int numberOfGames  = DEFAULT_GAME_COUNT; 
		
		System.out.printf("Please enter a .properties file name: ");
		Scanner scanner = new Scanner (System.in);
		propName = scanner.next();
		/*
		System.out.printf("Please specify number of games: ");
		numberOfGames = scanner.nextInt();
		*/
		try {
			initPropertise();
		} catch (QuickPickerException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		if(args.length > 0) {  // if user provided an arg, assume it to be a game count
			numberOfGames = Integer.parseInt(args[0]);  // [0] is the 1st element!
		}
		
		
		QuickPicker lotto = new QuickPicker(numberOfGames);
		
		System.out.println();
		lotto.displayTicket(); 

	}

}
