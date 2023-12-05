package com.govi.lokal

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.govi.lokal.databinding.ActivityDescriptionBinding
import com.govi.lokal.model.Product
import java.util.Timer
import java.util.TimerTask

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private lateinit var context: Context
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = applicationContext

        val product = intent.getSerializableExtra("Product") as Product

        binding.tvDescr.text = product.title
        binding.descrBrand.text = product.brand
        binding.descrCategory.text = "("+product.category+")"
        binding.descrDiscountPercentage.text = product.discountPercentage.toString()+"% off"
        binding.descrPrice.text = "$"+product.price.toString()+" USD"

        binding.descrStock.text = "Only "+product.stock.toString()+" items available"
        binding.descrStock.setTextColor(ContextCompat.getColor(context, R.color.stockTextColor))

        binding.tvDescr.text = product.description

        binding.descrRatings.text = product.rating.toString()+" ratings"
        binding.descrRatings.setTextColor(ContextCompat.getColor(context, R.color.ratingTextColor))

        binding.descrRatingBar.rating = product.rating.toFloat()

        // Set the rating change listener (optional, remove if not needed)
        binding.descrRatingBar.setOnRatingBarChangeListener { _, _, _ ->
        }

        val timer = Timer()
        // Schedule the task to run every second
        var i=0
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread(Runnable {
                    if(i<product.images.size){
                        Glide.with(context).load(product.images.get(i)).into(binding.ivDescr)
                        i++
                    }else{
                        i=0
                    }
                })
            }
        }, 0, 1000)

    }

}