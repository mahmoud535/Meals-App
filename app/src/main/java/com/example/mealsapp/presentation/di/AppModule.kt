package com.example.mealsapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.mealsapp.data.local.MealDao
import com.example.mealsapp.data.local.MealDatabase
import com.example.mealsapp.data.local.UserDao
import com.example.mealsapp.data.repository.LoginRepositoryImp
import com.example.mealsapp.data.repository.MealRepositoryImpl
import com.example.mealsapp.domain.repository.LoginRepository
import com.example.mealsapp.domain.repository.MealRepository
import com.example.mealsapp.domain.usecase.AddMealUseCase
import com.example.mealsapp.domain.usecase.AddToCartUseCase
import com.example.mealsapp.domain.usecase.GetCartItemsUseCase
import com.example.mealsapp.domain.usecase.GetImageListUseCase
import com.example.mealsapp.domain.usecase.GetMealsForDayUseCase
import com.example.mealsapp.domain.usecase.GetUserByEmailUseCase
import com.example.mealsapp.domain.usecase.InsertUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.mealsapp.data.local.MealDatabase as AppDatabase1

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MealDatabase {
        return MealDatabase.getDatabase(context)
    }

    @Provides
    fun provideMealDao(mealDatabase: MealDatabase): MealDao {
        return mealDatabase.mealDao()
    }

    @Provides
    fun provideUserDao(mealDatabase: MealDatabase): UserDao {
        return mealDatabase.userDao()
    }

    @Provides
    fun provideMealsRepository(mealDao: MealDatabase): MealRepository {
        return MealRepositoryImpl(mealDao)
    }

    @Provides
    fun provideLoginRepository(userDao: MealDatabase): LoginRepository {
        return LoginRepositoryImp(userDao)
    }

    @Provides
    fun provideGetImageListUseCase(mealRepository: MealRepository): GetImageListUseCase {
        return GetImageListUseCase(mealRepository)
    }

    @Provides
    fun provideAddMealUseCase(mealRepository: MealRepository): AddMealUseCase {
        return AddMealUseCase(mealRepository)
    }

    @Provides
    fun provideGetMealsForDayUseCase(mealRepository: MealRepository): GetMealsForDayUseCase {
        return GetMealsForDayUseCase(mealRepository)
    }

    @Provides
    fun provideAddToCartUseCase(mealRepository: MealRepository): AddToCartUseCase {
        return AddToCartUseCase(mealRepository)
    }

    @Provides
    fun provideGetCartItemsUseCase(mealRepository: MealRepository): GetCartItemsUseCase {
        return GetCartItemsUseCase(mealRepository)
    }

    @Provides
    fun provideGetUserByEmailUseCase(loginRepository: LoginRepository): GetUserByEmailUseCase {
        return GetUserByEmailUseCase(loginRepository)
    }

    @Provides
    fun provideInsertUserUseCase(loginRepository: LoginRepository): InsertUserUseCase {
        return InsertUserUseCase(loginRepository)
    }
}
