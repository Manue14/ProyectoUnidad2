package org.example.proyectounidad2.Model;

import java.util.ArrayList;

public enum Categoria {
    LIENZO("Lienzo"),
    PAPEL("Papel"),
    TEJIDO("Tejido"),
    METAL("Metal"),
    ARCILLA_BARRO("Arcilla/Barro"),
    CERAMICA("Cerámica"),
    MADERA("Madera"),
    PORCELANA("Porcelana"),
    MARMOL("Mármol"),
    MURAL("Mural");

    private String valor;

    private Categoria(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public static Categoria getByValor(String value) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.getValor().trim().equalsIgnoreCase(value)) {
                return categoria;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return valor;
    }
    
    
}
