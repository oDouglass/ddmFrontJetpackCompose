import Racas.iRaca
import up.ddm.Atributos

class AnaoColina : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            sabedoria += 1
        }
    }
}