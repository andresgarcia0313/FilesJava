package com.organization;

/**
 * Ejemplo de manejo de archivos en Java utilizando IO y NIO.2.
 * Este ejemplo muestra cómo leer y escribir archivos, así como manejar directorios.
 * Incluye ejemplos de serialización y deserialización de objetos.
 */

import java.io.*; // Importar las clases necesarias para IO que permite leer y escribir archivos
import java.nio.file.*; // Importar las clases necesarias para NIO.2 que permite manejar archivos y directorios
import java.util.*; // Importar las clases necesarias para colecciones que funciona para almacenar objetos
import java.util.stream.*; // Importar Stream para operaciones sobre archivos que funciona para procesar secuencias de elementos


// Clase que representa un objeto que se puede serializar
class Persona implements Serializable { // Implementar Serializable para que el objeto se pueda serializar
  private static final long serialVersionUID = 1L; // Versión de serialización para controlar la compatibilidad
  private final String nombre;
  private final int edad;

  public Persona(String nombre, int edad) {
    this.nombre = nombre;
    this.edad = edad;
  }

  @Override // Sobrescribir el método toString para mostrar la información del objeto
  public String toString() {// Devolver una cadena con la información del objeto
    return "Nombre: " + nombre + ", Edad: " + edad;
  }
}

public class App {

  public static void main(String[] args) {
    // Ruta del archivo para serialización
    Path rutaArchivo = Paths.get("persona.ser");// Crear un objeto Path con la ruta del archivo

    // Serialización: guardar el objeto en un archivo
    serializarObjeto(new Persona("Juan", 30), rutaArchivo);// Serializar un objeto en un archivo

    // Deserialización: leer el objeto desde un archivo
    deserializarObjeto(rutaArchivo);// Deserializar un objeto desde un archivo

    // Crear un archivo y escribir en él
    Path nuevoArchivo = Paths.get("nuevo_archivo.txt");// Funciona para crear un objeto Path con la ruta del archivo
    crearArchivo(nuevoArchivo);// Crear un archivo
    escribirArchivo(nuevoArchivo, "Hola, Mundo!");// Escribir en el archivo

    // Leer el archivo
    leerArchivo(nuevoArchivo);// Leer el archivo

    // Listar archivos en el directorio actual
    listarArchivosEnDirectorio(Paths.get("."));// Listar archivos en el directorio actual

    // Caminar por directorios y subdirectorios
    caminarPorDirectorios(Paths.get("."));// Caminar por directorios y subdirectorios

    // Copiar y mover archivos
    Path archivoCopiado = Paths.get("copiado_archivo.txt");// Crear un objeto Path con la ruta del archivo
    copiarArchivo(nuevoArchivo, archivoCopiado);// Copiar un archivo
    moverArchivo(archivoCopiado, Paths.get("movido_archivo.txt"));// Mover un archivo

    // Eliminar archivos
    eliminarArchivo(Paths.get("movido_archivo.txt"));
  }

  // Método para serializar un objeto en un archivo
  private static void serializarObjeto(Persona persona, Path rutaArchivo) {
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(rutaArchivo))) {
      oos.writeObject(persona);
      System.out.println("Objeto serializado con éxito.");
    } catch (IOException e) {
      System.err.println("Error al serializar el objeto: " + e.getMessage());
    }
  }

  /**
   * Método para deserializar un objeto desde un archivo.
   * @param rutaArchivo
   */
  private static void deserializarObjeto(Path rutaArchivo) {
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(rutaArchivo))) {
      Persona personaLeida = (Persona) ois.readObject();
      System.out.println("Objeto deserializado con éxito.");
      System.out.println(personaLeida);
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Error al deserializar el objeto: " + e.getMessage());
    }
  }

  // Método para crear un archivo
  private static void crearArchivo(Path rutaArchivo) {
    try {
      Files.createFile(rutaArchivo);
      System.out.println("Archivo creado con éxito.");
    } catch (IOException e) {
      System.err.println("Error al crear el archivo: " + e.getMessage());
    }
  }

  // Método para escribir en un archivo
  private static void escribirArchivo(Path rutaArchivo, String contenido) {
    try {
      Files.write(rutaArchivo, Arrays.asList(contenido), StandardOpenOption.WRITE);
      System.out.println("Archivo escrito con éxito.");
    } catch (IOException e) {
      System.err.println("Error al escribir en el archivo: " + e.getMessage());
    }
  }

  // Método para leer un archivo
  private static void leerArchivo(Path rutaArchivo) {
    try {
      List<String> lineas = Files.readAllLines(rutaArchivo);
      System.out.println("Contenido del archivo:");
      lineas.forEach(System.out::println);
    } catch (IOException e) {
      System.err.println("Error al leer el archivo: " + e.getMessage());
    }
  }

  // Método para listar archivos en un directorio
  private static void listarArchivosEnDirectorio(Path directorio) {
    try (Stream<Path> rutas = Files.list(directorio)) {
      System.out.println("Archivos en el directorio:");
      rutas.forEach(System.out::println);
    } catch (IOException e) {
      System.err.println("Error al listar archivos en el directorio: " + e.getMessage());
    }
  }

  // Método para caminar por directorios y subdirectorios
  private static void caminarPorDirectorios(Path directorio) {
    try (Stream<Path> rutas = Files.walk(directorio)) {
      System.out.println("Contenido del directorio y subdirectorios:");
      rutas.forEach(System.out::println);
    } catch (IOException e) {
      System.err.println("Error al caminar por directorios: " + e.getMessage());
    }
  }

  // Método para copiar un archivo
  private static void copiarArchivo(Path origen, Path destino) {
    try {
      Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
      System.out.println("Archivo copiado con éxito.");
    } catch (IOException e) {
      System.err.println("Error al copiar el archivo: " + e.getMessage());
    }
  }

  // Método para mover un archivo
  private static void moverArchivo(Path origen, Path destino) {
    try {
      Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
      System.out.println("Archivo movido con éxito.");
    } catch (IOException e) {
      System.err.println("Error al mover el archivo: " + e.getMessage());
    }
  }

  // Método para eliminar un archivo
  private static void eliminarArchivo(Path rutaArchivo) {
    try {
      Files.delete(rutaArchivo);
      System.out.println("Archivo eliminado con éxito.");
    } catch (IOException e) {
      System.err.println("Error al eliminar el archivo: " + e.getMessage());
    }
  }
}