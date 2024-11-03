package up.ddm

import android.os.Parcel
import android.os.Parcelable

class Atributos (

    var forca: Int = 8,
    var destreza: Int = 8,
    var constituicao: Int = 8,
    var inteligencia: Int = 8,
    var sabedoria: Int = 8,
    var carisma: Int = 8
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(forca)
        parcel.writeInt(destreza)
        parcel.writeInt(constituicao)
        parcel.writeInt(inteligencia)
        parcel.writeInt(sabedoria)
        parcel.writeInt(carisma)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Atributos> {
        override fun createFromParcel(parcel: Parcel): Atributos {
            return Atributos(parcel)
        }

        override fun newArray(size: Int): Array<Atributos?> {
            return arrayOfNulls(size)
        }
    }
}
