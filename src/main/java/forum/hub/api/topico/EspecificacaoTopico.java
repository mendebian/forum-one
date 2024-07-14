package forum.hub.api.topico;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EspecificacaoTopico {
    public static Specification<Topico> byNome(String nome) {
        return (root, query, builder) -> builder.like(root.get("titulo"), "%" + nome + "%");
    }

    public static Specification<Topico> byAno(Integer ano) {
        return (root, query, builder) -> builder.equal(builder.function("year", Integer.class, root.get("dataCriacao")), ano);
    }
}
