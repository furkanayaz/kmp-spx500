package org.ayaz.spx500.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.ayaz.spx500.data.di.CacheModule
import org.ayaz.spx500.data.di.DBModule
import org.ayaz.spx500.data.di.RepoModule
import org.ayaz.spx500.data.di.SessionModule
import org.ayaz.spx500.domain.di.MapperModule
import org.ayaz.spx500.domain.di.UseCaseModule
import org.ayaz.spx500.presentation.di.PresentationModule
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
            RepoModule().module,
            SessionModule().module,
            org.ayaz.spx500.data.di.UtilModule().module,
            org.ayaz.spx500.domain.di.UtilModule().module,
            MapperModule().module,
            UseCaseModule().module,
            PresentationModule().module
        )
    }
}