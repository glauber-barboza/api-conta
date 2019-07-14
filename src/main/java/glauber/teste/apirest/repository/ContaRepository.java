package glauber.teste.apirest.repository;

import glauber.teste.apirest.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {
}
