import Racas.iRaca
import up.ddm.Atributos

class Anao : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            constituicao += 2
        }
    }
}
