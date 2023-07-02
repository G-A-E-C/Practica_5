package controlador.DAO;

import controlador.ed.lista.ListaEnlazada;
import java.io.IOException;

public interface InterfaceDao<T> {
    
    public void guardar(T obj) throws IOException;
    public void actualizar(T obj, Integer pos) throws IOException;
    public ListaEnlazada<T> listar();
    public T obtener(Integer id);
    
}
