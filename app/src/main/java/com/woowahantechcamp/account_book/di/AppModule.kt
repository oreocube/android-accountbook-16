package com.woowahantechcamp.account_book.di

import android.content.Context
import com.woowahantechcamp.account_book.data.repository.AccountBookDBHelper
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.data.repository.AccountBookDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AccountBookDBHelper =
        AccountBookDBHelper(context)

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDataSource(
        dbHelper: AccountBookDBHelper,
        ioDispatcher: CoroutineDispatcher
    ) = AccountBookDataSource(dbHelper, ioDispatcher)

    @Singleton
    @Provides
    fun provideRepository(accountBookDatasource: AccountBookDataSource) =
        AccountBookRepository(accountBookDatasource)
}