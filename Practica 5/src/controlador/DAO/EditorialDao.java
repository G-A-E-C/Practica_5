package controlador.DAO;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Editorial;

public class EditorialDao extends AdapDao<Editorial> {

    private Editorial editorial;

    public EditorialDao() {
        super(Editorial.class);
    }

    public void guardarEditorial() throws IOException {
        editorial.setId(generarID());

        guardar(editorial);

        editorial = null;
    }

    public Editorial buscarEditorial(String nombre) throws EmptyException, PositionException {
        ListaEnlazada<Editorial> aux = listar();

        Editorial[] matriz = aux.toArray();

        ordenarEditorialNombre(matriz, 0, matriz.length - 1);

        int pos = buscarEditorialNombre(matriz, nombre);

        if (pos == -1) {
            throw new NullPointerException("Editorial no encontrada");
        }

        aux.toList(matriz);

        return aux.get(pos);
    }

    public Editorial buscarEditorial(int id) {
        ListaEnlazada<Editorial> aux = listar();

        Editorial[] matriz = aux.toArray();
        
        for(Editorial e : matriz) {
            
            if(e.getId() == id) {
                return e;
            }
        }
        
        return null;
    }

    private int buscarEditorialNombre(Editorial[] matriz, String nombre) {

        int l = 0, r = matriz.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (matriz[m].getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }

            if (matriz[m].getNombre().compareToIgnoreCase(nombre) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    private void merge(Editorial arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Editorial L[] = new Editorial[n1];
        Editorial R[] = new Editorial[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getNombre().compareToIgnoreCase(R[j].getNombre()) <= 0) {
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

    private void ordenarEditorialNombre(Editorial arr[], int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;

            ordenarEditorialNombre(arr, l, m);
            ordenarEditorialNombre(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    public Editorial getEditorial() {
        if (editorial == null) {
            editorial = new Editorial();
        }
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

}
