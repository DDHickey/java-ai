import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.lang.Math;
// import java.util.*; includes everything in library.

// NOTE:
// 
// *Guessed Letters Array
// *Guessed Vowels 

public class searchingWord
{
 
  public static void main(String args[])
  {
   
    // Declare Variables.
    File myFile;
    Scanner keys = null;
    int counter = 0;
    // 58110 Words in text file.
    ArrayList<String> wordArray = new ArrayList<String>();
    ArrayList<String> underscoreArray = new ArrayList<String>();
    ArrayList<Integer> index = new ArrayList<Integer>();
    String guessedLetters = "";
    int numOfGuesses = 10;
    boolean gameInProg = true;
    String myGuess = "a";
    boolean doneCheckingVowels = false;
    int vowelCount = 1;
    
    
    try 
    {
      // TODO: Initializes Scanner and File.
     myFile = new File("words.txt");
     keys = new Scanner(myFile);
    }
    catch (Exception ex)
    {
     System.err.println("Cannot read file."); 
    }
    
    while (keys.hasNextLine())
    {
      // TODO: Catches any errors of failing to open file.
      String line = keys.next();
      wordArray.add(line);
      counter += 1;
    }
    keys.close();
    
   Scanner question = new Scanner(System.in);
   
   System.out.println("Welcome to Hangman AI. \n Shortly you will think of a word and give me the number of characters in the word. \n Remember you are playing against me, the computer.\n");
   
    System.out.print("How many letters are in this word? ");
    int numOfLetters = question.nextInt();
    wordArray = condenseArrayWordSize(wordArray,numOfLetters);
    
    for(int j = 0; j < numOfLetters; j++)
    {
     underscoreArray.add("_ ");
    }
    
    while(gameInProg)
    {
      if(checkGuessCount(numOfGuesses))
      break;
    printGUI(numOfGuesses,numOfLetters,wordArray,underscoreArray);
    if(!doneCheckingVowels) {
    myGuess = makeVowelGuess(vowelCount);
    vowelCount += 1;
    }
    else {
    myGuess = makeGuess(wordArray, underscoreArray, guessedLetters);
    }
    guessedLetters += myGuess;
    
    System.out.print("\nDoes your word contain a(n) " + myGuess.toUpperCase() + "? ");
    System.out.print("(Y/N) \n");
    String condition = question.next();
    
    Scanner question2 = new Scanner(System.in);
    
    if(condition.toUpperCase().equals("Y"))
    {
      doneCheckingVowels = true;
      System.out.println("Type the position(s) where the letters comes up (separate by a space).");
      String line = question2.nextLine();
      String indexString[] = line.split(" ");
      for(String word: indexString)
      {
       index.add(Integer.parseInt(word) - 1);
      }
 
      wordArray = condenseArrayLetterGuess(wordArray, myGuess, index);
      underscoreArray = updateUnderscore(underscoreArray,index, myGuess);
       index.clear();
    }
    else 
    {
     numOfGuesses--;
     wordArray = condenseArrayLetterGuessWrong(wordArray,myGuess);
    }
   }
    if(checkGuessCount(numOfGuesses))
    {
     System.out.println("I ran out of guesses! \n Good Game!");
     try
     {
       // TODO: Add Delay.
       Thread.sleep(1000);
     }
     catch(Exception ex)
     {
      System.err.print("Thread.sleep() not working!"); 
     }
     System.exit(1);
    }
 }
  
  // Methods begin.
  
  static void printGUI(int numOfGuesses, int numOfLetters, ArrayList<String> wordArray, ArrayList<String> underscoreArray)
  {
    // TODO: Print GUI to user.
    printGuesses(numOfGuesses);
    printUnderscores(numOfLetters,underscoreArray);
    printIndexes(numOfLetters);
  
  }
  
  static void printUnderscores(int n, ArrayList<String> underscoreArray)
  {
    // TODO: Provides the user a visual feedback on the status of AI guesses -> void
    System.out.print("My word to guess is: ");
    for(int i = 0; i < n; i++)
    {
      System.out.print(underscoreArray.get(i));
    }
    System.out.println();
  }
  
  static void printIndexes(int n)
  {
    System.out.print("                     ");
    for(int i = 1; i <= n; i++)
    {
      System.out.print(i + " ");
    }
  }
  
  static void printGuesses(int g)
  {
    // TODO: Lets user know how many guesses are left -> void
    if(g < 2)
    System.out.println("I have " + g + " guess left.");
    else 
      System.out.println("I have " + g + " guesses left.");
  }
  
  static ArrayList<String> condenseArrayWordSize(ArrayList<String> array, int guess)
  {
    // TODO: Condenses the ArrayList to the size of guessed letters -> array of words
   ArrayList<String> newArray = new ArrayList<String>();
    
    for(int i = 0; i < array.size(); i++)
    {
      if(array.get(i).length() == guess)
      {
        newArray.add(array.get(i));
      }
    }
    return newArray;
  }
  
  static ArrayList<String> condenseArrayLetterGuess(ArrayList<String> array, String letter, ArrayList<Integer> index)
  {
    // TODO: Condenses the ArrayList to include letters from guess -> array of words
    ArrayList<String> newArray = new ArrayList<String>();
    
    for(int k = 0; k < index.size(); k++)
    {
    for(int i = 0; i < array.size(); i++)
    {
      if(array.get(i).substring(index.get(k),index.get(k) + 1).equals(letter))
      {
        newArray.add(array.get(i));
      }
    }
    array = new ArrayList<String>(newArray);
    if(k+1 != index.size())
      newArray.clear();
    }
    return newArray;
  }
  
  static ArrayList<String> condenseArrayLetterGuessWrong(ArrayList<String> array, String letter)
  {
    // TODO: Condenses the ArrayList to include letters from guess -> array of words
    ArrayList<String> newArray = new ArrayList<String>();
    
    for(int i = 0; i < array.size(); i++)
    {
      if(!array.get(i).contains(letter))
      {
        newArray.add(array.get(i));
      }
    }
    return newArray;
  }
  
  static void printArray(ArrayList<String> wordArray)
  {
    // TODO: Prints the array -> void
    for(int i = 0; i < wordArray.size(); i++)
    {
      System.out.println(wordArray.get(i));
    }
  }
  
  static ArrayList<String> updateUnderscore(ArrayList<String> score, ArrayList<Integer> index, String letter)
  {
    for (int i = 0; i < index.size(); i++)
    {
     score.set(index.get(i), letter + " "); 
    }
    return score;
  }
  
  static String makeGuess(ArrayList<String> array, ArrayList<String> underscore, String guessed)
  {
   // TODO: AI guess for letter -> String letter
    String letter = "";
    int counter = 0;
    int numOfMatches = 0;
    boolean contains = true;
    int rand = 0;
    
    for(int i = 0; i < underscore.size(); i++)
    {
      if(underscore.get(i).equals("_ "))
      {
        counter = i;
        break;
      }
    }
    letter = array.get((int)(array.size() / 2)).substring(counter,counter+1);
    //System.out.println(counter + " <-- Letter");
    rand = (int)(array.size() / 2);
    
    while(guessed.contains(letter))
    {
      rand = (int)(Math.random() * array.size());
      letter = array.get(rand).substring(counter,counter+1);
      //System.out.println(letter);
    }
    //guessed += letter;
    //System.out.println(guessed + " <-- Guessed");
  
   return letter;
  }
  
  static boolean checkGuessCount(int guess)
  {
    // TODO: Check if machine ran out of guesses.
    if(guess == 0)
    {
      return true;
    }
    return false;
  }
  
  static String makeVowelGuess(int vowelCount)
  {
   switch (vowelCount)
   {
     case 1: return "a";
     case 2: return "e";
     case 3: return "i";
     case 4: return "o";
     case 5: return "u";
     case 6: return "y";
     default: return "a"; 
   }
  }
  
  
}