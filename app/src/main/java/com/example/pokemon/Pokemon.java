package com.example.pokemon;

import java.util.Locale;

public class Pokemon {
    String nome;
    String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Pokemon(String nome, String imgUrl) {
        this.nome = nome;
        this.imgUrl = imgUrl;
    }

    public String getNome() {
        return nome.toUpperCase(Locale.ROOT);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
