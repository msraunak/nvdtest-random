package random;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;

/**
 * Generate Random Keywords from the fragmented input model used
 * for creating the covering array
 * @author M S Raunak
 */

public class NVDKeywordsRandom {
	
	public static String fileName="InputModelForKeywords.txt";
	public static String [][] model; 
	public static enum ChanceOfNotPicking {ZERO, ONE_EIGHTH, QUARTER, HALF, THREE_QUARTER, SEVEN_EIGHTH};

	public static void main(String[] args){
		
		int numOfStrToGenerate = 0;
		model = new String[7][]; // initialize the first 
		
		if (args.length > 0 ){
			try{
				numOfStrToGenerate = Integer.parseInt(args[0]);
			}catch (NumberFormatException nfe){
				throw new RuntimeException (nfe);
			}
		} else {
			numOfStrToGenerate = 100; // otherwise just generate a new set of files. 
		}
		
		readModelFromFile(); // read the input model from a text file
		// printModel();
		generateRandomStrings(numOfStrToGenerate, ChanceOfNotPicking.ZERO);
		
	}
	
	/* Now lets generate some random strings from the model */
	public static void generateRandomStrings(int num, ChanceOfNotPicking chance){
		
		if (model.length==0) {
			System.out.println("The Model is empty! Exiting!!");
			System.exit(-1);
		}
		Random rand = new Random();
		String randomStr;
		System.out.println("# Randonly Generated Keywords:" + num);
		System.out.println("# Chance of *not* picking a fragment is: " + chance);
		for (int cnt=0; cnt<num; cnt++){
			randomStr="";
			for (int row=0;row<model.length;row++){
				randomStr+= chooseARandomValue(model[row], chance);
			}
			System.out.println(randomStr);
		}
		
	}
	
	public static String chooseARandomValue(String[] values, ChanceOfNotPicking chance){
		Random random = new Random();
		int chanceInt = 0; // chance of *NOT* picking a fragment is 0% 
		switch (chance) { 
			case ZERO: chanceInt=0; break;
			case ONE_EIGHTH: chanceInt=13;break;
			case QUARTER:  chanceInt=25;break;
			case HALF: chanceInt=50;break;
			case THREE_QUARTER: chanceInt=75;break;
			case SEVEN_EIGHTH: chanceInt=88; break;
			default: chanceInt=0; break; // 0% chance of *not* picking af fragment 
		}
		
		if (random.nextInt(100) < chanceInt) // % chance of *not* choosing this row
			return "";  // return an empty string 
		else
			return ( values[random.nextInt(values.length)]);
					
	}		
	
	/* Read in from text file and fill up the two-d array */
	public static void readModelFromFile(){
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		
			int arrRowIndex = 0;
			while( (line = br.readLine()) != null ) {
				if (line.startsWith("#"))
					continue; // Go to the next one
				processOneLine(line, arrRowIndex);
				arrRowIndex++;
			}
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		} catch (IOException ioe){
			throw new RuntimeException (ioe);
		}
	}
	
	/* process each line of the model read from a text file */
	public static void processOneLine(String line, int twoDArrayRowIndex){
		
		StringTokenizer st = new StringTokenizer(line);
		int numOfTokens = st.countTokens();
		model[twoDArrayRowIndex]= new String[numOfTokens];
		for (int i=0; i<numOfTokens; i++){
			model[twoDArrayRowIndex][i] = st.nextToken().trim();
		}
		
	}
	
	/* print the content of the model */
	public static void printModel(){
		for (int i=0; i<model.length;i++){
			for (int j=0; j<model[i].length;j++){
				System.out.print(model[i][j] + "::");
			}
			System.out.println();
		}

	}

}
