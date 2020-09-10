import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Caesar {

  public static int validString(String plainText) {
    if(plainText!=null && plainText.matches("^[a-zA-Z0-9\\s_.,?!]*$")) {
      return 1;
    }
    else {
      return 0;
    }
  }

  public static String encrypt(String plainText,int displacement) {
    String encryptString="";
    for(int i=0;i<plainText.length();i++) {
      char c=plainText.charAt(i);
      int ascii=(int)c;
      if((c>='a' && c<='z')) {
        int shiftedAscii=(ascii+displacement-97)%26+97;
        encryptString+=(char)shiftedAscii;
      }
      else if ((c>='A' && c<='Z')) {
        int shiftedAscii=(ascii+displacement-65)%26+65;
        encryptString+=(char)shiftedAscii;
      }      
      else {
        encryptString+=c;
      }
    }
    System.out.println(encryptString);
    return encryptString;
  }

  public static String decrypt(String encryptedString,int displacement) {
    String decryptString="";
    for(int i=0;i<encryptedString.length();i++) {

      char c=encryptedString.charAt(i);
      int ascii=(int)c;

      if(c>='a' && c<='z') {
        int shiftedAscii=(ascii-displacement-97)%26;

        if(shiftedAscii<0) {
          shiftedAscii+=26;
        }

        shiftedAscii+=97;
        decryptString+=(char)shiftedAscii;
      }
      else if(c>='A' && c<='Z') {
        int shiftedAscii=(ascii-displacement-65)%26;

        if(shiftedAscii<0) {
          shiftedAscii+=26;
        }
        shiftedAscii+=65;
        decryptString+=(char)shiftedAscii;
      }
      else {
        decryptString+=encryptedString.charAt(i);
      }
    }
    return decryptString;
  }

  public static void cryptAnalysis(String encryptString){
    // text is encrypted
    int flag=0;
    ArrayList<String> commonWords = new ArrayList<String>(Arrays.asList(
    "THE","HE","SHE","BUT",
    "HERE", "INSTRUMENT","PHONE","ZEBRA","ANIMAL","BOOK",
    "LAPTOP","SALAD","PIZZA","DOSA","JAVA",
    "A","ON","HAVE","BYE","HELLO",
    "TO"));
    for(int i=0; i<26; i++){
        String xHat = decrypt(encryptString, i);
        if(commonWords.contains(xHat)){
            System.out.println("Cipher broken...");
            System.out.println("Key:" + i);
            System.out.println("Encrypted Text:" + xHat);
            flag = 1;
        }
    }
    if(flag==0){
        System.out.println("Cipher couldn't be broken!");
    }
}


  public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);

    String encryptedString="";
    String decryptedString="";
    System.out.println("Enter the string");
    String plainText=sc.nextLine();
    System.out.println("ENter the Displacement");
    int displacement=sc.nextInt();

    int isValidString=validString(plainText);
    if(isValidString==0) {
      System.out.println("Not a valid string");
    }
    else {
      System.out.println("Valid String");
      encryptedString=encrypt(plainText,displacement);
      System.out.println("Encrypted String: "+encryptedString);
      cryptAnalysis(encryptedString);
      decryptedString=decrypt(encryptedString,displacement);
      System.out.println("Decrypted String: "+decryptedString);
    }
  }
}