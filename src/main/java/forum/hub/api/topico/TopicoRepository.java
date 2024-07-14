package forum.hub.api.topico;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> , JpaSpecificationExecutor<Topico> {

    default Page<Topico> findAllByStatusTrue(Pageable paginacao) {
        return findAll((root, query, builder) -> builder.equal(root.get("status"), true), paginacao);
    }

    default Page<Topico> findAllByNomeContainingAndDataCriacaoYear(String nome, Integer ano, Pageable paginacao) {
        return findAll((root, query, builder) -> {
            List<Predicate> condicoes = new ArrayList<>();

            if (nome != null && !nome.isBlank()) {
                condicoes.add(builder.like(root.get("titulo"), "%" + nome + "%"));
            }

            if (ano != null) {
                condicoes.add(builder.equal(builder.function("year", Integer.class, root.get("dataCriacao")), ano));
            }

            condicoes.add(builder.equal(root.get("status"), true));

            return builder.and(condicoes.toArray(new Predicate[0]));
        }, paginacao);
    }
}
