import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Lista {

    private List<Paqueteria> serviEntrega ;

    public List<Paqueteria> getServiEntrega() {
        return serviEntrega;
    }

    public void setServiEntrega(List<Paqueteria> serviEntrega) {
        this.serviEntrega = serviEntrega;
    }

    public Lista( ){
         serviEntrega =new ArrayList<Paqueteria>();
    }

    public void adicionarElementos(Paqueteria p) throws Exception{
        if(serviEntrega.isEmpty())
            serviEntrega.add(p);
         else{
            for(Paqueteria p1: serviEntrega)
                if(p1.getTracking()== p.getTracking())
                    throw new Exception("Ya existe el paquete");
            serviEntrega.add(p);
        }
    }

    public String listarPaquetes(){
        String mensaje ="";
        for(Paqueteria p: serviEntrega)
            mensaje += p +"\n";
        return mensaje;
    }
    public String listarBusqueda(String cedula, String estado){
        String mensaje ="";
        for(Paqueteria p: serviEntrega)
            if(p.getCedulaReceptor().equals(cedula) && p.getEstado().equals(estado)) {
                mensaje += p + "\n";
            }

        return mensaje;
    }
    public Paqueteria buscarPaquete(int tracking){
        for(Paqueteria p:serviEntrega){
            if(p.getTracking()==tracking)
                return p;
        }
        return null;
    }
    public void modificar(int tracking){
        for(Paqueteria pa:serviEntrega) {
            if(pa.getTracking()==tracking) {
                if (pa.getEstado().equals("Receptado")) {
                    pa.setEstado("Enviado");
                    JOptionPane.showMessageDialog(null, "Paquete "+pa.getEstado());
                    return;
                }else{
                    pa.setEstado("Entregado");
                    JOptionPane.showMessageDialog(null, "Paquete "+pa.getEstado());
                    return;
                }
            }
        }
    }
    //cambio: añado metodo para modificar datos
    public void editar(int tracking, double nuevoPeso, String nuevaCiudadEntrega, String nuevaCiudadRecepcion, String nuevaCedulaReceptor) {
        Paqueteria paquete = buscarPaquete(tracking);
        if (buscarPaquete(tracking) != null) {
            paquete.setPeso(nuevoPeso);
            paquete.setCiudadEntrega(nuevaCiudadEntrega);
            paquete.setCiudadRecepcion(nuevaCiudadRecepcion);
            paquete.setCedulaReceptor(nuevaCedulaReceptor);
        } else {
            throw new IllegalArgumentException("No se encontró ningún empleado con la cédula especificada: " + tracking);
        }
    }


    public int sumarTotalPaquetes (){
     return totalPaquetes(0);

    }

    private int totalPaquetes (int indice){
        if(serviEntrega.size()==indice)
            return 0;
        else{
            return 1+totalPaquetes(indice+1);
        }

    }

    public double sumarTotalPeso(){
        return totalPeso(0);
    }

    private double totalPeso(int indice) {

        if(serviEntrega.size()==indice)
            return 0;
        else{
            return  serviEntrega.get(indice).getPeso()+totalPeso(indice+ 1);
        }
    }

    public double sumarTotalPesoCiudad(String ciudad){
        return totalPesoCiudad(0,ciudad);
    }

    private double totalPesoCiudad(int indice, String ciudad) {

        if(serviEntrega.size()==indice)
            return 0;
        else{
            if(serviEntrega.get(indice).getCiudadRecepcion().equals(ciudad)) {
                return serviEntrega.get(indice).getPeso() + totalPesoCiudad(indice + 1,ciudad);
            }else{
                return totalPesoCiudad(indice + 1,ciudad);
            }
        }
    }
    public double sumarTotalEstado(String estado){
        return totalEstado(0,estado);
    }

    private double totalEstado(int indice, String estado) {
        if(serviEntrega.size()==indice)
            return 0;
        else{
            if(serviEntrega.get(indice).getEstado().equals(estado)) {
                return 1+totalEstado(indice + 1,estado);
            }else{
                return totalEstado(indice + 1,estado);
            }
        }
    }
}



