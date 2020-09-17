import java.util.Scanner;

public class RailFence {

    public static int validString(String plainText) {
        if(plainText!=null && plainText.matches("^[a-zA-Z0-9\\s_.,?!]*$")) {
          return 1;
        }
        else {
          return 0;
        }
    }

    public static void initMatrix(String plainText, int noOfRails, String[][] encryptMatrix) {
        
        for(int i=0; i<noOfRails;i++) {
            for(int j=0;j<plainText.length();j++) {
                encryptMatrix[i][j]="-";
            }
        }

        int currI=0, currJ=0, currLen=0, flag=0;
        while(currJ < plainText.length()) {
            // System.out.println(currI);
            encryptMatrix[currI][currJ]="O";
            currLen++;
            if(currI+1 < noOfRails && flag==0) {
                currI++;
            }
            else {
                currI--;
                flag=1;
            }
            if(currI==0) {
                flag=0;
            }
            currJ++;
        }
    }

    public static String encrypt(String plainText, int noOfRails) {
        String encryptString="";
        System.out.println(plainText.length());
        String[][] encryptMatrix=new String[noOfRails][plainText.length()];
        initMatrix(plainText, noOfRails, encryptMatrix);

        System.out.println("Encrypt Matrix");
        int currChar=0;
        for(int i=0;i<noOfRails;i++) {
            for(int j=0;j<plainText.length();j++) {
                if(encryptMatrix[i][j]=="O") {
                    encryptMatrix[i][j]=""+plainText.charAt(currChar++);
                }
                System.out.print(encryptMatrix[i][j]+" ");
            }
            System.out.println("");
        }
        
        for(int i=0;i<noOfRails;i++) {
            for(int j=0;j<plainText.length();j++) {
                if(encryptMatrix[i][j]!="-") {
                    encryptString+=encryptMatrix[i][j];
                }
            }
            
        }
        return encryptString;
    }

    public static String decrypt(String encryptString, int noOfRails) {
        String decryptString="";
        String[][] decryptMatrix=new String[noOfRails][encryptString.length()];

        initMatrix(encryptString, noOfRails, decryptMatrix);
        int currChar=0;

        System.out.println("Decrypt Matrix");
        for(int i=0;i<noOfRails;i++) {
            for(int j=0;j<encryptString.length();j++) {
                if(decryptMatrix[i][j]=="O") {
                    decryptMatrix[i][j]=""+encryptString.charAt(currChar++);
                }
                System.out.print(decryptMatrix[i][j]+" ");
            }
            System.out.println("");
        }

        
        for(int i=0;i<noOfRails;i++) {
            for(int j=0;j<encryptString.length();j++) {
                if(decryptMatrix[i][j]!="-") {
                    decryptString+=decryptMatrix[i][j];
                }
            }
        }
        return decryptString;
    }

    public static void main(String[] args) {
        int noOfRails;
        String plainText="";
        String encryptString="";
        String decryptString="";

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rails");
        noOfRails = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the plain text: ");
        plainText=sc.nextLine();
        int isValidString=validString(plainText);

        if(isValidString==1) {

            encryptString=encrypt(plainText, noOfRails);
            System.out.println("Encrypted String: "+encryptString);
            decryptString=decrypt(encryptString, noOfRails);
            System.out.println("Decrypted String: "+decryptString);

        }
        else {
            System.out.println("Not a valid string");
        }
    }
}