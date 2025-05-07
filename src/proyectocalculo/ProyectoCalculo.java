/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectocalculo;

import java.util.Scanner;

/**
 *
 * @author juanpablo
 */
public class ProyectoCalculo {

   


    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Metodos metodos = new Metodos(); 
        int opcion;

        do {
            System.out.println("\n====== PROYECTO DE PRECÁLCULO ======");
            System.out.println("1. Teorema del Valor Medio");
            System.out.println("2. Teorema de Rolle");
            System.out.println("3. Método de Newton");
            System.out.println("4. Derivadas para Máximos y Mínimos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> metodos.TeoremaValorMedio();
                
                case 2 -> metodos.TeoremaRolle();
                
                case 3 -> metodos.MetodoNewton();
                
                case 4 -> metodos.DerivadaMaxyMin();
                
                case 5 -> System.out.println("Gracias por usar el programa.");
                
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }
}    
     
  
    

