import model.InfrastructureDepartment;
import model.Mesh;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static InfrastructureDepartment department =new InfrastructureDepartment();
    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        department.persist();
        while (true) {
            System.out.println("1.Importar\n2.Anadir\n3.Mostrar mallas\n4.Indice de peligrosidad\n5.Salir");
            int option = Integer.parseInt(reader.nextLine());
            switch (option) {
                case 1:
                    if(department.meshes.size()<=90) {
                        department.loadDataBase();
                        department.save();
                    }
                    break;
                case 2:
                    //Widght++Height++InUse++Branch
                    System.out.println("Escriba la info con el formato Widght++Height++InUse++Branch");
                    String input = reader.nextLine();
                    String[] data = input.split("\\+\\+");
                    department.getMeshes().add(new Mesh(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Boolean.parseBoolean(data[2]), data[3]));
                    department.save();
                    break;
                case 3:
                    department.showMeshes();
                    break;
                case 4:
                    department.showDangerousity();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
}