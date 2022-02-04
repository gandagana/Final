package com.example.myapplication7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication7.models.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var userphoto: ImageView
    private lateinit var userName: TextView
    private lateinit var profilephoto: EditText
    private lateinit var userNameInput: EditText
    private lateinit var saveBtn: Button
    private lateinit var start:Button
    private lateinit var buttonLogout: Button
    private val db = FirebaseDatabase.getInstance().getReference("Userinfo")
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()

        onClickListener()
        db.child(auth.currentUser?.uid!!).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo: UserInfo= snapshot.getValue(UserInfo::class.java) ?:return
                userName.text = userInfo.name
                Glide.with(this@ProfileActivity)
                    .load(userInfo.imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(userphoto)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    private fun init() {
        buttonLogout = findViewById(R.id.buttonLogout)
        userphoto = findViewById(R.id.userphoto)
        saveBtn = findViewById(R.id.saveBtn)
        profilephoto = findViewById(R.id.profilephoto)
        userNameInput = findViewById(R.id.userNameInput)
        start = findViewById(R.id.start)
        userName = findViewById(R.id.userName)



    }

    private fun onClickListener() {
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        start.setOnClickListener {
            startActivity(Intent(this, TicTacActivity::class.java))
            finish()
        }
        saveBtn.setOnClickListener {
            val name = userNameInput.text.toString()
            val imageUrl = profilephoto.text.toString()
            if (name.isEmpty() || imageUrl.isEmpty()){
                return@setOnClickListener
            }
            val userinfo = UserInfo(name, imageUrl)
            db.child(auth.currentUser?.uid!!)
                .setValue(userinfo)


        }
    }

}