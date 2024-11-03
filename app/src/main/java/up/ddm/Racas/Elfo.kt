import Racas.iRaca
import up.ddm.Atributos

class Elfo : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            destreza += 2
        }
    }
}
