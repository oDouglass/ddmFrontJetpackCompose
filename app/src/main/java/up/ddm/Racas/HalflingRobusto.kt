import Racas.iRaca
import up.ddm.Atributos

class HalflingRobusto : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            constituicao += 1
        }
    }
}
