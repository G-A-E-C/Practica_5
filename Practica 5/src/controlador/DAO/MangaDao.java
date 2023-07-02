package controlador.DAO;

import controlador.ed.lista.ListaEnlazada;
import controlador.ed.lista.exception.EmptyException;
import controlador.ed.lista.exception.PositionException;
import java.io.IOException;
import modelo.Manga;

public class MangaDao extends AdapDao<Manga>{
    private Manga manga;
    
    public MangaDao() {
        super(Manga.class);
    }

    public Manga getManga() {
        if(manga == null) {
            manga = new Manga();
        }
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }
    
    public void guardarManga() throws IOException {
        manga.setId(generarID());
        
        guardar(manga);
        
        manga = null;
    }
    
    public ListaEnlazada<Manga> buscarAutorBinaria(int autor) throws EmptyException, PositionException {
        ListaEnlazada<Manga> aux = listar();
        
        ListaEnlazada<Manga> resultado = new ListaEnlazada<>();

        Manga[] matriz = aux.toArray();

        ordenarAutor(matriz, 0, matriz.length - 1);

        int pos = buscarMangaAutor(matriz, autor);
        
        if(pos == -1) {
            throw new NullPointerException("Manga no encontrada");
        }
        
        aux.toList(matriz);
        
        resultado.insertar(aux.get(pos));
        
        return resultado;
    }
    
    public ListaEnlazada<Manga> buscarAutorLineal(int autor) {
        ListaEnlazada<Manga> aux = listar();
        
        ListaEnlazada<Manga> resultado = new ListaEnlazada<>();

        Manga[] matriz = aux.toArray();

        ordenarAutor(matriz, 0, matriz.length - 1);

        for(Manga m : matriz) {
            
            if(m.getId_autor() == autor) {
                resultado.insertar(m);
            }
        }
        
        return resultado;
    }
    
    private int buscarMangaAutor(Manga[] matriz, int autor) {
        
        int l = 0, r = matriz.length - 1;
        
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (matriz[m].getId_autor() == autor) {
                return m;
            }

            if (matriz[m].getId_autor() < autor) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }
    
    public void ordenarAutor(Manga arr[], int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;

            ordenarTitulo(arr, l, m);
            ordenarTitulo(arr, m + 1, r);

            mergeAutor(arr, l, m, r);
        }
    }
    
    private void mergeAutor(Manga arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Manga L[] = new Manga[n1];
        Manga R[] = new Manga[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getId_autor() <= R[j].getId_autor()) {
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
    
    public ListaEnlazada<Manga> buscarTituloBinaria(String titulo) throws EmptyException, PositionException {
        
        ListaEnlazada<Manga> aux = listar();
        
        ListaEnlazada<Manga> resultado = new ListaEnlazada<>();

        Manga[] matriz = aux.toArray();

        ordenarTitulo(matriz, 0, matriz.length - 1);

        int pos = buscarMangaTitulo(matriz, titulo);
        
        if(pos == -1) {
            throw new NullPointerException("Manga no encontrada");
        }
        
        aux.toList(matriz);
        
        resultado.insertar(aux.get(pos));
        
        return resultado;
    }
    
    public ListaEnlazada<Manga> buscarTituloLineal(String titulo) {
        
        ListaEnlazada<Manga> aux = listar();
        
        ListaEnlazada<Manga> resultado = new ListaEnlazada<>();

        Manga[] matriz = aux.toArray();

        ordenarTitulo(matriz, 0, matriz.length - 1);
        
        for(Manga m : matriz) {
            
            if(m.getTitulo().startsWith(titulo)) {
                resultado.insertar(m);
            }
        }
        
        return resultado;
    }
        
    
    public ListaEnlazada<Manga> buscarNumPagBinaria(int pagina) throws EmptyException, PositionException {
        
        ListaEnlazada<Manga> aux = listar();
        
        ListaEnlazada<Manga> resultado = new ListaEnlazada<>();

        Manga[] matriz = aux.toArray();

        ordenarTitulo(matriz, 0, matriz.length - 1);

        int pos = buscarMangaNumPaginas(matriz, pagina);
        
        if(pos == -1) {
            throw new NullPointerException("Manga no encontrada");
        }
        
        aux.toList(matriz);
        
        resultado.insertar(aux.get(pos));
        
        return resultado;
    }
    
    
    public ListaEnlazada<Manga> buscarNumPagLineal(int pagina) {
        
        ListaEnlazada<Manga> aux = listar();
        
        ListaEnlazada<Manga> resultado = new ListaEnlazada<>();

        Manga[] matriz = aux.toArray();

        ordenarTitulo(matriz, 0, matriz.length - 1);
        
        for(Manga m : matriz) {
            
            if(m.getNumPaginas() == pagina) {
                resultado.insertar(m);
            }
        }
        
        return resultado;
    }
    
    private int buscarMangaTitulo(Manga[] matriz, String nombre) {
        
        int l = 0, r = matriz.length - 1;
        
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (matriz[m].getTitulo().equalsIgnoreCase(nombre)) {
                return m;
            }

            if (matriz[m].getTitulo().compareToIgnoreCase(nombre) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }
    
    private int buscarMangaNumPaginas(Manga[] matriz, int paginas) {
        
        int l = 0, r = matriz.length - 1;
        
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (matriz[m].getNumPaginas() == paginas) {
                return m;
            }

            if (matriz[m].getNumPaginas() < paginas) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }
    
    public void ordenarNumPaginas(Manga arr[], int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;

            ordenarTitulo(arr, l, m);
            ordenarTitulo(arr, m + 1, r);

            mergeNumPaginas(arr, l, m, r);
            
        }
    }
    
    private void mergeNumPaginas(Manga arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Manga L[] = new Manga[n1];
        Manga R[] = new Manga[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {

            if (L[i].getNumPaginas() <= R[j].getNumPaginas()) {
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
    
    public void ordenarTitulo(Manga arr[], int l, int r) {
        if (l < r) {

            int m = l + (r - l) / 2;

            ordenarTitulo(arr, l, m);
            ordenarTitulo(arr, m + 1, r);

            mergeTitulo(arr, l, m, r);
        }
    }
    
    private void mergeTitulo(Manga arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Manga L[] = new Manga[n1];
        Manga R[] = new Manga[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getTitulo().compareToIgnoreCase(R[j].getTitulo()) <= 0) {
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
}
