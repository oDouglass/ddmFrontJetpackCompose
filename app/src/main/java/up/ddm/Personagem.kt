package org.example

import Racas.iRaca
import up.ddm.Atributos

open class Personagem(val atributosIniciais: Atributos, raca: iRaca) {

    val atributos: Atributos
    var pontosDeVida: Int = 10
    var nome: String = ""

    init {
        // Aplica o aprimoramento da raça aos atributos iniciais
        atributos = raca.aplicarAprimoramento(atributosIniciais)
    }

}