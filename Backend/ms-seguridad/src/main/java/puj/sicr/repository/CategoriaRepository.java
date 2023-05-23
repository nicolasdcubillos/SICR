package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Categoria;

import java.util.List;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query("SELECT c FROM Categoria	c ")
    List<Categoria> getByPage(Pageable pageable);
}
