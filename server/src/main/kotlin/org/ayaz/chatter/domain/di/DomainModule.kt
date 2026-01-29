package org.ayaz.bookstore.domain.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("org.ayaz.messenger.domain")
class DomainModule