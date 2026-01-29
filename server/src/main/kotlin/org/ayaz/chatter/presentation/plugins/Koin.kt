package org.ayaz.bookstore.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.ayaz.bookstore.data.di.DBModule
import org.ayaz.bookstore.domain.di.DomainModule
import org.ayaz.bookstore.presentation.di.PresentationModule
import org.koin.core.logger.Level
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(DBModule().module, DomainModule().module, PresentationModule().module)
    }
}