package com.example.facebookfirebase

import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class Edit : Fragment() {

    var Str_key = ""
    var Str_username = ""
    var Str_text = ""

    fun newInstance(key: String, username: String, text: String): Edit {

        val fragment = Edit()
        val bundle = Bundle()
        bundle.putString("key", key)
        bundle.putString("username", username)
        bundle.putString("text", text)
        fragment.setArguments(bundle)

        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        // Inflate the layout for this fragment

        val key = view.findViewById<TextView>(R.id.key)
        val edit_username = view.findViewById<TextView>(R.id.edit_username)
        val edit_text = view.findViewById<TextView>(R.id.edit_text)
        val sendData = view.findViewById<Button>(R.id.sendData)
        val delData = view.findViewById<Button>(R.id.delData)

        key.setText("KEY : " + Str_key)
        edit_username.setText("USERNAME : " + Str_username)
        edit_text.setText(Str_text)

        sendData.setOnClickListener {

            if (edit_text.text.toString().length > 0) {

                val mRootRef = FirebaseDatabase.getInstance().getReference()
                val mMessagesRef = mRootRef.child("data")

                mMessagesRef.child(Str_key).child("text").setValue(edit_text.text.toString())

                val fm: FragmentManager = activity!!.getSupportFragmentManager()
                fm.popBackStack("fragment_Edit", FragmentManager.POP_BACK_STACK_INCLUSIVE)

            } else {
                val builder: android.app.AlertDialog.Builder =
                    android.app.AlertDialog.Builder(this.context)
                builder.setTitle("ข้อมูลไม่สามารถเว้นว่างได้")
                builder.setNegativeButton("ปิด",
                    DialogInterface.OnClickListener { _, _ ->
                        //dialog.dismiss();
                    })

                builder.show()

            }


        }

        delData.setOnClickListener {

            val builder: android.app.AlertDialog.Builder =
                android.app.AlertDialog.Builder(this.context)
            builder.setTitle("ยืนยันที่จะลบข้อมูลนี้ใช่หรือไม่")
            builder.setMessage("ข้อมูลจะไม่สามารถกู้คืนได้")
            builder.setPositiveButton("ตกลง") { _, _ ->

                val mRootRef = FirebaseDatabase.getInstance().getReference()
                val mMessagesRef = mRootRef.child("data").child(Str_key)

                mMessagesRef.setValue(null)

                val fm: FragmentManager = activity!!.getSupportFragmentManager()
                fm.popBackStack("fragment_Edit", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                Toast.makeText(
                    this.context,
                    "ข้อมูลถูกลบเรียบร้อย",
                    Toast.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton("ยกเลิก",
                DialogInterface.OnClickListener { _, _ ->
                    //dialog.dismiss();
                })

            builder.show()


        }

        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            Str_key = bundle.getString("key").toString()
            Str_username = bundle.getString("username").toString()
            Str_text = bundle.getString("text").toString()

        }

    }

}

