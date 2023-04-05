package model;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
public class InfrastructureDepartment {
    static String folder = "Data"; //Ruta relativa, donde se guardan archivos
    static String path1 = "C:/Users/Juan/Downloads/Datos1.csv"; //Ruta absoluta (de donde se cargan los archivos)
    static String path2= "C:/Users/Juan/Downloads/Datos2.csv"; //Ruta absoluta (de donde se cargan los archivos)
    public ArrayList<Mesh> meshes;

    public InfrastructureDepartment() {
        meshes =new ArrayList<>();
    }

    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }

    public void setMeshes(ArrayList<Mesh> students) {
        this.meshes = meshes;
    }
    public void loadDataBase() throws IOException {
        if(meshes.size()>40) {
            loadDataBase(path1);
        }
        else{
            loadDataBase(path2);
        }
    }
    private void loadDataBase(String path) throws IOException {
        File file = new File(path);
            FileInputStream fis = new FileInputStream(file); //Lector
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            String[] arr;
            String content = "";
            while ((line = reader.readLine()) != null) {
                 arr= line.split("\\|");
                boolean flag;
                try {
                    Integer.parseInt(arr[0]);
                    flag = true;
                } catch (NumberFormatException e) {
                    flag = false;
                }
                if (flag) {
                    meshes.add(
                            new Mesh(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Boolean.parseBoolean(arr[2]), arr[3])
                    );
                    content+=line+"\n";
                }
             }
    }
    public void persist() throws IOException {
        File file = new File(folder+"/data.txt"); //Me genera la persistencia (hace que los archivos no se borren)
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String content = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            Gson gson = new Gson();
            Mesh[] array = gson.fromJson(content, Mesh[].class); //Almaceno los datos como arreglo.
            meshes.addAll(Arrays.asList(array));
            fis.close();//Cierro el lector.
        }
    }
    public void save() throws IOException {  //Con esto guardo los archivos que importé y los que construya
        File file = new File(folder+"/data.txt");
        FileOutputStream fos =new FileOutputStream(file); //Escribir// Fileoutput strings (corriente de salida)

        Gson gson =new Gson();//Creo un archivo tipo Json.
        String data=gson.toJson(meshes); //Se guardan en Json.

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
        writer.write(data); //Escribir (guardar informacion)
        writer.flush(); //Vaciar el buffer (vaciar informacion en el archivo destino)
        fos.close(); //Cerrar el archivo (Se debe cerrar)
    }
    public void showMeshes(){ //Me muestra informacion de las vallas
        System.out.println(" W   H  inUse      Brand");
        for (Mesh mesh : meshes) {
            System.out.println(mesh.getWidth() + " " + mesh.getHeight() + " " + mesh.isInUse() + " " + mesh.getBrand());
        }
        System.out.println("TOTAL: "+meshes.size()+" vallas");
    }
    public void showDangerousity() throws IOException { //Muestra la peligrosidad y me genera reporte
        System.out.println("**************************");
        System.out.println("DANGEROUS BILLBOARD REPORT");
        System.out.println("**************************");
        System.out.println("The dangerous billboard are:");
        ArrayList<Mesh> arr = new ArrayList<>();
        for (Mesh mesh : meshes) {
            if (mesh.getHeight() * mesh.getWidth() >= 200000) {
                arr.add(mesh);
            }
        }
        Mesh[] arr2=arr.toArray(new Mesh[0]);
        bubbleSort(arr2);
        for (int i=0; i< arr2.length;i++){
            System.out.println(i+1+"."+"BillBoard "+arr2[i].getBrand()+" with area "+arr2[i].getWidth()*arr2[i].getHeight());
        }
        File file = new File(folder+"/report.txt");
        FileOutputStream fos =new FileOutputStream(file); //Escribir// Fileoutput strings (corriente de salida)

        Gson gson =new Gson();//Creo un archivo tipo Json.
        String data=gson.toJson(arr2); //Se guardan en Json.

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
        writer.write(data); //Escribir (guardar informacion)
        writer.flush(); //Vaciar el buffer (vaciar informacion en el archivo destino)
        fos.close(); //Cerrar el archivo (Se debe cerrar)
    }
    public static void bubbleSort(Mesh[] array){ //Ordenamiento bubble de mayor a menor para organizar el top de peligrosidad.
        Mesh previous;
        Mesh current;
        for (int i=0; i< array.length-1; i++){ //Me recorre todo el arreglo.
            for (int j=1; j< array.length-i;j++){ //Me va recorriendo el arreglo, como voy aumentando i, voy reduciendo el recorrido de j.
                if((array[j-1].getHeight()*array[j-1].getWidth())<(array[j].getWidth()*array[j].getHeight())){ //Si el arreglo en la pos anterior es mayor que el arreglo en la pos actual.
                    previous=array[j-1]; //A la variable temporal le añado la posicion menor.
                    current=array[j];//A la variable temporal le añado la posicion mayor.
                    array[j-1]=current;//Organizo el arreglo.
                    array[j]=previous;
                }
            }
        }
    }
}

