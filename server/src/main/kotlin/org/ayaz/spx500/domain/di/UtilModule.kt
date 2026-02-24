package org.ayaz.spx500.domain.di

import org.ayaz.spx500.domain.util.encryption.PasswordEncryption
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class UtilModule {

    @Single
    fun providePasswordEncryption(): PasswordEncryption = PasswordEncryption()

}