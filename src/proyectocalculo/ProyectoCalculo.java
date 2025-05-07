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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("\n====== PROYECTO DE PRECÁLCULO ======");
                System.out.println("1. Teorema de Rolle");
                System.out.println("2. Teorema del Valor Medio");
                System.out.println("3. Método de Newton");
                System.out.println("4. Derivadas para Máximos y Mínimos");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println(">> Aquí irá el código del Teorema de Rolle");
                        break;
                    case 2:
                        System.out.println(">> Aquí irá el código del Teorema del Valor Medio");
                        break;
                    case 3:
                        System.out.println(">> Aquí irá el código del Método de Newton");
                        break;
                    case 4:
                        System.out.println(">> Aquí irá el código para máximos y mínimos");
                        break;
                    case 5:
                        System.out.println("Gracias por usar el programa.");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }

            } while (opcion != 5);
        }
    }
        // TODO code application logic here
  
    

