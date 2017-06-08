import java.awt.Color;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.io.* ;
import java.net.* ;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase Servidor que extiende de la clase JFrame e implementa ActionListener. 
 * Se encarga de generar la Interfaz que usaremos para implementar los métodos
 * de control usados para el Servidor de la aplicación.
 *
 * @author Odei
 * @version 21.02.2016
 */
public class Servidor extends JFrame {  
    /**
     * Variable TextArea usada para mostrar al usuario un contenedor con 
     * información relativa a los procesos gestionados por el Servidor.
     */
    protected TextArea taSer;
    
    /**
     * Variable entera usada para almacenar el Puerto de Conexión a la escucha.
     */
    protected static final int Puerto = 1900;
    
    /**
     * Variable de tipo cadena usada para almacenar el HOST de Conexión.
     */
    protected static final String HOST = "localhost";
    
    /**
     * Variable de tipo cadena usada para almacenar la Ruta del equipo 
     * Servidor donde estan almacenados los ficheros que mandaremos al Cliente.
     */
    protected final String ruta = "src/recursos/";
    
    /**
     * Genera y ejecuta una instancia de la aplicación Servidor creando una 
     * Interfaz Gráfica para facilitar al usuario la Gestión de la misma.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main( String[] args ) {
        Servidor servidor = new Servidor();
    }
    
    /**
     * Constructor de la Interfaz Gráfica implementada para el Servidor.
     * Genera e inicializa la Interfaz y los elementos utilizados
     * para visualizar de forma interactiva la ejecución de la aplicación.
     */
    public Servidor() {
        JPanel panel = new JPanel(null);                                        // Creamos un panel para dibujar la interfaz gráfica
        JLabel lb1 = new JLabel("Servidor");                                    // Agregamos etiquetas, botones, y demás elementos a la Interfaz
        taSer = new TextArea();
        lb1.setBounds(90, 15, 80, 20);                                          // Los posicionamos en el panel
        taSer.setBounds(25, 50, 190, 150);
        lb1.setForeground(Color.red);
        panel.add(lb1);                                                         // Y los agregamos al mismo
        panel.add(taSer);
        taSer.setEditable(false);                                               // Imposibilitamos la edición del Text Area

        JFrame frame = new JFrame("Servidor PSP");                              // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                Servidor.class.getResource("recursos/server.png")));            // Le ponemos una imágen de icono a la ventana
        frame.setSize(245, 260);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        arrancarServidor();                                                     // Arrancamos el Servidor por defecto al lanzar la aplicación
    }
    
    /**
     * Método usado para inicializar las variables necesarias para arrancar 
     * el Servidor y mantenerlo operativo en bucle capturando eventualidades.
     */
    private void arrancarServidor() {
        try {
            ServerSocket skServidor = new ServerSocket(Puerto);                 // Iniciamos la escucha del servidor en el puerto preestablecido
            taSer.setText("Escuchando puerto "+Puerto+"\n");
            while (true) {                                                      // Repetimos indefinidamente la escucha de peticiones hasta que cerremos la Interfaz
                try (Socket sCliente = skServidor.accept()) {                   // Esperamos a que se conecte un cliente y creamos un nuevo socket para el mismo
                    InputStream is = sCliente.getInputStream();                 // Obtenemos el flujo de entrada mandado por el cliente
                    DataInputStream flujo_entrada = new DataInputStream(is);
                    String nombre = flujo_entrada.readUTF();                    // Y lo almacenamos para usarlo posteriormente
                    
                    OutputStream os = sCliente.getOutputStream();
                    try (DataOutputStream dos = new DataOutputStream(os)) {
                        if (new File(ruta + nombre).isFile()) {                 // Comprobamos si el fichero existe
                            File f = new File(ruta + nombre);
                            byte[] ba = new byte[(int) f.length()];
                            FileInputStream fis = new FileInputStream(f);
                            BufferedInputStream bis =
                                    new BufferedInputStream(fis);
                            DataInputStream dis = new DataInputStream(bis);
                            dis.readFully(ba, 0, ba.length);
                            dos.writeUTF(f.getName());                          // Mandamos al cliente el nombre del fichero
                            dos.writeLong(ba.length);                           // Mandamos al cliente el tamaño del fichero
                            dos.write(ba, 0, ba.length);                        // Mandamos al cliente el fichero
                            taSer.setText(taSer.getText() +
                                    "Procesando: " + nombre + "\n");
                        } else {                                                // Si no existe el fichero mandamos un error al cliente
                            dos.writeUTF("error");
                            taSer.setText(taSer.getText() +
                                    "No existe : " + nombre + "\n");
                        }
                        dos.flush();
                        dos.close();                                            // Cerramos flujos
                    }                    
                    sCliente.close();                                           // Cerramos Socket
                } catch(Exception e) {
                    taSer.setText(e.getMessage());                              // Capturamos cualquier excepción y la mostramos dado el caso
                }
            }
        } catch(IOException e) {
            if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                taSer.setText("El Servidor ya está arrancado");
            } else {
                taSer.setText(e.getMessage());
            }
        }
    }
}