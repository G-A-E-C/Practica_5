package vista.modelotabla;

import controlador.DAO.AutorDao;
import controlador.DAO.EditorialDao;
import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import modelo.Manga;

public class MangaModeloTabla extends AbstractTableModel {

    private ListaEnlazada<Manga> manga;

    public ListaEnlazada<Manga> getManga() {
        return manga;
    }

    public void setManga(ListaEnlazada<Manga> manga) {
        this.manga = manga;
    }

    @Override
    public int getRowCount() {
        return manga.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Manga m = manga.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return m.getTitulo();
                case 1:
                    return m.getNumPaginas();
                case 2:
                    return m.getTomo();
                case 3:
                    return new AutorDao().buscarAutor(m.getId_autor()).getAlias();
                case 4:
                    return new EditorialDao().buscarEditorial(m.getId_editorial()).getNombre();
            }
        } catch (EmptyException | PositionException ex) {
            Logger.getLogger(MangaModeloTabla.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Titulo";
            case 1:
                return "Paginas";
            case 2:
                return "Tomo";
            case 3:
                return "Autor";
            case 4:
                return "Editorial";
        }
        
        return null;
    }

}
