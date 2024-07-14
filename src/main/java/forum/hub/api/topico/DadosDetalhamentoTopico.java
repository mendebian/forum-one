package forum.hub.api.topico;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, java.time.LocalDateTime dataCriacao, String autor ) {
    public DadosDetalhamentoTopico (Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getAutor());
    }
}
