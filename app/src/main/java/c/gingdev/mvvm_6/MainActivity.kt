package c.gingdev.mvvm_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import c.gingdev.mvvm_6.databinding.ActivityMainBinding
import c.gingdev.mvvm_6.vm.BlurImageViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .run {
                model = BlurImageViewModel(applicationContext)

                model!!.baseImg.set(ContextCompat.getDrawable(applicationContext, R.drawable.ic_launcher_foreground))
            }
    }
}
