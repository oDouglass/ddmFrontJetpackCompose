import Racas.iRaca
import up.ddm.Atributos

class Tiefling : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            inteligencia += 1
            carisma += 2
        }
    }
}
