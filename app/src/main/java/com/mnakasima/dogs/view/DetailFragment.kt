package com.mnakasima.dogs.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.mnakasima.dogs.R
import com.mnakasima.dogs.databinding.FragmentDetailBinding
import com.mnakasima.dogs.model.DogPallete
import com.mnakasima.dogs.util.getProgressDrawable
import com.mnakasima.dogs.util.loadImage
import com.mnakasima.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel:DetailViewModel
    private var dogUuid = 0

    private lateinit var dataBinding:FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)

        observerViewModel()

    }

    private fun observerViewModel(){

        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let{
                dataBinding.dog = dog

                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })

    }

    private fun setupBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>(){

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate{palette ->
                            val intColor = palette?.vibrantSwatch?.rgb ?:0
                            val myPalette = DogPallete(intColor)
                            dataBinding.palette = myPalette
                        }
                }

            })
    }

}
