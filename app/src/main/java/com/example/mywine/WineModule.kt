package com.example.mywine

import com.example.mywine.adapter.WineAdapter
import com.example.mywine.model.WineDatabase
import com.example.mywine.model.WinesListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dbModule = module {
    single {
        WineDatabase.getInstance(
            context = get()
        )
    }
    factory { get<WineDatabase>().wineDao() }
}

val repositoryModule = module {
    single { WineRepository(get()) }
}

val uiModule = module {
    factory { WineAdapter() }
    viewModel { WinesListViewModel(get()) }
}