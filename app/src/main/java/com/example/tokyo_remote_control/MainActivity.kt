package com.example.tokyo_remote_control

import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    // Firebase Database Reference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance("https://esp-32-firebase-demo-b9a40-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        // Initialize Buttons
        val btnUp: ImageButton = findViewById(R.id.btnUp)
        val btnDown: ImageButton = findViewById(R.id.btnDown)
        val btnLeft: ImageButton = findViewById(R.id.btnLeft)
        val btnRight: ImageButton = findViewById(R.id.btnRight)

        // Set touch listeners for each button
        setButtonTouchListener(btnUp, "forward")
        setButtonTouchListener(btnDown, "backward")
        setButtonTouchListener(btnLeft, "left")
        setButtonTouchListener(btnRight, "right")
    }

    // Function to set the touch listener for a button
    private fun setButtonTouchListener(button: ImageButton, command: String) {
        button.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // When the button is pressed, send the command
                    updateMotorCommand(command)
                }
                MotionEvent.ACTION_UP -> {
                    // When the button is released, send "stop"
                    updateMotorCommand("stop")
                }
            }
            true
        }
    }

    // Function to update the "motorCommand" in Firebase
    private fun updateMotorCommand(command: String) {
        databaseReference.child("motorCommand").setValue(command)
            .addOnSuccessListener {
                // Successfully updated motorCommand
            }
            .addOnFailureListener { e ->
                // Handle errors
                e.printStackTrace()
            }
    }
}
