import Racas.iRaca
import up.ddm.Atributos

class Halfling : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            destreza += 2
        }
    }
}
