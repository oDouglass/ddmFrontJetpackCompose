import Racas.iRaca
import up.ddm.Atributos

class AltoElfo : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            inteligencia += 1
        }
    }
}