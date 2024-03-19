package br.com.masterclass.superpecas.model.DTO;

public class PecaDTO {

//    @JsonIgnore
    private Long pecaID;

    private String nome;

    private String descricao;

    private String numeroSerie;

    private String fabricante;

    private String modeloCarro;

    private Carro carro;

    //    @JsonProperty(access = JsonProperty.Access.READY_ONLY)
    public Long getPecaID() { return pecaID; }

    public void setPecaID(Long pecaID) { this.pecaID = pecaID; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getFabricante(){
        return fabricante;
    }

    public void setFabricante(String fabricante){
        this.fabricante = fabricante;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro){
        this.modeloCarro = modeloCarro;
    }


    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public static class Carro {
        private String nomeModelo;

        private String fabricante;
        private Long carroID;

        public String getNomeModelo() {
            return nomeModelo;
        }

        public void setNomeModelo(String nomeModelo) {
            this.nomeModelo = nomeModelo;
        }

        public String getFabricante() {
            return fabricante;
        }

        public void setFabricante(String fabricante) {
            this.fabricante = fabricante;
        }

        public Long getCarroId() {
            return carroID;
        }

        public void setCarroId(Long carroID) {
            this.carroID = carroID;
        }
    }
}
