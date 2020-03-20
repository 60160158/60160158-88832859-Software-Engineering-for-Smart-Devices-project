package com.example.facebookfirebase

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class DataRealtime : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_data_realtime, container, false)
        // Inflate the layout for this fragment


        val btn4 = view.findViewById<Button>(R.id.btn4)

        //ประกาศตัวแปร DatabaseReference รับค่า Instance และอ้างถึง path ที่เราต้องการใน database
        val mRootRef = FirebaseDatabase.getInstance().getReference()

        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mUsersRef = mRootRef.child("users")
        val mMessagesRef = mRootRef.child("messages")
        val edit_username = view.findViewById<TextView>(R.id.edit_text)
        val edit_text = view.findViewById<TextView>(R.id.edit_message)

        btn4.setOnClickListener {

            if (edit_username.text.toString().length > 0 && edit_username.text.toString().length > 0) {
                val mMessagesRef2 = mRootRef.child("data")

                val key = mMessagesRef.push().key
                val postValues: HashMap<String, Any> = HashMap()
                postValues["username"] = edit_username.text.toString()
                postValues["text"] = edit_username.text.toString()

                val childUpdates: MutableMap<String, Any> = HashMap()

                childUpdates["$key"] = postValues

                mMessagesRef2.updateChildren(childUpdates)
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

        return view

    }

    data class FriendlyMessage(
        var username: String? = "",
        var text: String? = ""
    )


}
