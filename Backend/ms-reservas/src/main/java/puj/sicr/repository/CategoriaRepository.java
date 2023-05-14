package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.Categoria;

import java.util.List;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("SELECT c FROM Categoria	c ")
    List<Categoria> getByPage(Pageable pageable);
}
