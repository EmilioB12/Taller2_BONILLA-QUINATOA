import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaPaqueteria {
    private JPanel Ventana;
    private JTabbedPane tabbedPane1;
    private JSpinner spinnerTracking;
    private JComboBox comboRecepcion;
    private JComboBox comboEntrega;
    private JTextField textCedula;
    private JButton agregarButton;
    private JTextField textPeso;
    private JButton totalPesoButton;
    private JButton totalPaquetesButton;
    private JComboBox comboBusquedaCiudad;
    private JButton totalPaquetesCiudadButton;
    private JList list1;
    private JButton modificarButton;
    private JTextField textnumTrack;
    private JButton modificarButton1;
    private JButton paquetesPorEstadoButton;
    private JComboBox comboEstado;
    private JButton ordenarPorBurbujaButton;
    private JButton ordenarPesoPorInsercionButton;
    private JList list2;
    private JList list3;
    private JList list4;
    private JTextField textBuscarBinario;
    private JButton buscarPaquetePorTrackingButton;
    private JList list5;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JList list6;
    private JButton listarBusquedaButton;

    private Lista paquetes = new Lista();

    public VentanaPaqueteria(){
        quemarDatos();
        llenarJlist();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    paquetes.adicionarElementos(new Paqueteria(Integer.parseInt(spinnerTracking.getValue().toString()),Double.parseDouble(textPeso.getText()), comboRecepcion.getSelectedItem().toString(), comboEntrega.getSelectedItem().toString(), textCedula.getText()));
                    JOptionPane.showMessageDialog(null, "Paquete agregado:");
                    System.out.println(paquetes.listarPaquetes());
                    limpiarDatos();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                llenarJlistGeneral(list1);
            }
        });
        totalPesoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, paquetes.sumarTotalPaquetes());
            }
        });

        totalPaquetesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, paquetes.sumarTotalPeso());
            }
        });
        totalPaquetesCiudadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, paquetes.sumarTotalPesoCiudad(comboBusquedaCiudad.getSelectedItem().toString()));
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list1.getSelectedIndex()!=-1){
                    int indice = list1.getSelectedIndex();
                    Paqueteria pa = paquetes.getServiEntrega().get(indice);
                    spinnerTracking.setValue(pa.getTracking());
                    textPeso.setText(String.valueOf(pa.getPeso()));
                    comboRecepcion.setSelectedItem(pa.getCiudadEntrega());
                    comboEntrega.setSelectedItem(pa.getCiudadRecepcion());
                    textCedula.setText(pa.getCedulaReceptor());
                }
            }
        });
        modificarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int numTracking = Integer.parseInt(textnumTrack.getText());
                    paquetes.modificar(numTracking);
                    llenarJlist();
            }
        });
        paquetesPorEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, paquetes.sumarTotalEstado(comboEstado.getSelectedItem().toString()));
            }
        });
        ordenarPorBurbujaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quemarDatos();
                llenarJlistGeneral(list2);
                ordenarBurbuja(paquetes.getServiEntrega());
                llenarJlistGeneral(list3);
            }
        });
        ordenarPesoPorInsercionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quemarDatos();
                llenarJlistGeneral(list2);
                ordenarPesoInsercion(paquetes.getServiEntrega());
                llenarJlistGeneral(list4);
            }
        });
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    Paqueteria paqueteSeleccionado = (Paqueteria) list1.getSelectedValue();
                    int numeroTracking = paqueteSeleccionado.getTracking();
                    double nuevoPeso = Double.parseDouble(textPeso.getText());
                    String nuevaCiudadEntrega = comboEntrega.getSelectedItem().toString();
                    String nuevaCiudadRecepcion = comboRecepcion.getSelectedItem().toString();
                    String nuevaCedulaReceptor = textCedula.getText();
                    try {
                        paquetes.editar(numeroTracking, nuevoPeso, nuevaCiudadEntrega, nuevaCiudadRecepcion, nuevaCedulaReceptor);
                        String lista = paquetes.listarPaquetes();
                        llenarJlistGeneral(list1);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        buscarPaquetePorTrackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int busquedaTracking = Integer.parseInt(textBuscarBinario.getText());
                int num = buscarBinarioPaquete(paquetes.getServiEntrega(), busquedaTracking);
                JOptionPane.showMessageDialog(null, "El paquete es "+num);
            }
        });
        listarBusquedaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quemarDatos();
                for(Paqueteria p:paquetes.getServiEntrega()) {
                    paquetes.listarBusqueda(textField1.getText(), comboBox1.getSelectedItem().toString());
                    llenarJlistGeneral(list6);
                }
            }
        });

    }
    public void limpiarDatos(){
        spinnerTracking.setValue(0);
        textCedula.setText("");
        comboRecepcion.setSelectedIndex(0);
        comboEntrega.setSelectedIndex(0);
        textPeso.setText("");
    }
    public void quemarDatos(){
        try {
            paquetes.adicionarElementos(new Paqueteria(1, 120, "Quito", "Guayaquil", "123"));
            paquetes.adicionarElementos(new Paqueteria(2, 70, "Guayaquil", "Cuenca", "124"));
            paquetes.adicionarElementos(new Paqueteria(3, 80, "Riobamba", "Guayaquil", "125"));
        }catch(Exception ex1){

        }
    }
    public void llenarJlist(){
        DefaultListModel dl = new DefaultListModel<>();
        for(Paqueteria p:paquetes.getServiEntrega()){
            dl.addElement(p);
        }
        list1.setModel(dl);
    }
    public void llenarJlistGeneral(JList listMostrar){
        DefaultListModel dl = new DefaultListModel<>();
        for(Paqueteria p:paquetes.getServiEntrega()){
            dl.addElement(p);
        }
        listMostrar.setModel(dl);
    }
    public void ordenarBurbuja(List<Paqueteria> listaPaquetes){
        int i, j;
        Paqueteria aux;
        for (i = 0;i<listaPaquetes.size()-1;i++) {
            for (j = 0; j < listaPaquetes.size() - i - 1; j++) {
                if (listaPaquetes.get(j + 1).getTracking() < listaPaquetes.get(j).getTracking()) {
                    aux = listaPaquetes.get(j+1);
                    listaPaquetes.set(j+1, listaPaquetes.get(j));
                    listaPaquetes.set(j, aux);
                }
            }
        }
    }
    public void ordenarPesoInsercion(List<Paqueteria> listaPaquetes){
        //crear lista auxiliar para ordenar
        //List<Paqueteria> listaAux =new ArrayList<>();
        int i, j;
        double aux = 0;
        Paqueteria auxP;
        for (i = 0;i<listaPaquetes.size();i++) {
            auxP = listaPaquetes.get(i);
            j = i-1;
            while ((j>=0) && (listaPaquetes.get(j).getPeso() > auxP.getPeso())){
                listaPaquetes.set(j+1, listaPaquetes.get(j));
                j--;
            }
            listaPaquetes.set(j+1, auxP);
        }
    }
    public void listaBusquedaParam(List<Paqueteria> listaPaquetes){
        for(Paqueteria p: listaPaquetes){

        }
    }
    public int buscarBinarioPaquete(List<Paqueteria> listaPaquetes, int trackingBusqueda){
        List<Paqueteria> listaBusqueda = new ArrayList<>();
        ordenarBurbuja(paquetes.getServiEntrega());
        int inicio = 0;
        int fin = paquetes.getServiEntrega().size()-1;
        int medio;
        int resultado = -1;
        while(inicio <= fin){
            medio=(inicio+fin)/2;
            if(paquetes.getServiEntrega().get(medio).getTracking()==trackingBusqueda){
                resultado=medio;
                fin=inicio-1;
            }else if(paquetes.getServiEntrega().get(medio).getTracking()<trackingBusqueda){
                inicio=medio+1;
            }else{
                fin=medio-1;
            }
        }
        return resultado;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPaqueteria");
        frame.setContentPane(new VentanaPaqueteria().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
