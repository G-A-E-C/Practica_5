package controlador;

import controlador.DAO.AutorDao;
import controlador.DAO.EditorialDao;
import controlador.DAO.MangaDao;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Manga;

public class MangaControlador {
    private AutorDao autor = new AutorDao();
    private MangaDao manga = new MangaDao();
    private EditorialDao editorial = new EditorialDao();
    
    public ListaEnlazada<Manga> listar(){
        return manga.listar();
    }
    
    public void guardarAutor(String nombre) throws IOException{
        autor.getAutor().setAlias(nombre);
        autor.guardarAutor();
    }
    
    public void guardarEditorial(String nombre, String direccion) throws IOException {
        editorial.getEditorial().setNombre(nombre);
        editorial.getEditorial().setDireccion(direccion);
        
        editorial.guardarEditorial();
    }
    
    public void guardarManga(String titulo, int numPaginas, int tomo, String autor, String editorial) throws EmptyException, PositionException, IOException{
        manga.getManga().setTitulo(titulo);
        manga.getManga().setNumPaginas(numPaginas);
        manga.getManga().setTomo(tomo);
        manga.getManga().setId_autor(this.autor.buscarAutor(autor).getId());
        manga.getManga().setId_editorial(this.editorial.buscarEditorial(editorial).getId());
        
        manga.guardarManga();
        
    }
    
    public ListaEnlazada<Manga> buscarManga(String atributo, int tipo, Object elemento) throws EmptyException, PositionException{
        
        if(tipo == 0) {
            
            switch (atributo) {
                case "titulo": return manga.buscarTituloBinaria((String)elemento);
                case "paginas":return manga.buscarNumPagBinaria((int) elemento);
                case "autor": return manga.buscarAutorBinaria(this.autor.buscarAutor((String)elemento).getId());
            }
            
        } else {
            switch (atributo) {
                case "titulo": return manga.buscarTituloLineal((String)elemento);
                case "paginas":return manga.buscarNumPagLineal((int) elemento);
                case "autor": return manga.buscarAutorLineal(this.autor.buscarAutor((String) elemento).getId());
            }
            
        }
        
        return null;
    }
    
}
