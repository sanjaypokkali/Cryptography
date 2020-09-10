import java.util.Scanner;

public class HillCipher {

    public static int validString(String plainText) {
        if(plainText!=null && plainText.matches("^[a-zA-Z0-9\\s_.,?!]*$")) {
          return 1;
        }
        else {
          return 0;
        }
    }

    public static int[][] generateKeyMatrix(String keyString,int rowCol) {
        int[][] keyMatrix=new int[rowCol][rowCol];
        char[] keyCharArray=keyString.toCharArray();
        int keyIndex=0;

        for(int i=0;i<rowCol;i++) {
            for(int j=0;j<rowCol;j++) {
                keyMatrix[i][j]=((int)keyCharArray[keyIndex++])-65;
            }
        }
        return keyMatrix;
    }


    public static int[] matrixMultiplication(int[][] keyMatrix,int[] threeChar,int rowCol) {
        int[] multipliedMatrix=new int[rowCol];

        for(int i=0;i<rowCol;i++) {
            int temp=0;
            for(int j=0;j<rowCol;j++) {
                temp+=keyMatrix[i][j]*threeChar[j];
            }
            multipliedMatrix[i]=Math.floorMod(temp,26);
        }
        return multipliedMatrix;
    }

    public static String encrypt(String plainText,int[][] keyMatrix,int rowCol) {
        String encryptString="";

        while(plainText.length()%rowCol!=0) {
            plainText+="Z";
        }

        char[] plainTextCharArray=plainText.toCharArray();

        for(int i=0;i<plainText.length();) {

            int[] threeChar=new int[rowCol];

            for(int j=0;j<rowCol;j++) {
                threeChar[j]=((int)plainTextCharArray[i+j])-65;
            }
            
            int[] multipliedMatrix=matrixMultiplication(keyMatrix, threeChar, rowCol);

            for(int j=0;j<rowCol;j++) {

                encryptString+=""+(char)(multipliedMatrix[j]+65);
            }

            i+=rowCol;
        }
        return encryptString;
    }

    public static String decrypt(String encryptText,int[][] keyMatrixInverse, int rowCol) {
        String decrypString="";

        char[] encryptTextCharArray=encryptText.toCharArray();

        for(int i=0;i<encryptText.length();) {

            int[] threeChar=new int[rowCol];

            for(int j=0;j<rowCol;j++) {
                threeChar[j]=((int)encryptTextCharArray[i+j])-65;
            }
            
            int[] multipliedMatrix=matrixMultiplication(keyMatrixInverse, threeChar, rowCol);

            for(int j=0;j<rowCol;j++) {

                decrypString+=""+(char)(multipliedMatrix[j]+65);
            }

            i+=rowCol;
        }
        return decrypString;   
    }

    public static int[][] generateSubMatrix(int[][] keyMatrix,int rowCol, int r, int c) {
        //TODO
        //given a column and row to exclude generate a sub matrix
        int[][] subMatrix=new int[rowCol-1][rowCol-1];
        int row=0,col=0;

        for(int i=0;i<rowCol;i++) {
            for(int j=0;j<rowCol;j++) {

                if(i==r) {
                    break;
                }
                else if(j==c) {
                    // System.out.println("j: skip");
                    ;
                }
                else {
                    subMatrix[row][col++]=keyMatrix[i][j];

                    if(col==rowCol-1) {
                        row++;
                        col=0;
                    }
                }
            }
        }

        return subMatrix;
    }

    public static int findDeterminant(int[][] keyMatrix, int rowCol)
    {
        if (keyMatrix.length== 1) 
        {
            return Math.floorMod(keyMatrix[0][0],26);
        }
        if (keyMatrix.length==2) 
        {
            return Math.floorMod((keyMatrix[0][0]* keyMatrix[1][1]) - (keyMatrix[0][1]* keyMatrix[1][0]),26);
        }
        Double sum = 0.0;
        int sign;
        for (int i=0; i<keyMatrix.length; i++) {
                if(i%2==0) {
                    sign=1;
                }
                else {
                    sign=-1;
                }
                sum += sign * keyMatrix[0][i] * findDeterminant(generateSubMatrix(keyMatrix, rowCol ,0, i),rowCol-1);
        }
        
        return Math.floorMod(sum.intValue(), 26);
    }

    public static int findInverseDeterminant(int determinant) {
        for(int i=0;i<26;i++) {
            
            if((determinant*i)%26==1) {
                return i;
            }
        }
        return 0;
    }

    public static int[][] getAdjointMatrix(int[][] keyMatrix, int rowCol) {
        int[][] adjointMatrix=new int[rowCol][rowCol];

        for(int i=0;i<rowCol;i++) {
            for(int j=0;j<rowCol;j++) {
                int signi,signj;
                if(i%2==0) {
                    signi=1;
                }
                else {
                    signi=-1;
                }

                if(j%2==0) {
                    signj=1;
                }
                else {
                    signj=-1;
                }
                adjointMatrix[i][j]=signi*signj*findDeterminant(generateSubMatrix(keyMatrix, rowCol, i, j), rowCol);

            }
        }
        int[][] transposeAdjointMatrix=new int[rowCol][rowCol];

        for(int i=0;i<rowCol;i++) {
            for(int j=0;j<rowCol;j++) {
                transposeAdjointMatrix[j][i]=adjointMatrix[i][j];
            }
        }
        return transposeAdjointMatrix;
    }

    public static int[][] findInverse(int[][] keyMatrix,int rowCol, int ideterminant) {
        int[][] adjointMatrix=getAdjointMatrix(keyMatrix, rowCol);
        int[][] inverseMatrix=new int[rowCol][rowCol];

        for(int i=0;i<rowCol;i++) {
            for(int j=0;j<rowCol;j++) {
                inverseMatrix[i][j]=Math.floorMod(ideterminant*adjointMatrix[i][j], 26);
            }
         }

        return inverseMatrix;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        int rowCol,determinant,ideterminant;
        System.out.println("Enter the rowCol: ");
        rowCol=sc.nextInt();
        sc.nextLine();

        String plainText="";
        String keyString="";
        int[][] keyMatrix=new int[rowCol][rowCol];
        int[][] keyMatrixInverse=new int[rowCol][rowCol];

        System.out.println("Enter the plain text: ");
        plainText=sc.nextLine();
        int isValidString=validString(plainText);

        if(isValidString==1) {
            System.out.println("Enter key string");
            keyString=sc.nextLine();

            while(keyString.length()!=rowCol*rowCol) {
                System.out.println("Enter key string of length 9");
                keyString=sc.nextLine();
            } 
            keyMatrix=generateKeyMatrix(keyString,rowCol);

            System.out.println("Key matrix:");
            for(int i=0;i<rowCol;i++) {
                for(int j=0;j<rowCol;j++) {
                    System.out.print(keyMatrix[i][j]+" ");
                }
                System.out.println('\n');
            }

            determinant=findDeterminant(keyMatrix, rowCol);
            ideterminant=findInverseDeterminant(determinant);

            if(ideterminant!=0) {
                String encryptString="";
                encryptString=encrypt(plainText, keyMatrix, rowCol);
                System.out.println("Encrypted String: "+encryptString);

                keyMatrixInverse=findInverse(keyMatrix, rowCol, ideterminant);

                System.out.println("Inverse Key matrix:");
                for(int i=0;i<rowCol;i++) {
                    for(int j=0;j<rowCol;j++) {
                        System.out.print(keyMatrixInverse[i][j]+" ");
                    }
                    System.out.println('\n');
                }

                String decryptString="";
                decryptString=decrypt(encryptString, keyMatrixInverse, rowCol);
                System.out.println("Decrypted String: "+decryptString);

            }
            
            else {
                System.out.println("Key does not have an inverse");
            }
        }
        else {
            System.out.println("Not valid plain text");
        }
    }
}