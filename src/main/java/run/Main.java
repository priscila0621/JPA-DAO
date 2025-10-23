package run;

import entities.Autor;
import entities.Categoria;
import entities.Libro;
import services.dao.MyDao;
import services.interfaces.ICRUD;

import java.util.List;

public class Main {

    public static final ICRUD dao = new MyDao();


    public static void insertarAutor() {
        Autor a = new Autor();
        a.setNombre("Gabriel Garcia Marquez");
        a.setNacionalidad("Mexicana");
        dao.insert(a);

        Autor r = new Autor();
        r.setNombre("Ruben Dario");
        r.setNacionalidad("Nicaraguense");
        dao.insert(r);
    }

    public static void listarAutores() {
        System.out.println("=== Autores ===");
        List<Autor> autores = dao.getAll("autores.All", Autor.class);
        autores.forEach(autor -> System.out.println(autor.getNombre()));
        System.out.println();
    }

    public static void editarAutor() {
        // Usa Integer porque tu ICRUD tiene Integer en findById
        Autor a = dao.findById(1, Autor.class);
        a.setNacionalidad("Colombiana");
        dao.update(a);
    }

    public static void eliminarAutor() {
        Autor a = dao.findById(2, Autor.class);
        dao.delete(a);
    }


    public static void insertarCategoria() {
        Categoria c1 = new Categoria();
        c1.setNombre("Poesia");
        dao.insert(c1);

        Categoria c2 = new Categoria();
        c2.setNombre("Novela");
        dao.insert(c2);
    }

    public static void listarCategorias() {
        System.out.println("=== Categorias ===");
        List<Categoria> categorias = dao.getAll("categorias.All", Categoria.class);
        categorias.forEach(cat -> System.out.println(cat.getNombre()));
        System.out.println();
    }


    public static void insertarLibro() {
        // Asegúrate de que existan estos IDs previamente
        Autor autor1 = dao.findById(1, Autor.class);       // Gabriel
        Autor autor2 = dao.findById(2, Autor.class);       // Ruben
        Categoria catPoesia = dao.findById(1, Categoria.class);
        Categoria catNovela = dao.findById(2, Categoria.class);

        Libro l1 = new Libro();
        l1.setTitulo("Cien años de soledad");
        // Si tu entidad tiene 'anioPub' usa setAnioPub; si tiene 'añoPub', usa setAñoPub
        // l1.setAnioPub(1967);
        l1.setAutor(autor1);
        l1.setCategoria(catNovela);
        dao.insert(l1);

        Libro l2 = new Libro();
        l2.setTitulo("Azul…");
        // l2.setAnioPub(1888);
        l2.setAutor(autor2);
        l2.setCategoria(catPoesia);
        dao.insert(l2);
    }

    public static void listarLibros() {
        System.out.println("=== Libros ===");
        List<Libro> libros = dao.getAll("libros", Libro.class);
        libros.forEach(l -> System.out.println(l.getTitulo()));
        System.out.println();
    }


    public static void main(String[] args) {
        insertarAutor();
        listarAutores();

        insertarCategoria();
        listarCategorias();

        insertarLibro();
        listarLibros();

        editarAutor();
        listarAutores();

        eliminarAutor();
        listarAutores();
    }
}
