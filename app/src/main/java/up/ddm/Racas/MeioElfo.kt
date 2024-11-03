import Racas.iRaca
import up.ddm.Atributos

class MeioElfo : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            carisma += 2
        }
    }
}
