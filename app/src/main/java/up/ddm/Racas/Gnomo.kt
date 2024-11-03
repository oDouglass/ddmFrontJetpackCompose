import Racas.iRaca
import up.ddm.Atributos

class Gnomo : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            inteligencia += 2
        }
    }
}
