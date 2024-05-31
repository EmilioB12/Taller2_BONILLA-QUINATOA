import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese un numero: ");
        int num= sc.nextInt();
        int resultFactorial = factorial(num);
        System.out.println("El factorial de "+num+ " es "+resultFactorial);


        }
        public static int factorial(int numero){
         if(numero<=1)
             return 1;
         else
             return numero* factorial(numero-1);
        }
    }
