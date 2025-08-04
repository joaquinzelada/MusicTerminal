import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;
public class playMusic {
    public static boolean volverMenu = false;
    public static Scanner escaner = new Scanner(System.in);
    public static void main(String[]args) {
        String input = "";
        while (!input.equalsIgnoreCase("Q")){
            System.out.println("[REPRODUCTOR DE ARCHIVOS .WAV PARA LA TERMINAL]");
            System.out.println("✩°｡⋆⸜ 🎧✮");
            System.out.println("");
            System.out.println("Pulsa 'Q' para salir del programa, o 'Enter' para iniciar");
            input = escaner.nextLine();
            if (!input.equalsIgnoreCase("Q")){
                menuPrincipal();
            }
        }


    }
    public static void menuPrincipal(){
        volverMenu = false;  
            File[] albumes = listarAlbumes();
            int indiceAlbum = elegirAlbum(albumes);
            File albumElegido = albumes[indiceAlbum];

            File[] canciones = listarCanciones(albumElegido);
            int indiceCancion = elegirCancion(canciones);
            File cancion = canciones[indiceCancion];

            
            reproductorSencillo(canciones, indiceCancion, cancion);

    
    }
    public static void reproductorSencillo(File[] canciones, int indiceCancion, File cancion){
        String entrada = "";
        Clip clip = null;

        try {
            clip = AudioSystem.getClip();
            AudioInputStream archivoDeAudio = AudioSystem.getAudioInputStream(cancion);
            clip.open(archivoDeAudio);
            clip.start();
            String nombreCancion = cancion.getName();
            System.out.println("Reproduciendo: " + nombreCancion + " ♫♫");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            System.out.println("Error al reproducir el archivo: " + e.getMessage());
            return;
        }
        while(!entrada.equalsIgnoreCase("S") && !volverMenu) {
            System.out.println("");
            System.out.println("P = Play ; D = Detener ; R = Reiniciar ; S = Salir ; M = Volver al Menú");
            System.out.println("");
            System.out.println("[N = Canción siguiente; L = Canción anterior]");
            System.out.println(" ------------------->   <--------------------");
            System.out.println("                 (﹙˓ ‍🎧 ˒﹚) ");
            System.out.println("Que querés hacer?");
            entrada = escaner.nextLine().toUpperCase();

            switch(entrada) {
                case("P"): clip.start(); break;
                case("D"): clip.stop(); break;
                case("R"): clip.setMicrosecondPosition(0); break;
                case("S"): clip.close(); break;
                case("M"):
                    clip.stop();
                    clip.close();
                    volverMenu = true; 
                    break;

                
                case("N"): 
                    clip.stop();
                    clip.close();
                    indiceCancion = (indiceCancion + 1) % canciones.length;
                    cancion = canciones[indiceCancion];
                    try { 
                        AudioInputStream cancionSiguiente = AudioSystem.getAudioInputStream(cancion);
                        clip = AudioSystem.getClip();
                        clip.open(cancionSiguiente); 
                        clip.start();
                        String nombreCancion = cancion.getName();
                        System.out.println("Reproduciendo: " + nombreCancion + " ♫♫");
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
                        System.out.println("Error al reproducir el archivo: " + e.getMessage());

                    }
                    break;
                case("L"):
                    clip.stop();
                    clip.close();
                    indiceCancion = (indiceCancion - 1 + canciones.length) % canciones.length;
                    cancion = canciones[indiceCancion]; 
                    try { 
                        AudioInputStream cancionAnterior = AudioSystem.getAudioInputStream(cancion);
                        clip = AudioSystem.getClip();
                        clip.open(cancionAnterior); 
                        clip.start();
                        String nombreCancion = cancion.getName();
                        System.out.println("Reproduciendo: " +nombreCancion + " ♫♫");
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
                        System.out.println("Error al reproducir el archivo: " + e.getMessage());

                    }
                    break;
                
                default: System.out.println("Respuesta inválida");
            }
        }

    }
    public static File[] listarAlbumes(){
        File albums = new File ("Albums");
        if (!albums.exists()) {
            if (albums.mkdir()) {
             System.out.println("Carpeta 'Albums' creada (suelta las carpetas de los albumes, y dentro, las canciones en wav)");
            } else {
                System.out.println("No se pudo crear la carpeta 'Albums'.");
            }
}
        File[] archivos = albums.listFiles();
        if (albums.exists() && albums.isDirectory() && archivos != null){
                System.out.println("Albumes disponibles: ");
                int contador = 1;
                for(int i = 0; i < archivos.length; i++){
                    if(archivos[i].isDirectory()){
                        System.out.println(contador + ") " + archivos[i].getName());
                        contador++;
                    }
                }
        }
        else { 
            System.out.println("La carpeta 'Albums' no existe.");
        }
        return archivos;
    }
    public static int elegirAlbum(File[] albums){
        boolean valido = false;
        int numAlbum = -1;
        while(!valido){
            try {
                System.out.println("Elegí un álbum para reproducir (numero de álbum): ");
                numAlbum = escaner.nextInt();
                escaner.nextLine();
                if (numAlbum >= 1 && numAlbum <= albums.length && albums[numAlbum - 1].isDirectory()){ 
                    valido = true;
                }
                else {
                    System.out.println("El álbum no existe");
                }
            }
            catch (Exception e){
                System.out.println("Entrada inválida");
                escaner.next();
            }
        }
        return numAlbum - 1;
    }
    public static File[] listarCanciones(File albumElegido){
        File[] canciones = albumElegido.listFiles();
        if (albumElegido.exists() && albumElegido.isDirectory() && canciones != null){
                System.out.println("Canciones: ");
                for(int i = 0; i < canciones.length; i++){
                    if(canciones[i].isFile() && canciones[i].getName().toLowerCase().endsWith(".wav")){
                        System.out.println(( i + 1) + ") " + canciones[i].getName());
                    }
                }
        }
        else { 
            System.out.println("No hay canciones en la carpeta, recuerda usar .wav");
        }
        return canciones;
    }
    public static int elegirCancion(File[] canciones){
        boolean valido = false;
        int numCancion = -1;
        while(!valido){
            try {
                System.out.println("Elegí la canción a reproducir (numero de canción): ");
                numCancion = escaner.nextInt();
                escaner.nextLine();
                if (numCancion >= 1 && numCancion <= canciones.length && canciones[numCancion - 1].isFile()){ 
                    valido = true;
                }
                else {
                    System.out.println("La canción no existe");
                }
            }
            catch (Exception e){
                System.out.println("Entrada inválida");
                escaner.next();
            }
        }
        return numCancion - 1;
    }
 }
