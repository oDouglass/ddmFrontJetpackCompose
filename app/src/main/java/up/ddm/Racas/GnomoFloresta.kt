import Racas.iRaca
import up.ddm.Atributos

class GnomoFloresta : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            destreza += 1
        }
    }
}
