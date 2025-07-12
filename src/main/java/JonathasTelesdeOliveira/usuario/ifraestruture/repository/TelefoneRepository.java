package JonathasTelesdeOliveira.usuario.ifraestruture.repository;


import JonathasTelesdeOliveira.usuario.ifraestruture.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
