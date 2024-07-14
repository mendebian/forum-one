package forum.hub.api.topico;

import forum.hub.api.curso.Curso;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTopicos(

        @Id
        Long id,

        @NotNull
        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,


        @NotBlank
        String dataCriacao,

        @NotNull
        @NotBlank
        String autor,

        @NotNull
        @Valid
        Curso curso){

}
