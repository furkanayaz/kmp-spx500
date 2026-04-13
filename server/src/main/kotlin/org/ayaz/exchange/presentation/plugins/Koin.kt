package org.ayaz.exchange.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.ayaz.exchange.data.di.CacheModule
import org.ayaz.exchange.data.di.DBModule
import org.ayaz.exchange.data.di.NetworkModule
import org.ayaz.exchange.data.di.RepoModule
import org.ayaz.exchange.data.di.UowModule
import org.ayaz.exchange.data.di.SessionModule
import org.ayaz.exchange.domain.di.MapperModule
import org.ayaz.exchange.domain.di.UseCaseModule
import org.ayaz.exchange.presentation.di.PresentationModule
import org.koin.core.logger.Level
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(
            NetworkModule().module,
            CacheModule().module,
            DBModule().module,
            UowModule().module,
            RepoModule().module,
            SessionModule().module,
            org.ayaz.exchange.data.di.UtilModule().module,
            org.ayaz.exchange.domain.di.UtilModule().module,
            MapperModule().module,
            UseCaseModule().module,
            PresentationModule().module
        )
    }
}