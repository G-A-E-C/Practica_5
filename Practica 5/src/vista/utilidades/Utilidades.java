package vista.utilidades;

import controlador.DAO.AutorDao;
import controlador.DAO.EditorialDao;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import javax.swing.JComboBox;
import modelo.Autor;
import modelo.Editorial;

public class Utilidades {
    
    public static void cargarEditorial(JComboBox cbx) throws EmptyException, PositionException{
        cbx.removeAllItems();
        ListaEnlazada<Editorial> editoriales = new EditorialDao().listar();
        
        for(int i = 0; i < editoriales.size() ; i++) {
            
            cbx.addItem(editoriales.get(i));
            
        }
     
    }
    
    public static void cargarAutor(JComboBox cbx) throws EmptyException, PositionException{
        cbx.removeAllItems();
        ListaEnlazada<Autor> autores = new AutorDao().listar();
        
        for(int i = 0; i < autores.size() ; i++) {
            
            cbx.addItem(autores.get(i));
            
        }
    }
    
    public static void cargarTipo(JComboBox cbx){
        cbx.removeAllItems();
        cbx.addItem("Binaria");
        cbx.addItem("Lineal");
    }
    
    public static void cargarAtributos(JComboBox cbx){
        cbx.removeAllItems();
        cbx.addItem("Titulo");
        cbx.addItem("Paginas");
        cbx.addItem("Autor");
    }
    
}
