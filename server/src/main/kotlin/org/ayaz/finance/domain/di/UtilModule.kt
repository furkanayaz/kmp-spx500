package org.ayaz.finance.domain.di

import org.ayaz.finance.domain.util.encryption.PasswordEncryption
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class UtilModule {

    @Single
    fun providePasswordEncryption(): PasswordEncryption = PasswordEncryption()

}