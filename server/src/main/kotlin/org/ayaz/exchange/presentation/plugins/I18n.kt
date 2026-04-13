package org.ayaz.exchange.presentation.plugins

import io.ktor.i18n.I18n
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.installI18n() {
    install(I18n) {
        availableLanguages = listOf("en-US", "tr-TR")
        defaultLanguage = "en-US"
    }
}