package pl.nataliana.mywine.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.nataliana.mywine.WineRepository
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WinesListViewModel

val dbModule = module {
    single {
        WineDatabase.getInstance(
            context = get()
        )
    }
    factory { get<WineDatabase>().wineDatabaseDao }
}

val repositoryModule = module {
    single { WineRepository(get()) }
}

val uiModule = module {
    viewModel { WinesListViewModel(get(), get()) }
}