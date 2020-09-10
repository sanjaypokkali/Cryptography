import java.util.Scanner;


public class VigenereCipher {

    public static int validString(String plainText) {
        if(plainText!=null && plainText.matches("^[a-zA-Z0-9\\s_.,?!]*$")) {
          return 1;
        }
        else {
          return 0;
        }
    }

    public static String encrypt(String plainText, String key) {
        String encryptString="";

        for(int i=0;i<plainText.length();i++) {
            int plainInt=((int)plainText.charAt(i))-65;
            int keyInt=((int)key.charAt(i%key.length()))-65;

            int shiftedChar=(plainInt+keyInt)%26+65;
            encryptString+=""+(char)shiftedChar;
        }

        return encryptString;
    }

    public static String decrypt(String encryptedString, String key) {
        String decryptString="";

        for(int i=0;i<encryptedString.length();i++) {
            int encryptInt=(int)encryptedString.charAt(i);
            int keyInt=(int)key.charAt(i%key.length());

            int shiftedChar=(encryptInt-keyInt+26)%26;
            shiftedChar+=65;
            decryptString+=""+(char)shiftedChar;
        }

        return decryptString;
    }

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        String plainText;
        String key="";
        String encryptedString="";
        String decryptedString="";

        System.out.println("Enter the plain text");
        plainText=sc.nextLine();

        int isValidString=validString(plainText);
        if(isValidString==1) {
            System.out.println("Enter key");
            key=sc.nextLine();

            encryptedString=encrypt(plainText, key);
            System.out.println("Encrypted String: "+encryptedString);

            decryptedString=decrypt(encryptedString,key);
            System.out.println("Decrypted String: "+decryptedString);
        }
        else {
            System.out.println("Not a valid string");
        }
    }
}
