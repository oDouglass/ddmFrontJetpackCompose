import Racas.iRaca
import up.ddm.Atributos

class Drow : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            carisma += 1
        }
    }
}
