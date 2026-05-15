# MusicTerminal
El código creará una carpeta Albums, aquí deberá agregar una carpeta por álbum y dentro de ella las canciones en .wav 

PD: Hice este código con el conocimiento de mi primer cuatrimestre de la carrera, podría mejorarlo en un futuro y agregar interfaces gráficas. Gracias.

# EJECUCIÓN DEL PROGRAMA:
# en bash (terminal), ejecutá estos comandos:
```bash
git clone https://github.com/joaquinzelada/MusicTerminal.git
cd MusicTerminal
java playMusic.java 
java playMusic 


#FUNCIÓN PARA AGREGAR AL .BASHRC
# En .bashrc ejecuto esta función MusicTerminal(): 

MusicTerminal(){
        cd ~/MusicTerminal || return 
        java playMusic.java
}

#Luego recargá la terminal con:
source ~/.bashrc
# y podés ejecutar simplemente: 
MusicTerminal
#Gracias!
