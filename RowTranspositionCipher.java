import java.util.Arrays;
import java.util.Scanner;

public class RowTranspositionCipher {
    public static int validString(String plainText) {
        if(plainText!=null && plainText.matches("^[a-zA-Z0-9\\s_.,?!]*$")) {
          return 1;
        }
        else {
          return 0;
        }
    }

    public static int findIndex(int[] key, int value, int keyLength) {
        for(int i=0;i<keyLength;i++) {
            if(key[i]==value) {
                return i;
            }
        }
        return -1;
    }

    public static void printMatrix(String[][] matrix, int[] key, int keyLength, int textLength) {

        for(int i=0; i< keyLength; i++) {
            System.out.print(key[i]+" ");
        }
        System.out.println("");
        for(int i=0;i<textLength;i++) {
            for(int j=0;j<keyLength;j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public static String encrypt(String plainText, int[] key, int keyLength) {
        String encryptString="";

        String[][] encryptMatrix = new String[plainText.length()/keyLength][keyLength];
        int currChar=0;
        for(int i=0;i<plainText.length()/keyLength;i++) {
            for(int j=0;j<keyLength;j++) {
                // System.out.println("chatat "+plainText.charAt(currChar));
                encryptMatrix[i][j]=""+plainText.charAt(currChar++);
                
            }
        }

        for(int i=0;i<keyLength;i++) {
            int index = findIndex(key, i+1, keyLength);
            for(int j=0; j<plainText.length()/keyLength;j++) {
                encryptString+=encryptMatrix[j][index];
            }
        }

        System.out.println("Encrypt Matrix");
        printMatrix(encryptMatrix, key, keyLength, plainText.length()/keyLength);

        return encryptString;
    }

    public static String decrypt(String encryptString, int[] key, int keyLength) {
        String decryptString="";

        int currChar=0;
        String[][] decryptMatrix=new String[encryptString.length()/keyLength][keyLength];

        for(int i=0; i<keyLength;i++) {
            int index=findIndex(key, i+1, keyLength);
            for(int j=0;j<encryptString.length()/keyLength;j++) {
                decryptMatrix[j][index]=""+encryptString.charAt(currChar++);
            }
        }
        
        for(int i=0; i<encryptString.length()/keyLength;i++) {
            for(int j=0;j<keyLength;j++) {
                decryptString+=decryptMatrix[i][j];
            }
        }
        System.out.println("Decrypt Matrix");
        printMatrix(decryptMatrix, key, keyLength, encryptString.length()/keyLength);

        return decryptString;
    }

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the Key Length");
        int keyLength=sc.nextInt();
        sc.nextLine();

        System.out.println("Enter the String");
        String plainText=sc.nextLine();
        // sc.nextLine();
        int isValidString=validString(plainText);
        if(isValidString==1) {

            if(plainText.length()%keyLength==0) {

                System.out.println("Enter the key");
                int[] key=new int[keyLength];
                for(int i=0; i < keyLength; i++) {
                    key[i]=sc.nextInt();
                }
                System.out.println("Key: "+ Arrays.toString(key));
                String encryptString=encrypt(plainText, key, keyLength);
                System.out.println("Encrypt String: "+encryptString);

                String decryptString=decrypt(encryptString, key, keyLength);
                System.out.println("Decrypt String: "+decryptString);

            }
            else {
                System.out.println("Key length does not divide plain text length");
            }

        }
        else {
            System.out.println("Not a valid string");
        }

    }
}
