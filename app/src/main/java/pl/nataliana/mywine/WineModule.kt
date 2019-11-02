package pl.nataliana.mywine

import pl.nataliana.mywine.adapter.WineAdapter
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WinesListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
    factory { WineAdapter() }
    viewModel { WinesListViewModel(get()) }
}