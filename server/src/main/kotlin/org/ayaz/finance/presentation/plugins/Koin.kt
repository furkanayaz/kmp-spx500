package org.ayaz.finance.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.ayaz.finance.data.di.CacheModule
import org.ayaz.finance.data.di.DBModule
import org.ayaz.finance.data.di.RepoModule
import org.ayaz.finance.data.di.UowModule
import org.ayaz.finance.data.di.SessionModule
import org.ayaz.finance.domain.di.MapperModule
import org.ayaz.finance.domain.di.UseCaseModule
import org.ayaz.finance.presentation.di.PresentationModule
import org.koin.core.logger.Level
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(
            CacheModule().module,
            DBModule().module,
            UowModule().module,
            RepoModule().module,
            SessionModule().module,
            org.ayaz.finance.data.di.UtilModule().module,
            org.ayaz.finance.domain.di.UtilModule().module,
            MapperModule().module,
            UseCaseModule().module,
            PresentationModule().module
        )
    }
}