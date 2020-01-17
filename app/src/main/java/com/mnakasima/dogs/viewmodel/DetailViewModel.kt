package com.mnakasima.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnakasima.dogs.model.DogBreed
import com.mnakasima.dogs.model.DogDatabase
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel(application: Application): BaseViewModel(application) {

    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid: Int){

        launch{
            val dog = DogDatabase(getApplication()).DogDao().getDog(uuid)
            dogLiveData.value = dog
        }

    }

}