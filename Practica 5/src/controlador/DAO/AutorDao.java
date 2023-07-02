package controlador.DAO;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Autor;

public class AutorDao extends AdapDao<Autor> {

    private Autor autor;

    public AutorDao() {
        super(Autor.class);
    }

    public Autor buscarAutor(String nombre) throws EmptyException, PositionException {

        ListaEnlazada<Autor> aux = listar();

        Autor[] matriz = aux.toArray();

        ordenarAutorAlias(matriz, 0, matriz.length - 1);

        int pos = buscarAutorAlias(matriz, nombre);
        
        if(pos == -1) {
            throw new NullPointerException("Autor no encontrado");
        }
        
        aux.toList(matriz);
        
        return aux.get(pos);
    }
    
    public Autor buscarAutor(int id) {
        ListaEnlazada<Autor> aux = listar();

        Autor[] matriz = aux.toArray();
        
        for(Autor a : matriz){
            
            if(a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    private int buscarAutorAlias(Autor[] matriz, String nombre) {
        
        int l = 0, r = matriz.length - 1;
        
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (matriz[m].getAlias().equalsIgnoreCase(nombre)) {
                return m;
            }

            if (matriz[m].getAlias().compareToIgnoreCase(nombre) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    public void guardarAutor() throws IOException {

        autor.setId(generarID());

        guardar(autor);

        autor = null;

    }

    private void merge(Autor arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Autor L[] = new Autor[n1];
        Autor R[] = new Autor[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getAlias().compareToIgnoreCase(R[j].getAlias()) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    private void ordenarAutorAlias(Autor arr[], int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;

            ordenarAutorAlias(arr, l, m);
            ordenarAutorAlias(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    public Autor getAutor() {
        if (autor == null) {
            autor = new Autor();
        }
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

}
