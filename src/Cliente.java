import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Clase Cliente que extiende de la clase JFrame e implementa ActionListener. 
 * Se encarga de generar la Interfaz que usaremos para implementar los métodos
 * de control usados para el Cliente de la aplicación.
 *
 * @author Odei
 * @version 21.02.2016
 */
public class Cliente extends JFrame implements ActionListener {
    /**
     * Variable JButton usada para controlar las peticiones enviadas al Servidor.
     */
    protected JButton btnEn;
    
    /**
     * Variable TextField usada para capturar el texto enviado al Servidor.
     */
    protected TextField tfCad;
    
    /**
     * Variable TextArea usada para mostrar al usuario un contenedor con 
     * información relativa a los procesos gestionados desde el Cliente.
     */
    protected TextArea taCli;
    
    /**
     * Variable que comprueba la selección de Selección Manual de ficheros.
     */
    protected JRadioButton rbSM;
    
    /**
     * Variable que comprueba la selección de Selección Listado de ficheros.
     */
    protected JRadioButton rbSL;
    
    /**
     * Variable que comprueba la selección del JCheckBox para Texto.
     */
    protected JCheckBox cbTxt;
    
    /**
     * Variable que comprueba la selección del JCheckBox para Imagen.
     */
    protected JCheckBox cbImg;
    
    /**
     * Variable que comprueba la selección del JCheckBox para Pdf.
     */
    protected JCheckBox cbPdf;
    
    /**
     * Variable entera usada para almacenar el número de peticiones de 
     * ficheros enviadas al Servidor desde el Cliente.
     */
    protected int peticiones;
    
    /**
     * Variable panel usada para almacenar los elementos de la Interfaz.
     */
    protected JPanel panel;
    
    /**
     * Variable vector de cadenas usada para almacenar los nombres de los 
     * ficheros que el Cliente selecciona para pedírselos al Servidor.
     */
    protected String[] ficheros;
    
    /**
     * Variable de tipo cadena usada para almacenar la Ruta del equipo 
     * Cliente donde se almacenaran los ficheros mandados por el Servidor.
     */
    protected final String ruta = "src/recursos/tmp/";
    
    /**
     * Constructor de la Interfaz Gráfica implementada para el Cliente.
     * Genera e inicializa la Interfaz y los elementos utilizados
     * para visualizar de forma interactiva la ejecución de la aplicación.
     */
    public Cliente() {
        panel = new JPanel(null);                                               // Creamos un panel para dibujar la interfaz gráfica
        JLabel lb2 = new JLabel("Cliente");                                     // Agregamos etiquetas, botones, y demás elementos a la Interfaz
        ButtonGroup bgSel = new ButtonGroup();
        rbSM = new JRadioButton("Selección Manual");
        rbSL = new JRadioButton("Selección Listado");
        tfCad = new TextField();
        cbTxt = new JCheckBox("Notas.txt");                                     // Creamos elementos de los JCheckBox agregando la opción por defecto
        cbImg = new JCheckBox("Imagen.jpg");
        cbPdf = new JCheckBox("Fichero.pdf");
        btnEn = new JButton("Enviar");
        taCli = new TextArea("Conectando a puerto "+Servidor.Puerto+"\n");
        lb2.setBounds(185, 15, 80, 20);                                         // Los posicionamos en el panel
        rbSM.setBounds(30, 220, 160, 20);
        rbSL.setBounds(30, 270, 160, 20);
        tfCad.setBounds(190, 220, 100, 20);
        cbTxt.setBounds(190, 250, 80, 20);
        cbImg.setBounds(190, 270, 90, 20);
        cbPdf.setBounds(190, 290, 90, 20);
        btnEn.setBounds(300, 230, 90, 60);
        taCli.setBounds(30, 50, 360, 150);
        lb2.setForeground(Color.blue);
        bgSel.add(rbSM);
        bgSel.add(rbSL);                                                        // Agregamos JRadioButton a sus grupos correspondientes
        panel.add(lb2);
        panel.add(rbSM);                                                        // Y agregamos elementos al panel
        panel.add(rbSL);
        panel.add(tfCad);
        panel.add(cbTxt);
        panel.add(cbImg);
        panel.add(cbPdf);
        panel.add(btnEn);
        panel.add(taCli);
        rbSM.addActionListener((ActionListener) this);                          // Agregamos los JRadioButton al escuchador de eventos
        rbSL.addActionListener((ActionListener) this);
        btnEn.addActionListener((ActionListener) this);
        tfCad.setEnabled(false);
        cbTxt.setEnabled(false);
        cbImg.setEnabled(false);
        cbPdf.setEnabled(false);                                                // Deshabilitamos algunos botones para evitar irregularidades
        btnEn.setEnabled(false);
        taCli.setEditable(false);                                               // Imposibilitamos la edición del Text Area

        JFrame frame = new JFrame("Cliente PSP");                               // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                Cliente.class.getResource("recursos/client.png")));             // Le ponemos una imágen de icono a la ventana
        frame.setSize(420, 360);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
    /**
     * Genera y ejecuta una instancia de la aplicación Cliente creando una 
     * Interfaz Gráfica para facilitar al usuario la Gestión de la misma.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main( String[] args ) {
        Cliente cliente = new Cliente();
    }
    
   /**
     * Creamos un escuchador de eventos para capturar las opciones
     * utilizadas durante la ejecución de la aplicación.
     * 
     * @param evt ActionEvent: 
     */
    @Override
    public void actionPerformed(ActionEvent evt) {						
        switch (evt.getActionCommand()) {
            case "Selección Manual":                                            // Si seleccionamos la opción Selección Manual
                tfCad.setEnabled(true);                                         // Habilitamos y Deshabilitamos las opciones correspondientes
                cbTxt.setEnabled(false);
                cbImg.setEnabled(false);
                cbPdf.setEnabled(false);
                btnEn.setEnabled(true);
                break;
            case "Selección Listado":
                tfCad.setEnabled(false);
                cbTxt.setEnabled(true);
                cbImg.setEnabled(true);
                cbPdf.setEnabled(true);
                btnEn.setEnabled(true);
                break;
            case "Enviar":                                                      // Si pulsamos en el botón Enviar
                taCli.setText("");                                              // Limpiamos el Text Area
                if (rbSM.isSelected()) {
                    peticiones = 1;                                             // Establecemos el número de peticiones a realizar
                } else {
                    peticiones = 0;
                    obtenerSeleccion();                                         // Obtenemos los nombres de los ficheros seleccionados por el Cliente
                }
                arrancarCliente();                                              // Y las ejecutamos
                break;
            default:
        }
    }

    /**
     * Método usado para inicializar las variables necesarias para arrancar 
     * el Cliente y mantenerlo operativo tantas veces como peticiones existan.
     */
    private void arrancarCliente() {
        int n = 0;
        while (peticiones-- > 0) {                                              // Mientras existan peticiones
            try (Socket sCliente = new Socket(Servidor.HOST, Servidor.Puerto)) {// Nos conectamos al servidor por el puerto preestablecido
                String cad=rbSM.isSelected() ? tfCad.getText() : ficheros[n];   // Capturamos o generamos la cadena a mandar al servidor
                OutputStream os = sCliente.getOutputStream();
                DataOutputStream flujo_salida = new DataOutputStream(os);
                flujo_salida.writeUTF(cad);                                     // Y la mandamos mediante un flujo de salida hacia al servidor
                
                InputStream is = sCliente.getInputStream();  
                DataInputStream dis = new DataInputStream(is);                  // Creamos flujos para obtener los datos envidos por el servidor
                String destino = ruta + dis.readUTF();
                if (!destino.contains("error")) {                               // Si el nombre de fichero recibido no contiene la palabra error
                    try (FileOutputStream fos = new FileOutputStream(destino)) {// Creamos su flujo correspondiente a partir de la ruta generada para el mismo
                        int bytesLeidos;
                        long tam = dis.readLong();                              // Obtenemos el tamaño de fichero enviado
                        byte[] buf = new byte[1024];
                        while (tam > 0 && (bytesLeidos = dis.read(buf,0,        // Y reconstruimos el fichero byt a byte
                              (int)Math.min(buf.length, tam))) != -1) {
                            fos.write(buf, 0, bytesLeidos);
                            tam -= bytesLeidos;     
                        }
                        fos.close();
                        Desktop.getDesktop().open(new File(destino));           // Lanzamos el fichero para visualizarlo desde el s.o.
                    }
                    String c=(rbSM.isSelected()) ? tfCad.getText():ficheros[n]; // Si hemos seleccionado la opción manual obtenems el nombre del text field
                    taCli.setText(taCli.getText() + "Fichero creado en: " +     // en caso contrario del vector de nombres de ficheros seleccionados
                                                 ruta + c + "\n");
                } else {
                    taCli.setText(taCli.getText() + "No existe el fichero: " +
                                                   tfCad.getText() + "\n");
                }
                sCliente.close();                                               // Cerramos Socket
                n++;                                                            // Aumentamos contador para procesar siguiente fichero si existiese
            } catch(Exception e) {
                if (e.getMessage().equals("Connection refused: connect")) {     // Capturamos cualquier excepción y la mostramos dado el caso
                    taCli.setText("El Servidor no está arrancado");
                } else {
                    taCli.setText(e.getMessage());
                }
            }
        }
    }
    
    /**
     * Método usado para obtener un vector de cadenas con los nombres de los
     * ficheros seleccionados para enviarselos al Servidor.
     */
    private void obtenerSeleccion() {
        ficheros = new String[3];
        for (int i = 0; i < panel.getComponentCount(); i++) {                   // Recorremos todos los elementos del panel
            Component c = panel.getComponent(i);
            if (c.getClass().toString().contains("JCheckBox")) {                // Y si son del tipo JCheckBox
                JCheckBox cb = (JCheckBox) c;
                if (cb.isSelected()) {                                          // Comprobamos si estan seleccionados
                    ficheros[peticiones++] = cb.getText();                      // Y los añadimos al vector de ficheros aumentando el número de peticiones
                }
            }
        }
    }
}