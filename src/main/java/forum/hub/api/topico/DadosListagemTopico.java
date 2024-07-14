package forum.hub.api.topico;

public record DadosListagemTopico(Long id, String titulo, String mensagem, java.time.LocalDateTime dataCriacao, String autor) {
    public DadosListagemTopico (Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getAutor());
    }

}
