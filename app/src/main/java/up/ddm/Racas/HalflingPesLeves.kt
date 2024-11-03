import Racas.iRaca
import up.ddm.Atributos

class HalflingPesLeves : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            carisma += 1
        }
    }
}
