package com.example.facebookfirebase

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.facebookfirebase.databinding.ActivityMainBinding
import com.facebook.login.LoginManager
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var isRotate = false
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val ViewAnimation = ViewAnimation()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewAnimation.init(binding.home);
        ViewAnimation.init(binding.exit);
        ViewAnimation.init(binding.list);

        binding.fab.setOnClickListener {
            isRotate = ViewAnimation.rotateFab(binding.fab, isRotate)
        }

        binding.fab.setOnClickListener {
            isRotate = ViewAnimation.rotateFab(binding.fab, !isRotate)
            if (isRotate) {
                ViewAnimation.showInY(binding.home);
                ViewAnimation.showInY(binding.exit);
                ViewAnimation.showInY(binding.list)
            } else {
                ViewAnimation.showOutY(binding.home);
                ViewAnimation.showOutY(binding.exit);
                ViewAnimation.showOutY(binding.list)
            }
        }

        binding.list.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "เข้าสู่หน้าจอข้อมูลรายงาน.",
                Toast.LENGTH_LONG
            )
                .show()

//            mediaPlayer?.pause()

            val database = database()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.layout, database, "fragment_database")
            transaction.addToBackStack("fragment_database")
            transaction.commit()

        }

        binding.home.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "เข้าสู่หน้าแรก.",
                Toast.LENGTH_LONG
            )
                .show()

//            mediaPlayer?.pause()

            LoginManager.getInstance().logOut()
            this!!.supportFragmentManager.popBackStack()

            val authen = authen()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.layout, authen, "fragment_authen")
            transaction.addToBackStack("fragment_authen")
            transaction.commit()

        }

        binding.exit.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "ออกจากระบบ.",
                Toast.LENGTH_LONG
            )
                .show()

//            mediaPlayer?.pause()

            val builder: android.app.AlertDialog.Builder =
                android.app.AlertDialog.Builder(this)
            builder.setTitle("คุณต้องการออกจากระบบหรือไม่")
            builder.setPositiveButton("ตกลง") { _, _ ->
                finish()
                Toast.makeText(
                    this,
                    "ออกจากระบบสำเร็จ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton("ยกเลิก",
                DialogInterface.OnClickListener { _, _ ->
                    //dialog.dismiss();
                })

            builder.show()


        }



        debugHashKey()

        val authen = authen()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.layout, authen, "fragment_authen")
        transaction.addToBackStack("fragment_authen")
        transaction.commit()

        //         No title
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }


    }


    @Suppress("DEPRECATION")
    private fun debugHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.facebookfirebase",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.getEncoder().encodeToString(md.digest()))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    override fun onBackPressed() {

        val manager = supportFragmentManager.findFragmentById(R.id.layout)

        if (manager is authen) {
            finish()
        } else {
            super.onBackPressed();
        }

    }

}
