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
        a.setNacionalidad("Mexicano");
        dao.insert(a);

        Autor r = new Autor();
        r.setNombre("Ruben Dario");
        r.setNacionalidad("Nicaraguense");
        dao.insert(r);
    }

    public static void listarAutores() {
        System.out.println("\n== Autores ==");
        List<Autor> autores = dao.getAll("autores.All", Autor.class);
        autores.forEach(autor ->
                System.out.println(autor.getId() + " - " + autor.getNombre() +
                        " (" + autor.getNacionalidad() + ")"));
    }

    public static void editarAutor() {
        Autor a = dao.findById(1, Autor.class); // si tu @Id fuera Long, cambia a 1L y la firma de findById
        if (a != null) {
            a.setNacionalidad("Colombiana");
            dao.update(a);
        } else {
            System.out.println("Autor ID=1 no encontrado.");
        }
    }

    public static void eliminarAutor() {
        Autor a = dao.findById(2, Autor.class);
        if (a != null) {
            dao.delete(a);
        } else {
            System.out.println("Autor ID=2 no encontrado.");
        }
    }

    public static void agregarCategoria() {
        Categoria c1 = new Categoria();
        c1.setNombre("Ciencia");
        dao.insert(c1);

        Categoria c2 = new Categoria();
        c2.setNombre("Novela");
        dao.insert(c2);
    }

    public static void listarCategorias() {
        System.out.println("\n== Categorías ==");
        List<Categoria> categorias = dao.getAll("categorias.All", Categoria.class);
        categorias.forEach(c ->
                System.out.println(c.getId() + " - " + c.getNombre()));
    }

    public static void agregarLibro() {
        // Usamos Autor ID=1 y Categoria ID=1 como ejemplo
        Autor autor = dao.findById(1, Autor.class);
        Categoria categoria = dao.findById(1, Categoria.class);

        if (autor == null || categoria == null) {
            System.out.println("Inserta primero un Autor (ID=1) y una Categoria (ID=1).");
            return;
        }

        Libro l = new Libro();
        l.setTitulo("Cien anios de soledad"); // evita 'ñ' en nombres de campos Java
        l.setAnioPub(1967);                    // tu campo se llama 'añoPub' en la entidad
        l.setAutor(autor);
        l.setCategoria(categoria);

        dao.insert(l);
    }

    public static void listarLibros() {
        System.out.println("\n== Libros ==");
        List<Libro> libros = dao.getAll("libros", Libro.class); // ¡ojo! el NamedQuery es "libros"
        libros.forEach(li -> System.out.println(
                li.getId() + " - " + li.getTitulo() + " (" + li.getAnioPub() + ")" +
                        " | AutorID=" + (li.getAutor() != null ? li.getAutor().getId() : null) +
                        " | CategoriaID=" + (li.getCategoria() != null ? li.getCategoria().getId() : null)
        ));
    }

    // =================== MAIN ===================
    public static void main(String[] args) {
        // Autor
        insertarAutor();
        listarAutores();
        editarAutor();
        listarAutores();
        eliminarAutor();
        listarAutores();

        // Categoría
        agregarCategoria();
        listarCategorias();

        // Libro
        agregarLibro();
        listarLibros();
    }
}