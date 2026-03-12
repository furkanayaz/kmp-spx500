package org.ayaz.spx500.data.uow_s.spx

fun interface IGetSpxDataUow {
    operator fun invoke()
}

class GetSpxDataUow: IGetSpxDataUow {
    override fun invoke() {

    }
}