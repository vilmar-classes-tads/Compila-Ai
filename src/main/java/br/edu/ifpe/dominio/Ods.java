package br.edu.ifpe.dominio;

public enum Ods {
    ODS_1("Erradicação da pobreza"),
    ODS_2("Fome zero e agricultura sustentável"),
    ODS_3("Saúde e bem-estar"),
    ODS_4("Educação de qualidade"),
    ODS_5("Igualdade de gênero"),
    ODS_6("Água potável e saneamento"),
    ODS_7("Energia limpa e acessível"),
    ODS_8("Trabalho decente e crescimento econômico"),
    ODS_9("Indústria, inovação e infraestrutura"),
    ODS_10("Redução das desigualdades"),
    ODS_11("Cidades e comunidades sustentáveis"),
    ODS_12("Consumo e produção responsáveis"),
    ODS_13("Ação contra a mudança global do clima"),
    ODS_14("Vida na água"),
    ODS_15("Vida terrestre"),
    ODS_16("Paz, justiça e instituições eficazes"),
    ODS_17("Parcerias e meios de implementação");

    private final String descricao;

    Ods(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
