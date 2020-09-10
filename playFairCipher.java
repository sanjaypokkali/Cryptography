import java.util.Scanner;
public class playFairCipher {

    public static int validString(String plainText) {
        if(plainText!=null && plainText.matches("^[a-zA-Z0-9\\s_.,?!]*$")) {
          return 1;
        }
        else {
          return 0;
        }
    }

    public static int checkIfCharExists(char[] array,char check) {
        for(int i=0;i<array.length;i++) {
            if(array[i]==check) {
                return 1;
            }
        }
        return 0;
    }

    public static int[] findPosOfChar(String array, char[][] keySquare) {
        int[] XY=new int[4];
        char t1=array.charAt(0);
        char t2=array.charAt(1);
        int flag1=0;
        int flag2=0;
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                if(keySquare[i][j]==t1) {
                    XY[0]=i;
                    XY[1]=j;
                    flag1=1;
                }
                if(keySquare[i][j]==t2) {
                    XY[2]=i;
                    XY[3]=j;
                    flag2=1;
                }
                if(flag1==1 && flag2==1) {
                    break;
                }
            }
            if(flag1==1 && flag2==1) {
                break;
            }
        }

        return XY;
    }

    public static char[][] generateKeySquare(String key) {

        char[][] keySquare=new char[5][5];
        char[] alreadyAdded=new char[26];
        int count=0;
        int currChar=65;
        int alreadyAddedPos=0;
        
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {

                if(count>=key.length()) {
                    char tempChar=(char)currChar;
                    if((checkIfCharExists(alreadyAdded,tempChar)==0) && (tempChar!='J')) {
                        keySquare[i][j]=tempChar;
                        alreadyAdded[alreadyAddedPos++]=tempChar;
                    }
                    else {
                        j--;
                    }
                    currChar++;
                }

                else {
                    char tempChar=key.charAt(count);
                    count++;
                    if((checkIfCharExists(alreadyAdded,tempChar)==0) && (tempChar!='J')) {
                        
                        keySquare[i][j]=tempChar;
                        alreadyAdded[alreadyAddedPos++]=tempChar;
                    }
                    else {
                        j--;
                    }
                }
            }
        }
        return keySquare;
    }

    public static String encrypt(String plainText,char[][] keySquare) {

        String encryptString="";
        String[] twoCharacters=new String[(plainText.length()+1)/2];

        int twoCharactersPos=0;
        plainText=plainText.replaceAll("J","I");

        char char1;
        char char2;
        for(int i=0;i<plainText.length();) {

            char1=plainText.charAt(i);

            if(i+1<plainText.length()) {
                char2=plainText.charAt(i+1);
            }
            else {
                char2='Z';
            }
            String tempString="";
            if(char1!=char2) {
                tempString=""+char1+char2;
                i+=2;
            }
            else if(char1==char2){
                tempString=""+char1+"X";
                i++;
            }

            twoCharacters[twoCharactersPos++]=tempString;
        }
        
        for(int i=0;i<twoCharacters.length;i++) {
            int[] posArray=findPosOfChar(twoCharacters[i], keySquare);

            int x1=posArray[0];
            int y1=posArray[1];
            int x2=posArray[2];
            int y2=posArray[3];

            char tempChar1;
            char tempChar2;
            if(y1==y2) {
                
                if(y1==4) {
                    x1=0;
                }
                else {
                    x1++;
                }
                if(x2==4) {
                    x2=0;
                }
                else {
                    x2++;
                }
                tempChar1=keySquare[x1][y1];
                tempChar2=keySquare[x2][y2];
            }
            else if(x1==x2) {

                if(y1==4) {
                    y1=0;
                }
                else {
                    y1++;
                }
                if(y2==4) {
                    y2=0;
                }
                else {
                    y2++;
                }
                tempChar1=keySquare[x1][y1];
                tempChar2=keySquare[x2][y2];

            }
            else {
                tempChar1=keySquare[x1][y2];
                tempChar2=keySquare[x2][y1];
            }
            encryptString+=""+tempChar1+tempChar2;
        }
        return encryptString;
    }

    public static String decrypt(String encryptString, char[][] keySquare) {
        String decryptString="";

        String[] twoCharacters=new String[(encryptString.length()+1)/2];
        int twoCharactersPos=0;
        for(int i=0;i<encryptString.length();i+=2) {

            String tempString=""+encryptString.charAt(i) +encryptString.charAt(i+1);
            twoCharacters[twoCharactersPos++]=tempString;
        }
        
        for(int i=0;i<twoCharacters.length;i++) {
            int[] posArray=findPosOfChar(twoCharacters[i], keySquare);

            int x1=posArray[0];
            int y1=posArray[1];
            int x2=posArray[2];
            int y2=posArray[3];

            char tempChar1;
            char tempChar2;
            if(y1==y2) {
                
                if(x1==0) {
                    x1=4;
                }
                else {
                    x1--;
                }
                if(x2==0) {
                    x2=4;
                }
                else {
                    x2--;
                }
                tempChar1=keySquare[x1][y1];
                tempChar2=keySquare[x2][y2];
            }
            else if(x1==x2) {

                if(y1==0) {
                    y1=4;
                }
                else {
                    y1--;
                }
                if(y2==0) {
                    y2=4;
                }
                else {
                    y2--;
                }
                tempChar1=keySquare[x1][y1];
                tempChar2=keySquare[x2][y2];

            }
            else {
                tempChar1=keySquare[x1][y2];
                tempChar2=keySquare[x2][y1];
            }
            decryptString+=""+tempChar1+tempChar2;
        }        

        return decryptString;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the Plain Text");
        String plainText=sc.nextLine();
        int isValidString=validString(plainText);
        
        if(isValidString==0) {
            System.out.println("Not a valid string");
        }
        else {
            System.out.println("Enter the Key");
            String key=sc.nextLine();
            char [][] keySquare=generateKeySquare(key);

            System.out.println("Final keySquare");
            for(int i=0;i<5;i++) {
                for(int j=0;j<5;j++) {
                    System.out.print(keySquare[i][j]+" ");
                }
                System.out.println("\n");
            }
            System.out.println("Encrypt String");
            String encryptString=encrypt(plainText, keySquare);
            System.out.println(encryptString);
            System.out.println("Decrypt String");
            String decryptString=decrypt(encryptString, keySquare);
            System.out.println(decryptString);
        }

    }
}