package com.example.myapplicationlockmode

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources


class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var view: ViewGroup
    private lateinit var params: WindowManager.LayoutParams

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Service is Created", Toast.LENGTH_SHORT).show()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.overlay_view, null) as ViewGroup

        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
            )

        params.gravity = Gravity.TOP or Gravity.START
        params.x=0
        params.y=100

        windowManager.addView(view, params)

        view.setOnTouchListener(object : View.OnTouchListener{
            var initialX=0
            var initialY=0
            var initialTouchX=0.0f
            var initialTouchY=0.0f
            var moving=false

            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                view!!.performClick()
                when(event!!.action){
                    MotionEvent.ACTION_DOWN->{
                        initialX=params.x
                        initialY=params.y
                        initialTouchX= event.rawX
                        initialTouchY=event.rawY
                        moving=true
                    }
                    MotionEvent.ACTION_UP->{
                        moving=false
                    }
                    MotionEvent.ACTION_MOVE->{
                        params.x=initialX+(event.rawX-initialTouchX).toInt()
                        params.y=initialY+(event.rawY-initialTouchY).toInt()
                        windowManager.updateViewLayout(view,params)
                    }
                }
                return true
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(view)
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    /*private var initialX=0
    private var initialY=0
    private var initialTouchX=0.0f
    private var initialTouchY=0.0f
    private var moving=false


    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        view!!.performClick()
        when(event!!.action){
            MotionEvent.ACTION_DOWN->{
                initialX=params.x
                initialY=params.y
                initialTouchX= event.rawX
                initialTouchY=event.rawY
                moving=true

            }
            MotionEvent.ACTION_UP->{
                moving=false
            }
            MotionEvent.ACTION_MOVE->{
                params.x=initialX+(event.rawX-initialTouchX).toInt()
                params.y=initialY+(event.rawY-initialTouchY).toInt()
                windowManager.updateViewLayout(view,params)
            }
        }
        return true
    }*/

    /*override fun onClick(p0: View?) {
        if(!moving) Toast.makeText(this, "Clock Touched", Toast.LENGTH_SHORT).show()
    }*/

}